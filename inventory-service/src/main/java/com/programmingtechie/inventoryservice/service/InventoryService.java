package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skucode){
        return inventoryRepository.findBySkucodeIn(skucode).
                stream().map(inventory ->
                        InventoryResponse.builder()
                                .skucode(inventory.getSkucode())
                                .inStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

}
