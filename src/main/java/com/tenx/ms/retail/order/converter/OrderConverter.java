package com.tenx.ms.retail.order.converter;


import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.order.rest.dto.Order;

public final class OrderConverter {

    private OrderConverter() {

    }

    public static Order entityToDto(OrderEntity entity) {
        Order dto = new Order();

        dto.setStoreId(entity.getStore().getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public static OrderEntity dtoToEntity(Order dto) {
        OrderEntity entity = new OrderEntity();

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());

        StoreEntity store = new StoreEntity();
        store.setId(dto.getStoreId());
        entity.setStore(store);
        return entity;
    }

}
