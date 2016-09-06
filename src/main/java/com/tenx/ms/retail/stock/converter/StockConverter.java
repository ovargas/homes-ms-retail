package com.tenx.ms.retail.stock.converter;


import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.rest.dto.Stock;

public final class StockConverter {

    private StockConverter() {
    }

    public static StockEntity dtoToEntity(Stock stock) {

        StockEntity entity = new StockEntity();
        entity.setProductId(stock.getProductId());
        entity.setCount(stock.getCount());

        return entity;
    }


    public static Stock entityToDto(StockEntity stockEntity) {
        Stock stock = new Stock();

        ProductEntity product = stockEntity.getProduct();
        stock.setProductId(product.getId());
        stock.setStoreId(product.getStore().getId());
        stock.setCount(stockEntity.getCount());

        return stock;
    }
}
