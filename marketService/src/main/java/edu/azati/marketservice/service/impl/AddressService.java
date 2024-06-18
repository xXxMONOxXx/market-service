package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.exception.AddressNotFoundException;
import edu.azati.marketservice.mapper.AddressMapper;
import edu.azati.marketservice.model.Address;
import edu.azati.marketservice.repository.AddressRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AddressService implements BaseService<AddressDto> {

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    @Override
    public AddressDto save(AddressDto dto) {
        log.info("Saving a new address");
        return addressMapper.addressToDto(addressRepository.save(addressMapper.dtoToAddress(dto)));
    }

    @Override
    public AddressDto delete(Long id) {
        log.info("Deleting address from database");
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
        addressRepository.deleteById(id);
        return addressMapper.addressToDto(address);
    }

    @Override
    public AddressDto findById(Long id) {
        return addressMapper.addressToDto(addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id)));
    }

    @Override
    public AddressDto update(AddressDto dto, Long id) {
        log.info("Updating existing address");
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
        addressMapper.updateAddressFromDto(dto, address);
        addressRepository.save(address);
        return addressMapper.addressToDto(address);
    }

    @Override
    public Page<AddressDto> findAll(int pageNumber, int pageSize) {
        return addressRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(addressMapper::addressToDto);
    }
}
