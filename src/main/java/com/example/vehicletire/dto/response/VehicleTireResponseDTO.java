package com.example.vehicletire.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleTireResponseDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("numeroFogo")
    private String numeroFogo;
    @JsonProperty("marca")
    private String marca;
    @JsonProperty("pressaoAtual")
    private Double pressaoAtual;
    @JsonProperty("posicao")
    private String posicao;

}
