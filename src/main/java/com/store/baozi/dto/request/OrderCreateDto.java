package com.store.baozi.dto.request;

import jakarta.validation.constraints.Min;

public record OrderCreateDto(
        Long clientId,
        Long productId,
        @Min(value = 1, message = "O mínimo de produtos permitidos para um pedido é 1.")
        Integer amount
) {
}
