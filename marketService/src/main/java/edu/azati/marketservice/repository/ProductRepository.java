package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySellerIdAndCategoriesOrderByPriceAsc(Pageable pageable, Long sellerId, List<Long> categories);

    Optional<Product> findBySellerIdAndCategoriesOrderByPriceDesc(Pageable pageable, Long sellerId, List<Long> categories);
}
