package com.programmingtechie.inventoryservice.runner;

import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InventoryRunner implements  CommandLineRunner  {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Inventory inventory1 = Inventory.builder()
                .skucode("iphone_13")
                .quantity(100)
                .build();

        Inventory inventory2 = Inventory.builder()
                .skucode("iphone_12")
                .quantity(0)
                .build();
        List<Inventory> inventories= List.of(inventory1,inventory2);
        inventoryRepository.saveAll(inventories);
        log.info("inventory data is saved in DB");

    }
}
