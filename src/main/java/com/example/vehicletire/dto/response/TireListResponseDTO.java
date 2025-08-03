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
public class TireListResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("numero_fogo")
    private String numeroFogo;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("pressao_atual")
    private Double pressaoAtual;

    @JsonProperty("status")
    private String status;
}
