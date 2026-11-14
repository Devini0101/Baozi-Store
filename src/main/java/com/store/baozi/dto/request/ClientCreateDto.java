package com.store.baozi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClientCreateDto(

        @NotBlank(message = "Informe o nome para o cliente.")
        String name,

        @NotNull(message = "Informe a data inicial do cliente")
        LocalDate clientSince
) {
}
