package com.store.baozi.controller;

import com.store.baozi.dto.request.ClientCreateDto;
import com.store.baozi.dto.request.ProductCreateDto;
import com.store.baozi.dto.response.ClientResponseDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.dto.response.ProductResponseDto;
import com.store.baozi.service.ClientService;
import com.store.baozi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createProduct(@RequestBody @Valid ClientCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Long clientId = Long.valueOf(id);
        return this.service.getById(clientId);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return this.service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(@PathVariable String id) {
        Long clientId = Long.valueOf(id);
        return this.service.deleteById(clientId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid ClientCreateDto dto) {
        Long clientId = Long.valueOf(id);
        return this.service.update(clientId, dto);
    }

}
