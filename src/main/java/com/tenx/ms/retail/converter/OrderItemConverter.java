package com.tenx.ms.retail.converter;


import com.tenx.ms.retail.domain.OrderItemEntity;
import com.tenx.ms.retail.rest.dto.OrderItem;


public final class OrderItemConverter {

    private OrderItemConverter() {

    }

    public static OrderItem entityToDto(OrderItemEntity entity) {

        OrderItem dto = new OrderItem();
        dto.setProductId(entity.getProductId());
        dto.setCount(entity.getCount());
        return dto;
    }

    public static OrderItemEntity dtoToEntity(Long orderId, OrderItem dto) {

        OrderItemEntity entity = new OrderItemEntity();

        entity.setProductId(dto.getProductId());
        entity.setCount(dto.getCount());
        entity.setOrderId(orderId);

        return entity;
    }
}
