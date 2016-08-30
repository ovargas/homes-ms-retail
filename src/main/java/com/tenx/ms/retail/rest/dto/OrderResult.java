package com.tenx.ms.retail.rest.dto;


import com.tenx.ms.commons.validation.constraints.EnumValid;
import com.tenx.ms.retail.enums.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("OrderResult")
public class OrderResult {

    public OrderResult() {}

    public OrderResult(Long orderId, String status) {

        this.orderId = orderId;
        this.status = status;
    }

    @ApiModelProperty(value = "Order Id")
    private Long orderId;

    @ApiModelProperty(value = "Order status")
    @EnumValid(enumClass = OrderStatus.class)
    private String status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
