package com.example.vehicletire.controller;

import com.example.vehicletire.business.VehicleBusiness;
import com.example.vehicletire.dto.AttachTireRequestDTO;
import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.dto.response.VehicleListResponseDTO;
import com.example.vehicletire.dto.response.VehicleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/veiculos")
@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
@Validated
public class VehicleController {

    private final VehicleBusiness vehicleBusiness;

    public VehicleController(VehicleBusiness vehicleBusiness) {
        this.vehicleBusiness = vehicleBusiness;
    }

    @Operation(summary = "Listar todos os veículos", description = "Retorna uma lista de todos os veículos cadastrados")
    @GetMapping
    public ResponseEntity<List<VehicleListResponseDTO>> getAllVehicles() {
        List<VehicleListResponseDTO> vehicles = vehicleBusiness.listarTodos();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) throws Exception {
        VehicleResponseDTO vehicle = vehicleBusiness.buscarPorId(id);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Adicionar um novo veículo", description = "Adiciona um novo veículo")
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleCreateRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO vehicle = vehicleBusiness.cadastrar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @Operation(summary = "Adiciona um pneu ao veículo", description = "Vincula um pneu ao veículo")
    @PostMapping("/{vehicleId}/pneus")
    public ResponseEntity<Void> attachTire(
            @PathVariable Long vehicleId,
            @Valid @RequestBody AttachTireRequestDTO requestDTO) throws Exception {
        vehicleBusiness.associarPneu(vehicleId, requestDTO.getTireId(), requestDTO.getPosicao());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Desvincula um pneu do veículo", description = "Desvincula um pneu ao veículo")
    @DeleteMapping("/{vehicleId}/pneus/{tireId}")
    public ResponseEntity<Void> detachTire(
            @PathVariable Long vehicleId,
            @PathVariable Long tireId) throws Exception {
        vehicleBusiness.desassociarPneu(vehicleId, tireId);
        return ResponseEntity.ok().build();
    }
}
