package com.tenx.ms.retail.stock.services;


import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.services.ProductService;
import com.tenx.ms.retail.stock.converter.StockConverter;
import com.tenx.ms.retail.stock.repositories.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.tenx.ms.retail.stock.converter.StockConverter.dtoToEntity;

@Service
public class StockService {


    @Autowired
    private StockRepository repository;

    @Autowired
    private ProductService productService;

    public void updateStock(Stock stock) {
        Product product = productService.getProductById(stock.getProductId());

        if (product == null || product.getStoreId().longValue() != stock.getStoreId().longValue()) {
            String message = String.format("Product %d with store %d",
                stock.getProductId(),
                stock.getStoreId());
            throw new NoSuchElementException(message);
        }

        repository.save(dtoToEntity(stock));
    }

    public Stock getStock(Long storeId, Long productId) {
        Stock stock = repository.findByProductId(productId).map(StockConverter::entityToDto).get();
        if (stock.getStoreId().longValue() != storeId.longValue()) {
            String message = String.format("Product %d with store %d",
                productId,
                storeId);
            throw new NoSuchElementException(message);

        }

        return stock;
    }

}
