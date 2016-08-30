package com.tenx.ms.retail.enums;

import com.tenx.ms.commons.rest.BaseValueNameEnum;
import com.tenx.ms.commons.util.EnumUtil;

public enum OrderStatus implements BaseValueNameEnum<OrderStatus> {
    ORDERED(1, "ORDERED"),
    PACKING(2, "PACKING"),
    SHIPPED(3, "SHIPPED"),
    INVALID(EnumUtil.INVALID_ENUM_VALUE, EnumUtil.INVALID_ENUM_MSG);

    private int value;
    private String label;

    OrderStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toJson() {
        return label;
    }

    @Override
    public OrderStatus getInvalidEnum() {
        return INVALID;
    }

    @Override
    public int getValue() {
        return value;
    }
}
