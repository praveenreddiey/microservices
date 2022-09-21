package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.dto.OrderedItemsListDTO;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderedItemsList;
import com.programmingtechie.orderservice.repository.OrderRepository;
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
        List<OrderedItemsList> orderedItemsListList = orderRequest.getOrderedItemsListDTO().stream()
//                .map(dto -> {
//                    return OrderedItemsList.builder()b
//                            .price(dto.getPrice())
//                            .quantity(dto.getQuantity())
//                            .skucode(dto.getSkucode())
//                            .build();
//                }
//                )
                .map(this :: mapDTOtOEntity)
                .toList();

        order.setOrderedItemsList(orderedItemsListList);
        orderRepository.save(order);

    }
    private OrderedItemsList mapDTOtOEntity(OrderedItemsListDTO orderedItemsListDTO){
        OrderedItemsList orderedItemsList = new OrderedItemsList();
        orderedItemsList.setQuantity(orderedItemsListDTO.getQuantity());
        orderedItemsList.setPrice(orderedItemsListDTO.getPrice());
        orderedItemsList.setSkucode(orderedItemsListDTO.getSkucode());
        return orderedItemsList;

    }
}
