package com.store.baozi.controller;

import com.store.baozi.dto.request.OrderCreateDto;
import com.store.baozi.dto.request.ProductCreateDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.dto.response.OrderResponseDto;
import com.store.baozi.dto.response.ProductResponseDto;
import com.store.baozi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createProduct(@RequestBody @Valid OrderCreateDto dto) {
        return this.service.create(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Long orderId = Long.valueOf(id);
        return this.service.getById(orderId);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return this.service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(@PathVariable String id) {
        Long orderId = Long.valueOf(id);
        return this.service.deleteById(orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid OrderCreateDto dto) {
        Long orderId = Long.valueOf(id);
        return this.service.update(orderId, dto);
    }

}
