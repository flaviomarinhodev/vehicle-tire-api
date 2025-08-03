package com.example.vehicletire.dto.request;

import com.example.vehicletire.entity.TireStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TireCreateRequestDTO {

    @NotBlank(message = "Número do fogo é obrigatório")
    private String numeroFogo;

    @NotBlank(message = "Marca é obrigatória")
    private String marca;

    @NotNull(message = "Pressão atual é obrigatória")
    @Positive(message = "Pressão atual deve ser um valor positivo")
    private Double pressaoAtual;

    @NotNull(message = "Status é obrigatório")
    private TireStatus status;
}
