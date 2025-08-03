package com.example.vehicletire.controller;

import com.example.vehicletire.business.TireBusiness;
import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
import com.example.vehicletire.entity.TireStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tires")
@Tag(name = "Pneus", description = "API para gerenciamento de pneus")
public class TireController {

    private final TireBusiness tireBusiness;

    @Autowired
    public TireController(TireBusiness tireBusiness) {
        this.tireBusiness = tireBusiness;
    }

    @Operation(summary = "Criar um novo pneu", description = "Cadastra um novo pneu no sistema")
    @PostMapping
    public ResponseEntity<TireResponseDTO> criarPneu(@Valid @RequestBody TireCreateRequestDTO dto) {
        TireResponseDTO tire = tireBusiness.cadastrarNovoPneu(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tire);
    }

    @Operation(summary = "Remover pneu", description = "Remove um pneu do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPneu(@PathVariable Long id) {
        tireBusiness.removerPneu(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Listar pneus disponíveis", description = "Retorna uma lista de todos os pneus com status disponível")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<TireListResponseDTO>> listarDisponiveis() {
        List<TireListResponseDTO> tires = tireBusiness.listarTodosDisponiveis();
        return ResponseEntity.status(HttpStatus.OK).body(tires);
    }

    @Operation(summary = "Listar pneus em uso", description = "Retorna uma lista de todos os pneus com status em uso")
    @GetMapping("/em-uso")
    public ResponseEntity<List<TireListResponseDTO>> listarEmUso() {
        List<TireListResponseDTO> tires = tireBusiness.listarTodosEmUso();
        return ResponseEntity.status(HttpStatus.OK).body(tires);
    }

    @Operation(summary = "Buscar pneus por marca", description = "Retorna uma lista de pneus filtrados pelas marcas especificadas")
    @GetMapping("/marca")
    public ResponseEntity<List<TireListResponseDTO>> buscarPorMarca(@RequestParam List<String> marca) {
        List<TireListResponseDTO> tires = tireBusiness.buscarPorMarca(marca);
        return ResponseEntity.status(HttpStatus.OK).body(tires);
    }

    @Operation(summary = "Buscar pneus por marca e status", description = "Retorna uma lista de pneus filtrados por marca e status específicos")
    @GetMapping("/marca-status")
    public ResponseEntity<List<TireListResponseDTO>> buscarPorMarcaEStatus(@RequestParam String marca, @RequestParam TireStatus status) {
        List<TireListResponseDTO> tires = tireBusiness.buscarPorMarcaEStatus(marca, status);
        return ResponseEntity.status(HttpStatus.OK).body(tires);
    }

    @Operation(summary = "Contar pneus disponíveis", description = "Retorna a quantidade total de pneus com status disponível")
    @GetMapping("/contar/disponiveis")
    public ResponseEntity<Long> contarDisponiveis() {
        Long count = tireBusiness.contarDisponiveis();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @Operation(summary = "Contar pneus em uso", description = "Retorna a quantidade total de pneus com status em uso")
    @GetMapping("/contar/em-uso")
    public ResponseEntity<Long> contarEmUso() {
        Long count = tireBusiness.contarEmUso();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
