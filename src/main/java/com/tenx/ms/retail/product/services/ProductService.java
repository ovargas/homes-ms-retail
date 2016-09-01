package com.tenx.ms.retail.product.services;

import com.tenx.ms.retail.product.converter.ProductConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repositories.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tenx.ms.retail.product.converter.ProductConverter.dtoToEntity;

@Service
public class ProductService {


    @Autowired
    private ProductRepository repository;

    public Product getProductById(Long id) {
        return repository.findById(id).map(ProductConverter::entityToDto).get();
    }

    public List<Product> getProducts() {

        return repository.findAll().stream().map(ProductConverter::entityToDto).collect(Collectors.toList());
    }

    public Long createProduct(Product product) {

        ProductEntity entity = repository.save(dtoToEntity(product));
        return entity.getId();
    }

    public void updateProduct(Product product) {

        ProductEntity entity = repository.findById(product.getProductId()).get();
        if (entity != null) {
            ProductEntity productToUpdate = dtoToEntity(product);

            if (productToUpdate.getName() == null) {
                productToUpdate.setName(entity.getName());
            }
            if (productToUpdate.getDescription() == null) {
                productToUpdate.setDescription(entity.getDescription());
            }
            if (productToUpdate.getSku() == null) {
                productToUpdate.setSku(entity.getSku());
            }
            if (productToUpdate.getPrice() == null) {
                productToUpdate.setPrice(entity.getPrice());
            }

            productToUpdate.setStore(entity.getStore());
            repository.save(productToUpdate);
        }
    }

    public void deleteProduct(Long id) {
        ProductEntity entity = repository.findById(id).get();
        if (entity != null) {
            repository.delete(id);
        }
    }
}
