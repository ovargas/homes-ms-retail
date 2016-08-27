package com.tenx.ms.retail.converter;


import com.tenx.ms.retail.domain.ProductEntity;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.rest.dto.Product;

public final class ProductConverter {

    private ProductConverter() {
    }

    public static Product entityToDto(ProductEntity entity) {
        Product product = new Product();
        product.setName(entity.getName());
        product.setProductId(entity.getId());
        product.setStoreId(entity.getStore().getId());
        product.setDescription(entity.getDescription());
        product.setSku(entity.getSku());
        product.setPrice(entity.getPrice());
        return product;
    }

    public static ProductEntity dtoToEntity(Product dto) {
        ProductEntity product = new ProductEntity();
        product.setName(dto.getName());
        product.setId(dto.getProductId());
        StoreEntity store = new StoreEntity();
        store.setId(dto.getStoreId());
        product.setStore(store);
        product.setDescription(dto.getDescription());
        product.setSku(dto.getSku());
        product.setPrice(dto.getPrice());
        return product;
    }
}
