package com.tenx.ms.retail.product.rest.dto;

import com.tenx.ms.commons.validation.constraints.DollarAmount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@ApiModel("Product")
public class Product {

    @ApiModelProperty(value = "Product Id", required = true)
    private Long productId;

    @ApiModelProperty(value = "Name of the product", required = true)
    private String name;

    @ApiModelProperty(value = "Description of the item", required = true)
    private String description;

    @ApiModelProperty(value = "Store associated with the product", required = true)
    private Long storeId;

    @ApiModelProperty(value = "SKU code", required = true)
    @Valid
    @Pattern(regexp = "^[A-Za-z0-9]{5,10}$", message = "SKU must be alpha-numeric with a min length of 5 and max of 10")
    private String sku;

    @ApiModelProperty(value = "Price of the product", required = true)
    @Valid
    @DollarAmount
    private BigDecimal price;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
