package com.example.vehicletire.business;

import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.service.TireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TireBusiness {

    private final TireService tireService;

    @Autowired
    public TireBusiness(TireService tireService) {
        this.tireService = tireService;
    }

    public Tire cadastrarNovoPneu(Tire tire) {
        if (tireService.existsByNumeroFogo(tire.getNumeroFogo())) {
            throw new IllegalArgumentException("Já existe um pneu com o número de fogo: " + tire.getNumeroFogo());
        }

        tire.setStatus(TireStatus.DISPONIVEL);
        return tireService.save(tire);
    }

    public Tire atualizarPneu(Long id, Tire dadosAtualizados) {
        Tire existente = tireService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pneu não encontrado com ID: " + id));

        existente.setMarca(dadosAtualizados.getMarca());
        existente.setPressaoAtual(dadosAtualizados.getPressaoAtual());
        existente.setStatus(dadosAtualizados.getStatus());

        return tireService.save(existente);
    }

    public void removerPneu(Long id) {
        if (!tireService.findById(id).isPresent()) {
            throw new IllegalArgumentException("Pneu não encontrado para remoção com ID: " + id);
        }

        tireService.deleteById(id);
    }

    public List<Tire> listarTodosDisponiveis() {
        return tireService.findAllAvailable();
    }

    public List<Tire> listarTodosEmUso() {
        return tireService.findAllInUse();
    }

    public List<Tire> buscarPorMarca(String marca) {
        return tireService.findByMarcaContaining(marca);
    }

    public List<Tire> buscarPorMarcaEStatus(String marca, TireStatus status) {
        return tireService.findByMarcaContainingAndStatus(marca, status);
    }

    public List<Tire> listarPressaoEntre(double min, double max) {
        return tireService.findByPressaoBetween(min, max);
    }

    public List<Tire> listarComPressaoBaixa(double pressaoMinima) {
        return tireService.findWithLowPressure(pressaoMinima);
    }

    public Optional<Tire> buscarPorNumeroFogo(String numeroFogo) {
        return tireService.findByNumeroFogo(numeroFogo);
    }

    public Optional<Tire> buscarPorIdComVeiculos(Long id) {
        return tireService.findByIdWithVehicles(id);
    }

    public List<Tire> listarNaoAtribuidos() {
        return tireService.findUnassignedTires();
    }

    public long contarDisponiveis() {
        return tireService.countAvailableTires();
    }

    public long contarEmUso() {
        return tireService.countTiresInUse();
    }

    public List<Tire> listarOrdenadosPorMarcaENumero() {
        return tireService.findAllOrderedByMarcaAndNumeroFogo();
    }

    public List<Tire> buscarPorMarcas(List<String> marcas) {
        return tireService.findByMarcas(marcas);
    }
}
