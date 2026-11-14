package com.store.baozi.service;

import com.store.baozi.dto.request.ProductCreateDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.dto.response.ProductResponseDto;
import com.store.baozi.model.Product;
import com.store.baozi.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<MessageResponseDto> create(@Valid ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.name());
        product.setPrice(productCreateDto.price());
        product.setStock(productCreateDto.stock());

        repository.save(product);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponseDto("Produto criado com sucesso!"));
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Product> found = repository.findById(id);

        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Produto não encontrado"));
        }

        Product product = found.get();
        return ResponseEntity.ok(new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        ));
    }

    public ResponseEntity<?> getAll() {
        List<ProductResponseDto> list = repository.findAllByOrderByIdAsc().stream().map(
                p -> new ProductResponseDto(p.getId(), p.getName(), p.getPrice(), p.getStock())
        ).toList();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Nenhum produto encontrado."));
        }

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<MessageResponseDto> deleteById(Long productId) {
        Optional<Product> found = repository.findById(productId);

        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Produto não encontrado"));
        }

        Product product = found.get();
        repository.delete(product);
        return ResponseEntity.ok(new MessageResponseDto("Produto removido com sucesso."));
    }

    public ResponseEntity<?> update(Long productId, ProductCreateDto dto) {
        Optional<Product> found = repository.findById(productId);

        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Produto não encontrado"));
        }

        Product product = found.get();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        repository.save(product);

        return ResponseEntity.ok(
                new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock())
        );
    }
}
