package com.example.vehicletire.dto.request;

import com.example.vehicletire.entity.VehicleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleCreateRequestDTO {
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$",
            message = "Placa deve estar no formato AAA0000 ou AAA0A00")
    private String placa;

    @NotBlank(message = "Marca é obrigatória")
    private String marca;

    @NotNull
    @Min(value = 0, message = "Quilometragem deve ser maior ou igual a 0")
    private Integer quilometragem;

    @NotNull
    private VehicleStatus status;

}
