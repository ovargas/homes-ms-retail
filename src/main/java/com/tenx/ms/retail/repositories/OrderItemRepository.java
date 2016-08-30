package com.tenx.ms.retail.repositories;


import com.tenx.ms.retail.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
