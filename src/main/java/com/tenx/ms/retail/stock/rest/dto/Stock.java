package com.tenx.ms.retail.stock.rest.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@ApiModel("stock")
public class Stock {

    @ApiModelProperty(value = "Product Id", required = true)
    private Long productId;
    @ApiModelProperty(value = "Store Id", required = true)
    private Long storeId;
    @ApiModelProperty(value = "Total count in stock", required = true)
    @Min(value = 0)
    @Valid
    private Long count;

    public Stock() {
    }

    public Stock(Long productId, Long storeId, Long count) {

        this.productId = productId;
        this.storeId = storeId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
