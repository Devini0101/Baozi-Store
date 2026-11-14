package com.store.baozi.repository;

import com.store.baozi.dto.response.ClientResponseDto;
import com.store.baozi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findAllByOrderByIdAsc();
}
