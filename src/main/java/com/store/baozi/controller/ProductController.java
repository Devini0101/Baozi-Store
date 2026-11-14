package com.store.baozi.controller;

import com.store.baozi.dto.request.ProductCreateDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.dto.response.ProductResponseDto;
import com.store.baozi.model.Product;
import com.store.baozi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        return this.service.create(productCreateDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Long productId = Long.valueOf(id);
        return this.service.getById(productId);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return this.service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(@PathVariable String id) {
        Long productId = Long.valueOf(id);
        return this.service.deleteById(productId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid ProductCreateDto dto) {
        Long productId = Long.valueOf(id);
        return this.service.update(productId, dto);
    }

}
