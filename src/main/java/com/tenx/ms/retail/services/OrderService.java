package com.tenx.ms.retail.services;

import com.tenx.ms.retail.converter.OrderItemConverter;
import com.tenx.ms.retail.domain.OrderEntity;
import com.tenx.ms.retail.domain.OrderItemEntity;
import com.tenx.ms.retail.exceptions.ValidationException;
import com.tenx.ms.retail.repositories.OrderItemRepository;
import com.tenx.ms.retail.repositories.OrderRepository;
import com.tenx.ms.retail.rest.dto.Order;
import com.tenx.ms.retail.rest.dto.OrderItem;
import com.tenx.ms.retail.rest.dto.OrderResult;
import com.tenx.ms.retail.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.tenx.ms.retail.converter.OrderConverter.dtoToEntity;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StockService stockService;

    @Transactional
    public OrderResult createNewOrder(Order order) {

        OrderEntity entity = dtoToEntity(order);
        entity.setStatus("ORDERED");
        entity.setOrderDate(new Date());


        for (OrderItem item : order.getProducts()) {
            Stock stock = stockService.getStock(order.getStoreId(), item.getProductId());
            if (stock != null && stock.getCount() >= item.getCount()) {
                stock.setCount(stock.getCount() - item.getCount());
                stockService.updateStock(stock);
            } else {
                throw new ValidationException(String.format("Isufficient stock for product %d", item.getProductId()));
            }
        }

        OrderEntity result = orderRepository.save(entity);
        List<OrderItemEntity> items = order.getProducts().stream().map(item -> OrderItemConverter.dtoToEntity(result.getOrderId(), item)).collect(Collectors.toList());
        orderItemRepository.save(items);
        return new OrderResult(result.getOrderId(), result.getStatus());

    }
}
