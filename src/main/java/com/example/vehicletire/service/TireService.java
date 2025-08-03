package com.example.vehicletire.service;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.mapper.TireMapper;
import com.example.vehicletire.repository.TireRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TireService {

    private final TireRepository tireRepository;
    private final TireMapper tireMapper;

    public TireService(TireRepository tireRepository, TireMapper tireMapper) {
        this.tireRepository = tireRepository;
        this.tireMapper = tireMapper;
    }

    public TireResponseDTO findByNumeroFogo(String numeroFogo) throws Exception {
        Tire tire = tireRepository.findByNumeroFogo(numeroFogo)
                .orElseThrow(() -> new Exception("Pneu não encontrado"));
        return tireMapper.toResponseDTO(tire);

    }


    public boolean existsByNumeroFogo(String numeroFogo) {
        return tireRepository.existsByNumeroFogo(numeroFogo);
    }

    public List<TireListResponseDTO> findByStatus(TireStatus status) {
        return tireRepository.findByStatus(status)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findAllAvailable() {
        return tireRepository.findAllAvailable()
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findAllInUse() {
        return tireRepository.findAllInUse()
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findByMarcaContaining(String marca) {
        return tireRepository.findByMarcaIgnoreCaseContaining(marca)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }


    public List<TireListResponseDTO> findByMarcaContainingAndStatus(String marca, TireStatus status) {
        return tireRepository.findByMarcaIgnoreCaseContainingAndStatus(marca, status)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findByPressaoBetween(Double min, Double max) {
        return tireRepository.findByPressaoAtualBetween(min, max)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findWithLowPressure(Double pressaoMinima) {
        return tireRepository.findWithLowPressure(pressaoMinima)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public long countAvailableTires() {
        return tireRepository.countAvailableTires();
    }

    public long countTiresInUse() {
        return tireRepository.countTiresInUse();
    }

    public List<TireListResponseDTO> findAllOrderedByMarcaAndNumeroFogo() {
        return tireRepository.findAllByOrderByMarcaAscNumeroFogoAsc()
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public TireResponseDTO findByIdWithVehicles(Long id) throws Exception {
        Tire byIdWithVehicles = tireRepository.findByIdWithVehicles(id)
                .orElseThrow(() -> new Exception("Pneu não encontrado"));
        return tireMapper.toResponseDTO(byIdWithVehicles);
    }

    public List<TireListResponseDTO> findUnassignedTires() {
        return tireRepository.findUnassignedTires()
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TireListResponseDTO> findByMarcas(List<String> marcas) {
        return tireRepository.findByMarcaIn(marcas)
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }

    public TireResponseDTO save(TireCreateRequestDTO requestDTO) {

        Tire tire = tireMapper.toEntity(requestDTO);
        Tire savedTire = tireRepository.save(tire);

        return tireMapper.toResponseDTO(savedTire);
    }

    public void deleteById(Long id) {
        tireRepository.deleteById(id);
    }

    public TireResponseDTO findById(Long id) {
        Tire tire = tireRepository.findById(id)
                .orElseThrow();
        return tireMapper.toResponseDTO(tire);
    }


    @Transactional
    public List<TireListResponseDTO> findAll() {
        return tireRepository.findAll()
                .stream()
                .map(tireMapper::toListResponseDTO)
                .collect(Collectors.toList());
    }
}
