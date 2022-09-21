package com.programmingtechie.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderedItemsListDTO {
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
