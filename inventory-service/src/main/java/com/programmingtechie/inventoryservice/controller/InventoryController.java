package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import com.programmingtechie.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skucode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skucode){
        return inventoryService.isInStock(skucode);
    }
}
