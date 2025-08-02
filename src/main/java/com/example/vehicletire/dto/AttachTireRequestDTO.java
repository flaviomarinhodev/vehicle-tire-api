package com.example.vehicletire.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachTireRequestDTO {

    @NotNull(message = "ID do pneu é obrigatório")
    private Long tireId;

    @NotBlank(message = "Posição é obrigatória")
    @Size(min = 1, max = 10, message = "Posição deve ter entre 1 e 10 caracteres")
    private String posicao;

}
