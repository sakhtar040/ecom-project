package com.ecomproject.order_service.service;

import com.ecomproject.order_service.dto.OrderLineItemDto;
import com.ecomproject.order_service.dto.OrderRequest;
import com.ecomproject.order_service.model.Order;
import com.ecomproject.order_service.model.OrderLineItem;
import com.ecomproject.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItems =
                orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto){
        return OrderLineItem.builder()
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .skuCode(orderLineItemDto.getSkuCode())
                .build();
    }
}
