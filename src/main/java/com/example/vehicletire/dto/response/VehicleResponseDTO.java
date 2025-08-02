package com.example.vehicletire.dto.response;

import com.example.vehicletire.entity.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("placa")
    private String placa;
    @JsonProperty("marca")
    private String marca;
    @JsonProperty("quilometragem")
    private Integer quilometragem;
    @JsonProperty("status")
    private VehicleStatus status;
    @JsonProperty("pneus")
    private List<VehicleTireResponseDTO> pneus;

}

