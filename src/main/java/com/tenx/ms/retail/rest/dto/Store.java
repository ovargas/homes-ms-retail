package com.tenx.ms.retail.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Store")
public class Store {

    @ApiModelProperty(value = "Store Id", required = true)
    private Long storeId;

    @ApiModelProperty(value = "Store Name", required = true)
    private String name;


    public Long getStoreId() {
        return storeId;
    }

    public Store setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Store setName(String name) {
        this.name = name;
        return this;
    }
}
