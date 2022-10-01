package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.dto.OrderedItemsListDTO;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderedItemsList;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderedItemsList> orderedItemsList = orderRequest.getOrderedItemsListDTO().stream()
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

        order.setOrderedItemsList(orderedItemsList);

        List<String> skucodes=order.getOrderedItemsList()
                .stream().map(orderedItems -> {
                    return orderedItems.getSkucode();
                }).toList();

        //we can also write as

//        List<String> skucodes=order.getOrderedItemsList()
//                .stream().map(OrderedItemsList::getSkucode)
//                .toList();

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try(final Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
            //call inventory service before placing an order
            //to check if product is in stock
            InventoryResponse inventoryResponse[] = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skucode" ,skucodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isInStock);


            if(allProductsInStock){
                orderRepository.save(order);
                return "Order placed succesfully";
            }else{
                throw new IllegalArgumentException("product is not in stock, please try again later");
            }
        }finally {
            inventoryServiceLookup.end();
        }


    }
    private OrderedItemsList mapDTOtOEntity(OrderedItemsListDTO orderedItemsListDTO){
        OrderedItemsList orderedItemsList = new OrderedItemsList();
        orderedItemsList.setQuantity(orderedItemsListDTO.getQuantity());
        orderedItemsList.setPrice(orderedItemsListDTO.getPrice());
        orderedItemsList.setSkucode(orderedItemsListDTO.getSkucode());
        return orderedItemsList;

    }
}
