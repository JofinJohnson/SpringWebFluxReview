package com.springreview.webfluxreview.controller;

import com.springreview.webfluxreview.dto.ProductDto;
import com.springreview.webfluxreview.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public Flux<ProductDto> getAllProducts() {
        return service.getProducts();
    }

    @GetMapping("{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return service.getProduct(id);
    }

    @GetMapping("range")
    public Flux<ProductDto> getProductsInPriceRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return service.getProductInPriceRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return service.saveProduct(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productMono, @PathVariable String id) {
        return service.updateProduct(productMono, id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable  String id) {
        return service.deleteProduct(id);
    }
 }
