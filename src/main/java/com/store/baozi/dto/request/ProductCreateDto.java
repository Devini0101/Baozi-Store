package com.store.baozi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateDto(
        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(min = 3, message = "O nome deve ser maior que 3 caracteres.")
        String name,
        @NotNull(message = "O preço do produto deve ser informado.")
        BigDecimal price,
        @NotNull(message = "Informe se o produto está em estoque ou não.")
        Boolean stock
) {
}
