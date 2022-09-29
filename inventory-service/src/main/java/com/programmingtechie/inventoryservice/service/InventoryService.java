package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    //@Synchronized
    public  List<InventoryResponse> isInStock(List<String> skucode){
        log.info("wait started");
        try {
            Thread.sleep(10000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("wait ended");
        return inventoryRepository.findBySkucodeIn(skucode).
                stream().map(inventory ->
                        InventoryResponse.builder()
                                .skucode(inventory.getSkucode())
                                .inStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

}
