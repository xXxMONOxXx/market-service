package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.exception.ReviewNotFoundException;
import edu.azati.marketservice.mapper.ProductReviewMapper;
import edu.azati.marketservice.model.ProductReview;
import edu.azati.marketservice.repository.ProductReviewRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductReviewService implements BaseService<ProductReviewDto> {

    private final ProductReviewMapper reviewMapper;

    private final ProductReviewRepository reviewRepository;

    @Override
    public ProductReviewDto save(ProductReviewDto dto) {
        log.info("Creating new review for product");
        return reviewMapper.reviewToDto(reviewRepository.save(reviewMapper.dtoToReview(dto)));
    }

    @Override
    public ProductReviewDto delete(Long id) {
        log.info("Deleting review for product from database");
        ProductReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.deleteById(id);
        return reviewMapper.reviewToDto(review);
    }

    @Override
    public ProductReviewDto findById(Long id) {
        return reviewMapper.reviewToDto(reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id)));
    }

    @Override
    public ProductReviewDto update(ProductReviewDto dto, Long id) {
        log.info("Updating existing product order");
        ProductReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        reviewMapper.updateReviewFromDto(dto, review);
        reviewRepository.save(review);
        return reviewMapper.reviewToDto(review);
    }

    @Override
    public Page<ProductReviewDto> findAll(int pageNumber, int pageSize) {
        return reviewRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(reviewMapper::reviewToDto);
    }
}
