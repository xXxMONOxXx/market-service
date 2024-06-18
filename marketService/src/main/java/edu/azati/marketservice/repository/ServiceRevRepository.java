package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.rev.ServiceRev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRevRepository extends JpaRepository<ServiceRev, Long> {

    List<ServiceRev> findByServiceId(Long id);
}
