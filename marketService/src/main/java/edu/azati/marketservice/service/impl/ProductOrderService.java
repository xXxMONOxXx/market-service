package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.exception.ProductOrderNotFoundException;
import edu.azati.marketservice.mapper.ProductOrderMapper;
import edu.azati.marketservice.model.ProductOrder;
import edu.azati.marketservice.model.ProductOrderProduct;
import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.repository.ProductOrderRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductOrderService implements BaseService<ProductOrderDto> {

    private final ProductOrderMapper productOrderMapper;

    private final ProductOrderRepository productOrderRepository;

    private final ProductService productService;

    private final OrderMailService orderMailService;

    @Override
    public ProductOrderDto save(ProductOrderDto dto) {
        log.info("Creating new order of products");
        ProductOrder productOrder = productOrderMapper.dtoToProductOrder(dto);
        List<ProductOrderProduct> products = productOrder.getProducts();
        productOrder.setProducts(null);
        productOrder = productOrderRepository.save(productOrder);
        Long orderId = productOrder.getId();
        products.forEach(product -> product.setOrderId(orderId));
        productOrder.setProducts(products);
        return productOrderMapper.productOrderToDto(productOrderRepository.save(productOrder));
    }

    @Override
    public ProductOrderDto delete(Long id) {
        log.info("Deleting product order from database");
        ProductOrder productOrder = productOrderRepository.findById(id)
                .orElseThrow(() -> new ProductOrderNotFoundException(id));
        productOrderRepository.deleteById(id);
        return productOrderMapper.productOrderToDto(productOrder);
    }

    @Override
    public ProductOrderDto findById(Long id) {
        return productOrderMapper.productOrderToDto(productOrderRepository.findById(id)
                .orElseThrow(() -> new ProductOrderNotFoundException(id)));
    }

    @Override
    public ProductOrderDto update(ProductOrderDto dto, Long id) {
        log.info("Updating existing product");
        ProductOrder productOrder = productOrderRepository.findById(id)
                .orElseThrow(() -> new ProductOrderNotFoundException(id));
        boolean mustNotifyCustomer = false;
        Status oldStatus = productOrder.getCurrentStatus();
        if (!dto.getCurrentStatus().equals(productOrder.getCurrentStatus())) {
            mustNotifyCustomer = true;
        }
        productOrderMapper.updateProductOrderFromDto(dto, productOrder);
        productOrderRepository.save(productOrder);
        ProductOrderDto response = productOrderMapper.productOrderToDto(productOrder);
        if (mustNotifyCustomer) {
            log.info("Status of order changed, sending email to customer");
            orderMailService.sendStatusChangeMessageForProductOrder(oldStatus,
                    dto.getCurrentStatus(),
                    id,
                    response,
                    productOrder.getUpdatedAt());
        }
        return response;
    }

    @Override
    public Page<ProductOrderDto> findAll(int pageNumber, int pageSize) {
        return productOrderRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(productOrderMapper::productOrderToDto);
    }

    public boolean orderIsForProducts(Long id) {
        log.info("Check if order contains products");
        return productOrderRepository.findById(id)
                .orElseThrow(() -> new ProductOrderNotFoundException(id)).getOrderHasProducts();
    }

    public Long getAmount(Long id) {
        log.info("Calculating total price of the order");
        ProductOrderDto order = findById(id);
        AtomicReference<Long> amount = new AtomicReference<>(0L);
        order.getProducts().forEach(product ->
                amount.updateAndGet(v ->
                        v + (long) product.getQuantity() * productService.findById(product.getProductId()).getPrice()));
        return amount.get();
    }
}
