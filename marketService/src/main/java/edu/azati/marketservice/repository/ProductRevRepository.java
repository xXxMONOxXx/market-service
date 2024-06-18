package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.rev.ProductRev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRevRepository extends JpaRepository<ProductRev, Long> {

    List<ProductRev> findByProductId(Long id);
}
