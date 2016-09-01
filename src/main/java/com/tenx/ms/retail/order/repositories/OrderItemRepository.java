package com.tenx.ms.retail.order.repositories;


import com.tenx.ms.retail.order.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
