package com.store.baozi.service;

import com.store.baozi.dto.request.ClientCreateDto;
import com.store.baozi.dto.response.ClientResponseDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.model.Client;
import com.store.baozi.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<MessageResponseDto> create(ClientCreateDto dto) {

        LocalDate today = LocalDate.now();
        if (dto.clientSince().isAfter(today)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDto("A data de clienteDesde não deve ser maior que a atual"));
        }

        Client client = new Client();

        client.setName(dto.name());
        client.setClientSince(dto.clientSince());

        repository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDto("Cliente criado com sucesso."));
    }

    public ResponseEntity<?> getById(Long clientId) {

        Optional<Client> clientOptional = repository.findById(clientId);

        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Cliente não encontrado."));
        }

        Client client = clientOptional.get();

        return ResponseEntity.ok(new ClientResponseDto(client.getId(),client.getName(), client.getClientSince()));
    }

    public ResponseEntity<?> getAll() {
        List<ClientResponseDto> list = repository.findAllByOrderByIdAsc().stream().map(
                c -> new ClientResponseDto(c.getId(), c.getName(), c.getClientSince())
        ).toList();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Nenhum cliente encontrado."));
        }

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<MessageResponseDto> deleteById(Long clientId) {
        Optional<Client> clientOptional = repository.findById(clientId);

        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Cliente não encontrado."));
        }

        Client client = clientOptional.get();

        repository.delete(client);

        return ResponseEntity.ok(new MessageResponseDto("Cliente removido com sucesso"));
    }

    public ResponseEntity<?> update(Long clientId, @Valid ClientCreateDto dto) {
        Optional<Client> clientOptional = repository.findById(clientId);

        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Cliente não encontrado."));
        }

        Client client = clientOptional.get();

        client.setName(dto.name());
        client.setClientSince(dto.clientSince());
        repository.save(client);
        return ResponseEntity.ok(new ClientResponseDto(client.getId(), client.getName(), client.getClientSince()));
    }
}
