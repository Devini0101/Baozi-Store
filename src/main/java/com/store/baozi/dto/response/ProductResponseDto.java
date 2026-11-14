package com.store.baozi.dto.response;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        Boolean stock
) {
}
