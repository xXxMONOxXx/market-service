package edu.azati.marketservice.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.dto.UserVerificationResponseDto;
import edu.azati.marketservice.exception.SmsVerificationException;
import edu.azati.marketservice.exception.UserNotFoundException;
import edu.azati.marketservice.mapper.UserMapper;
import edu.azati.marketservice.model.Role;
import edu.azati.marketservice.model.User;
import edu.azati.marketservice.model.UserDetails;
import edu.azati.marketservice.model.enums.VerificationStatus;
import edu.azati.marketservice.repository.UserRepository;
import edu.azati.marketservice.service.BaseService;
import edu.azati.marketservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto> {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final KeycloakService keycloakService;

    @Value("${twilio.account.sdi}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.service.id}")
    private String twilioServiceId;

    @Override
    public UserDto save(UserDto dto) {
        log.info("Creating new user");
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userMapper.dtoToUser(dto);
        UserDetails userDetails = user.getUserDetails();
        List<Role> roles = user.getRoles();
        user.setRoles(null);
        user.setUserDetails(null);
        user = userRepository.save(user);
        Long userId = user.getId();
        userDetails.setId(userId);
        user.setUserDetails(userDetails);
        roles.forEach(role -> role.setUserId(userId));
        user.setRoles(roles);
        Twilio.init(twilioAccountSid, twilioAuthToken);
        try {
            Verification verification = Verification.creator(
                    twilioServiceId, user.getUserDetails().getPhone(), "sms").create();
            log.info(String.format("Verification though SMS status: %s", verification.getStatus()));
        } catch (Exception e) {
            throw new SmsVerificationException(e);
        }
        return userMapper.userToDto(userRepository.save(user));
    }

    @Override
    public UserDto delete(Long id) {
        log.info("Deleting existing user from database");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(user.getId());
        return userMapper.userToDto(user);
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.userToDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public UserDto update(UserDto dto, Long id) {
        log.info("Updating existing user");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userMapper.updateUserFromDto(dto, user);
        userRepository.save(user);
        return userMapper.userToDto(userRepository.save(user));
    }

    @Override
    public Page<UserDto> findAll(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(userMapper::userToDto);
    }

    public UserVerificationResponseDto verifyUser(String code, Long userId) {
        UserDto user = findById(userId);
        if (user == null) {
            return UserVerificationResponseDto.builder()
                    .verificationMessage("User does not exist")
                    .build();
        }
        Twilio.init(twilioAccountSid, twilioAuthToken);
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            twilioServiceId)
                    .setTo(user.getUserDetails().getPhone())
                    .setCode(code)
                    .create();
            log.info(verificationCheck.getStatus());
            if (VerificationStatus.APPROVED.getValue().equals(verificationCheck.getStatus())) {
                log.info(String.format("Success verification for user %s", userId));
                user.setVerifiedAt(LocalDateTime.now());
                update(user, userId);
                keycloakService.registerUser(user);
                return UserVerificationResponseDto.builder()
                        .verified(true)
                        .verificationMessage(Constants.SUCCESS_VERIFICATION_MESSAGE)
                        .build();
            } else {
                log.info(String.format("Verification for user %s failed, status: %s",
                        userId,
                        verificationCheck.getStatus()));
                return UserVerificationResponseDto.builder()
                        .verified(false)
                        .verificationMessage(Constants.FAILED_VERIFICATION_MESSAGE)
                        .build();
            }

        } catch (Exception e) {
            log.info("Verification failed.");
            throw new SmsVerificationException(e);
        }
    }

    public String generateToken(UserDto userDto) {
        return keycloakService.generateToken(userDto);
    }


}
