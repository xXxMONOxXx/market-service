package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ServiceReviewDto;
import edu.azati.marketservice.exception.ReviewNotFoundException;
import edu.azati.marketservice.mapper.ServiceReviewMapper;
import edu.azati.marketservice.model.ServiceReview;
import edu.azati.marketservice.repository.ServiceReviewRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ServiceReviewService implements BaseService<ServiceReviewDto> {

    private final ServiceReviewMapper reviewMapper;

    private final ServiceReviewRepository reviewRepository;

    @Override
    public ServiceReviewDto save(ServiceReviewDto dto) {
        log.info("Creating a new review for service");
        return reviewMapper.reviewToDto(reviewRepository.save(reviewMapper.dtoToReview(dto)));
    }

    @Override
    public ServiceReviewDto delete(Long id) {
        log.info("Deleting an existing review of service");
        ServiceReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.deleteById(id);
        return reviewMapper.reviewToDto(review);
    }

    @Override
    public ServiceReviewDto findById(Long id) {
        return reviewMapper.reviewToDto(reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id)));
    }

    @Override
    public ServiceReviewDto update(ServiceReviewDto dto, Long id) {
        log.info("Updating an existing review of service");
        ServiceReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        reviewMapper.updateReviewFromDto(dto, review);
        reviewRepository.save(review);
        return reviewMapper.reviewToDto(review);
    }

    @Override
    public Page<ServiceReviewDto> findAll(int pageNumber, int pageSize) {
        return reviewRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(reviewMapper::reviewToDto);
    }
}
