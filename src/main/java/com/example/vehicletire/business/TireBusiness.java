package com.example.vehicletire.business;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.service.TireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TireBusiness {

    private final TireService tireService;

    @Autowired
    public TireBusiness(TireService tireService) {
        this.tireService = tireService;
    }

    public TireResponseDTO cadastrarNovoPneu(TireCreateRequestDTO tire) {
        if (tireService.existsByNumeroFogo(tire.getNumeroFogo())) {
            throw new IllegalArgumentException("Já existe um pneu com o número de fogo: " + tire.getNumeroFogo());
        }

        tire.setStatus(TireStatus.DISPONIVEL);
        return tireService.save(tire);
    }

    public void removerPneu(Long id) {
        TireResponseDTO existente = tireService.findById(id);
        if (existente == null) {
            throw new IllegalArgumentException("Pneu não encontrado para remoção com ID: " + id);
        }

        tireService.deleteById(id);
    }

    public List<TireListResponseDTO> listarTodosDisponiveis() {
        return tireService.findAllAvailable();
    }

    public List<TireListResponseDTO> listarTodosEmUso() {
        return tireService.findAllInUse();
    }

    public List<TireListResponseDTO> buscarPorMarca(List<String> marca) {
        return tireService.findByMarcas(marca);
    }

    public List<TireListResponseDTO> buscarPorMarcaEStatus(String marca, TireStatus status) {
        return tireService.findByMarcaContainingAndStatus(marca, status);
    }

    public Long contarDisponiveis() {
        return tireService.countAvailableTires();
    }

    public Long contarEmUso() {
        return tireService.countTiresInUse();
    }

}
