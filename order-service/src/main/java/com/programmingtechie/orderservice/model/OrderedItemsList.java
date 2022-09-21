package com.programmingtechie.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "t_order_line_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedItemsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;

}
