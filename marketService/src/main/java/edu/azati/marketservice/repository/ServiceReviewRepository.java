package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.ServiceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceReviewRepository extends JpaRepository<ServiceReview, Long> {
}
