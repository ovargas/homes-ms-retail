package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel("OrderProduct")
public class OrderItem {

    public OrderItem() {

    }

    public OrderItem(Long productId, long count) {
        this.productId = productId;
        this.count = count;
    }

    @ApiModelProperty(value = "Product Id", required = true)
    private Long productId;


    @ApiModelProperty(value = "Product count", required = true)
    @Min(value = 1)
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
