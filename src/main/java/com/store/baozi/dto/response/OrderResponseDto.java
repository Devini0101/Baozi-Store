package com.store.baozi.dto.response;

public record OrderResponseDto(
        Long id,
        Long clientId,
        Long productId,
        Integer amount
) {
}
