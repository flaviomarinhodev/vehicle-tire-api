package com.example.vehicletire.business;

import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.dto.response.VehicleListResponseDTO;
import com.example.vehicletire.dto.response.VehicleResponseDTO;
import com.example.vehicletire.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleBusiness {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleBusiness(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public List<VehicleListResponseDTO> listarTodos() {
        return vehicleService.findAll();
    }

    public VehicleResponseDTO buscarPorId(Long id) throws Exception {
        return vehicleService.findById(id);
    }

    public VehicleResponseDTO cadastrar(VehicleCreateRequestDTO requestDTO) throws Exception {
        return vehicleService.create(requestDTO);
    }

    public void associarPneu(Long vehicleId, Long tireId, String posicao) throws Exception {
        vehicleService.attachTire(vehicleId, tireId, posicao);
    }

    public void desassociarPneu(Long vehicleId, Long tireId) throws Exception {
        vehicleService.detachTire(vehicleId, tireId);
    }
}
