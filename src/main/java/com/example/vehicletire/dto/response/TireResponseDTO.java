package com.example.vehicletire.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("numeroFogo")
    private String numeroFogo;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("pressaoAtual")
    private Double pressaoAtual;

    @JsonProperty("status")
    private String status;

    @JsonProperty("posicao")
    private String posicao;
}