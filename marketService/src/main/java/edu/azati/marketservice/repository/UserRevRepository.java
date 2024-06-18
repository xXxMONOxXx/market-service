package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.rev.UserRev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRevRepository extends JpaRepository<UserRev, Long> {
    
    List<UserRev> findByUserId(Long userId);
}
