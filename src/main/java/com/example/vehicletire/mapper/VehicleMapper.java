package com.example.vehicletire.mapper;

import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.dto.response.VehicleListResponseDTO;
import com.example.vehicletire.dto.response.VehicleResponseDTO;
import com.example.vehicletire.dto.response.VehicleTireResponseDTO;
import com.example.vehicletire.entity.Vehicle;
import com.example.vehicletire.entity.VehicleTire;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {

    public VehicleListResponseDTO toListResponseDTO(Vehicle vehicle) {
        return new VehicleListResponseDTO(
        );
    }

    public VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO(
        );

        if (vehicle.getVehicleTires() != null) {
            List<VehicleTireResponseDTO> pneus = vehicle.getVehicleTires()
                    .stream()
                    .map(this::toVehicleTireResponseDTO)
                    .collect(Collectors.toList());
            dto.setPneus(pneus);
        }

        return dto;
    }

    public Vehicle toEntity(VehicleCreateRequestDTO requestDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlaca(requestDTO.getPlaca());
        vehicle.setMarca(requestDTO.getMarca());
        vehicle.setQuilometragem(requestDTO.getQuilometragem());
        vehicle.setStatus(requestDTO.getStatus());
        return vehicle;
    }

    private VehicleTireResponseDTO toVehicleTireResponseDTO(VehicleTire vehicleTire) {
        VehicleTireResponseDTO dto = new VehicleTireResponseDTO();
        dto.setId(vehicleTire.getTire().getId());
        dto.setNumeroFogo(vehicleTire.getTire().getNumeroFogo());
        dto.setMarca(vehicleTire.getTire().getMarca());
        dto.setPressaoAtual(vehicleTire.getTire().getPressaoAtual());
        dto.setPosicao(vehicleTire.getPosicao());
        return dto;
    }
}
