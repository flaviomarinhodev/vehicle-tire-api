package com.example.vehicletire.service;

import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.repository.TireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TireService {

    private final TireRepository tireRepository;

    @Autowired
    public TireService(TireRepository tireRepository) {
        this.tireRepository = tireRepository;
    }

    public Optional<Tire> findByNumeroFogo(String numeroFogo) {
        return tireRepository.findByNumeroFogo(numeroFogo);
    }

    public boolean existsByNumeroFogo(String numeroFogo) {
        return tireRepository.existsByNumeroFogo(numeroFogo);
    }

    public List<Tire> findByStatus(TireStatus status) {
        return tireRepository.findByStatus(status);
    }

    public List<Tire> findAllAvailable() {
        return tireRepository.findAllAvailable();
    }

    public List<Tire> findAllInUse() {
        return tireRepository.findAllInUse();
    }

    public List<Tire> findByMarcaContaining(String marca) {
        return tireRepository.findByMarcaIgnoreCaseContaining(marca);
    }

    public List<Tire> findByMarcaContainingAndStatus(String marca, TireStatus status) {
        return tireRepository.findByMarcaIgnoreCaseContainingAndStatus(marca, status);
    }

    public List<Tire> findByPressaoBetween(Double min, Double max) {
        return tireRepository.findByPressaoAtualBetween(min, max);
    }

    public List<Tire> findWithLowPressure(Double pressaoMinima) {
        return tireRepository.findWithLowPressure(pressaoMinima);
    }

    public long countAvailableTires() {
        return tireRepository.countAvailableTires();
    }

    public long countTiresInUse() {
        return tireRepository.countTiresInUse();
    }

    public List<Tire> findAllOrderedByMarcaAndNumeroFogo() {
        return tireRepository.findAllByOrderByMarcaAscNumeroFogoAsc();
    }

    public Optional<Tire> findByIdWithVehicles(Long id) {
        return tireRepository.findByIdWithVehicles(id);
    }

    public List<Tire> findUnassignedTires() {
        return tireRepository.findUnassignedTires();
    }

    public List<Tire> findByMarcas(List<String> marcas) {
        return tireRepository.findByMarcaIn(marcas);
    }

    public Tire save(Tire tire) {
        return tireRepository.save(tire);
    }

    public void deleteById(Long id) {
        tireRepository.deleteById(id);
    }

    public Optional<Tire> findById(Long id) {
        return tireRepository.findById(id);
    }

    public List<Tire> findAll() {
        return tireRepository.findAll();
    }
}
