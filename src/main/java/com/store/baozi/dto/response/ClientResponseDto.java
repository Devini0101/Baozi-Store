package com.store.baozi.dto.response;

import java.time.LocalDate;

public record ClientResponseDto(
        Long id,
        String name,
        LocalDate clientSince
) {
}
