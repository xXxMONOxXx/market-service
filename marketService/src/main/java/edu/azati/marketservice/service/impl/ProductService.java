package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ImageDto;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.exception.ProductNotFoundException;
import edu.azati.marketservice.mapper.ProductMapper;
import edu.azati.marketservice.model.Product;
import edu.azati.marketservice.model.ProductPriceHistory;
import edu.azati.marketservice.repository.ProductPriceHistoryRepository;
import edu.azati.marketservice.repository.ProductRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<ProductDto> {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    private final ImageService imageService;

    @Override
    public ProductDto save(ProductDto dto) {
        log.info("Creating new product");
        return productMapper.productToDto(productRepository.save(productMapper.dtoToProduct(dto)));
    }

    @Override
    public ProductDto delete(Long id) {
        log.info("Deleting existing product from database");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        return productMapper.productToDto(product);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        ProductDto productDto = productMapper.productToDto(product);
        productDto.setImages(imageService.mapImagesToImageResponseDto(product.getImages()));
        return productDto;
    }

    @Override
    public ProductDto update(ProductDto dto, Long id) {
        log.info("Updating existing product");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        if (!product.getPrice().equals(dto.getPrice())) {
            productPriceHistoryRepository.save(ProductPriceHistory.builder()
                    .price(product.getPrice())
                    .productId(product.getId())
                    .build());
        }
        productMapper.updateProductFromDto(dto, product);
        productRepository.save(product);
        ProductDto productDto = productMapper.productToDto(product);
        productDto.setImages(imageService.mapImagesToImageResponseDto(product.getImages()));
        return productDto;
    }

    @Override
    public Page<ProductDto> findAll(int pageNumber, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(product -> {
                    ProductDto productDto = productMapper.productToDto(product);
                    productDto.setImages(imageService.mapImagesToImageResponseDto(product.getImages()));
                    return productDto;
                });
    }

    public void addImage(MultipartFile image, Boolean isPreview, Long productId) {
        log.info("Adding new image for product");
        if (Boolean.TRUE.equals(isPreview)) {
            imageService.changePreview(productId, true);
        }
        imageService.saveImage(ImageDto.builder()
                .productId(productId)
                .isPreview(isPreview)
                .image(image)
                .build());
    }

    public void deleteImage(Long id) {
        log.info("Deleting image, related to product");
        imageService.deleteImage(id);
    }

    public Object findBy(int pageNumber, int pageSize, String sortByAsc, Long sellerId, List<Long> categoriesIds) {
        if ("desc".equals(sortByAsc)) {
            return productRepository.findBySellerIdAndCategoriesOrderByPriceDesc(PageRequest.of(pageNumber, pageSize), sellerId, categoriesIds)
                    .map(product -> {
                        ProductDto productDto = productMapper.productToDto(product);
                        productDto.setImages(imageService.mapImagesToImageResponseDto(product.getImages()));
                        return productDto;
                    });
        } else {
            return productRepository.findBySellerIdAndCategoriesOrderByPriceAsc(PageRequest.of(pageNumber, pageSize), sellerId, categoriesIds)
                    .map(product -> {
                        ProductDto productDto = productMapper.productToDto(product);
                        productDto.setImages(imageService.mapImagesToImageResponseDto(product.getImages()));
                        return productDto;
                    });
        }
    }
}
