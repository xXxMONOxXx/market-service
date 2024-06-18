package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.model.rev.ProductRev;
import edu.azati.marketservice.model.rev.ServiceRev;
import edu.azati.marketservice.model.rev.UserRev;
import edu.azati.marketservice.repository.ProductRevRepository;
import edu.azati.marketservice.repository.ServiceRevRepository;
import edu.azati.marketservice.repository.UserRevRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RevisionService {

    private final UserRevRepository userRevRepository;

    private final ServiceRevRepository serviceRevRepository;

    private final ProductRevRepository productRevRepository;

    public List<ProductRev> getProductHistory(Long id) {
        return productRevRepository.findByProductId(id);
    }

    public List<ServiceRev> getServiceHistory(Long id) {
        return serviceRevRepository.findByServiceId(id);
    }

    public List<UserRev> getUserHistory(Long id) {
        return userRevRepository.findByUserId(id);
    }
}
