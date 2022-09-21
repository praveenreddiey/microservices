package com.programmingtechie.productservice.service;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository ps;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .description(productRequest.getDescription())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
        ps.save(product);
        log.info("Product {} is saved",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = ps.findAll();
        List<ProductResponse> productResponses = products.stream().map(p -> {
            return ProductResponse.builder()
                    .description(p.getDescription())
                    .price(p.getPrice())
                    .name(p.getName())
                    .id(p.getId())
                    .build();
        }).toList();
        return productResponses;


    }
}
