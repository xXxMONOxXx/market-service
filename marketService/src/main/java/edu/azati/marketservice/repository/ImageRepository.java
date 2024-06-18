package edu.azati.marketservice.repository;

import edu.azati.marketservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByProductIdAndIsPreview(Long productId, Boolean isPreview);

    Optional<Image> findByServiceIdAndIsPreview(Long serviceId, Boolean isPreview);

    Optional<Image> findByFilename(String filename);
}
