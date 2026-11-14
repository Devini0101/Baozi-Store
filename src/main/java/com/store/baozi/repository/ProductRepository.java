package com.store.baozi.repository;

import com.store.baozi.dto.response.ProductResponseDto;
import com.store.baozi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdAsc();

    Optional<Product> findByIdAndStockTrue(Long id);
}
