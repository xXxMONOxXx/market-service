package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String adminClientId;
    @Value("${keycloak.client-id.secret}")
    private String adminClientSecret;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    public void registerUser(UserDto userDto) {
        log.info("Start of user registration in keycloak");
        UserRepresentation user = getUserRepresentation(userDto);
        UsersResource usersResource = getUsersResource();
        try (Response response = usersResource.create(user)) {
            if (HttpStatus.CREATED.value() != response.getStatus()) {
                log.error("User registration in keycloak failed");
                throw new KeycloakException(String.format("Keycloak server responded with status: %s",
                        response.getStatus()));
            }
        } catch (Exception e) {
            log.error("Keycloak server is not available");
            throw new KeycloakException(e);
        }
        List<UserRepresentation> savedUser = usersResource.search(userDto.getUsername(), true);
        assignRoles(savedUser.get(0).getId(), userDto.getRoles().stream()
                .map(roleDto -> roleDto.getName().getValue()).toList());
    }

    public String generateToken(UserDto userDto) {
        try (Keycloak generatedEntry = generateKeycloakEntryForUser(userDto.getUsername(), userDto.getPassword())) {
            return generatedEntry.tokenManager().getAccessTokenString();
        } catch (Exception e) {
            log.error("Keycloak server is not available or credentials are invalid");
            throw new KeycloakException(e);
        }
    }

    private Keycloak generateKeycloakEntryForUser(String username, String password) {
        return KeycloakBuilder.builder()
                .username(username)
                .password(password)
                .realm(realm)
                .serverUrl(authServerUrl)
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
    }

    private UserRepresentation getUserRepresentation(UserDto userDto) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getUserDetails().getFirstname());
        user.setLastName(userDto.getUserDetails().getSurname());
        user.setEmailVerified(false);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(userDto.getPassword());
        cred.setTemporary(false);
        user.setCredentials(List.of(cred));
        return user;
    }

    private void assignRoles(String userId, List<String> roles) {
        List<RoleRepresentation> roleList = rolesToRealmRoleRepresentation(roles);
        keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roleList);

    }

    private List<RoleRepresentation> rolesToRealmRoleRepresentation(List<String> roles) {
        List<RoleRepresentation> existingRoles = keycloak.realm(realm)
                .roles()
                .list();
        List<String> serverRoles = existingRoles
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
        List<RoleRepresentation> resultRoles = new ArrayList<>();
        for (String role : roles) {
            int index = serverRoles.indexOf(role);
            if (index != -1) {
                resultRoles.add(existingRoles.get(index));
            } else {
                log.info("Role doesn't exist");
            }
        }
        return resultRoles;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

}
