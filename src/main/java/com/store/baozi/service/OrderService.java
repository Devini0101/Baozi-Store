package com.store.baozi.service;

import com.store.baozi.dto.request.OrderCreateDto;
import com.store.baozi.dto.response.ClientResponseDto;
import com.store.baozi.dto.response.MessageResponseDto;
import com.store.baozi.dto.response.OrderResponseDto;
import com.store.baozi.model.Client;
import com.store.baozi.model.Order;
import com.store.baozi.model.Product;
import com.store.baozi.repository.ClientRepository;
import com.store.baozi.repository.OrderRepository;
import com.store.baozi.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository repository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }


    public ResponseEntity<MessageResponseDto> create(@Valid OrderCreateDto dto) {
        Optional<Product> productOptional = productRepository.findByIdAndStockTrue(dto.productId());

        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Produto não encontrado ou sem estoque no momento."));
        }

        Product product = productOptional.get();

        Optional<Client> clientOptional = clientRepository.findById(dto.clientId());

        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Cliente não encontrado"));
        }

        Client client = clientOptional.get();

        Order order = new Order();

        order.setProduct(product);
        order.setClient(client);
        order.setAmount(dto.amount());

        repository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDto("Pedido criado com sucesso."));
    }

    public ResponseEntity<?> getById(Long orderId) {
        Optional<Order> orderOptional = repository.findById(orderId);

        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Pedido não encontrado"));
        }

        Order order = orderOptional.get();

        return ResponseEntity.ok(new OrderResponseDto(order.getId(), order.getClient().getId(), order.getProduct().getId(), order.getAmount()));
    }

    public ResponseEntity<?> getAll() {
        List<OrderResponseDto> list = repository.findAllByOrderByIdAsc().stream().map(
                o -> new OrderResponseDto(o.getId(), o.getClient().getId(), o.getProduct().getId(), o.getAmount())
        ).toList();

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Nenhum Pedido encontrado"));
        }

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<MessageResponseDto> deleteById(Long orderId) {

        Optional<Order> orderOptional = repository.findById(orderId);

        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Pedido não encontrado"));
        }

        Order order = orderOptional.get();

        repository.delete(order);

        return ResponseEntity.ok(new MessageResponseDto("Pedido deletado com sucesso."));
    }

    public ResponseEntity<?> update(Long orderId, OrderCreateDto dto) {
        Optional<Order> orderOptional = repository.findById(orderId);

        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Pedido não encontrado"));
        }

        Order order = orderOptional.get();

        Optional<Product> productOptional = productRepository.findById(dto.productId());

        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Produto não encontrado"));
        }

        Product product = productOptional.get();

        Optional<Client> clientOptional = clientRepository.findById(dto.clientId());

        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDto("Cliente não encontrado"));
        }

        Client client = clientOptional.get();

        order.setClient(client);
        order.setProduct(product);
        order.setAmount(dto.amount());

        repository.save(order);
        return ResponseEntity.ok(new OrderResponseDto(order.getId(), order.getClient().getId(), order.getProduct().getId(), order.getAmount()));
    }
}
