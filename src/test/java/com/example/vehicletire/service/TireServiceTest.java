package com.example.vehicletire.service;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.mapper.TireMapper;
import com.example.vehicletire.repository.TireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TireServiceTest {

    @Mock
    private TireRepository tireRepository;

    @Mock
    private TireMapper tireMapper;

    @InjectMocks
    private TireService tireService;

    private Tire tire;
    private TireCreateRequestDTO createRequestDTO;
    private TireResponseDTO responseDTO;
    private TireListResponseDTO listResponseDTO;

    @BeforeEach
    void setUp() {
        tire = new Tire();
        tire.setId(1L);
        tire.setMarca("Michelin");
        tire.setStatus(TireStatus.DISPONIVEL);
        tire.setNumeroFogo("12345ABC");
        tire.setPressaoAtual(32.0);

        createRequestDTO = new TireCreateRequestDTO();
        createRequestDTO.setMarca("Michelin");
        createRequestDTO.setNumeroFogo("12345ABC");
        createRequestDTO.setPressaoAtual(32.0);
        createRequestDTO.setStatus(TireStatus.DISPONIVEL);

        responseDTO = new TireResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setMarca("Michelin");
        responseDTO.setNumeroFogo("12345ABC");
        responseDTO.setPressaoAtual(32.0);
        responseDTO.setStatus(TireStatus.DISPONIVEL.name());

        listResponseDTO = new TireListResponseDTO();
        listResponseDTO.setId(1L);
        listResponseDTO.setMarca("Michelin");
        listResponseDTO.setNumeroFogo("12345ABC");
        listResponseDTO.setStatus(TireStatus.DISPONIVEL.name());
    }

    @Test
    void testFindByNumeroFogo() throws Exception {
        when(tireRepository.findByNumeroFogo("12345ABC")).thenReturn(Optional.of(tire));
        when(tireMapper.toResponseDTO(tire)).thenReturn(responseDTO);

        TireResponseDTO result = tireService.findByNumeroFogo("12345ABC");

        assertNotNull(result);
        assertEquals("12345ABC", result.getNumeroFogo());
        verify(tireRepository).findByNumeroFogo("12345ABC");
        verify(tireMapper).toResponseDTO(tire);
    }

    @Test
    void testFindByNumeroFogoNotFound() {
        when(tireRepository.findByNumeroFogo("12345ABC")).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            tireService.findByNumeroFogo("12345ABC");
        });

        assertEquals("Pneu não encontrado", exception.getMessage());
        verify(tireRepository).findByNumeroFogo("12345ABC");
        verify(tireMapper, never()).toResponseDTO(any());
    }

    @Test
    void testExistsByNumeroFogo() {
        when(tireRepository.existsByNumeroFogo("12345ABC")).thenReturn(true);

        boolean exists = tireService.existsByNumeroFogo("12345ABC");

        assertTrue(exists);
        verify(tireRepository).existsByNumeroFogo("12345ABC");
    }

    @Test
    void testFindByStatus() {
        when(tireRepository.findByStatus(TireStatus.DISPONIVEL)).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findByStatus(TireStatus.DISPONIVEL);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findByStatus(TireStatus.DISPONIVEL);
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindAllAvailable() {
        when(tireRepository.findAllAvailable()).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findAllAvailable();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findAllAvailable();
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindAllInUse() {
        when(tireRepository.findAllInUse()).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findAllInUse();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findAllInUse();
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindByMarcaContaining() {
        when(tireRepository.findByMarcaIgnoreCaseContaining("Michelin")).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findByMarcaContaining("Michelin");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findByMarcaIgnoreCaseContaining("Michelin");
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindByMarcaContainingAndStatus() {
        when(tireRepository.findByMarcaIgnoreCaseContainingAndStatus("Michelin", TireStatus.DISPONIVEL))
                .thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findByMarcaContainingAndStatus("Michelin", TireStatus.DISPONIVEL);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findByMarcaIgnoreCaseContainingAndStatus("Michelin", TireStatus.DISPONIVEL);
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindByPressaoBetween() {
        when(tireRepository.findByPressaoAtualBetween(30.0, 35.0)).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findByPressaoBetween(30.0, 35.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findByPressaoAtualBetween(30.0, 35.0);
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindWithLowPressure() {
        when(tireRepository.findWithLowPressure(25.0)).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findWithLowPressure(25.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findWithLowPressure(25.0);
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testCountAvailableTires() {
        when(tireRepository.countAvailableTires()).thenReturn(5L);

        long count = tireService.countAvailableTires();

        assertEquals(5L, count);
        verify(tireRepository).countAvailableTires();
    }

    @Test
    void testCountTiresInUse() {
        when(tireRepository.countTiresInUse()).thenReturn(3L);

        long count = tireService.countTiresInUse();

        assertEquals(3L, count);
        verify(tireRepository).countTiresInUse();
    }

    @Test
    void testFindAllOrderedByMarcaAndNumeroFogo() {
        when(tireRepository.findAllByOrderByMarcaAscNumeroFogoAsc()).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findAllOrderedByMarcaAndNumeroFogo();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findAllByOrderByMarcaAscNumeroFogoAsc();
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindByIdWithVehicles() throws Exception {
        when(tireRepository.findByIdWithVehicles(1L)).thenReturn(Optional.of(tire));
        when(tireMapper.toResponseDTO(tire)).thenReturn(responseDTO);

        TireResponseDTO result = tireService.findByIdWithVehicles(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tireRepository).findByIdWithVehicles(1L);
        verify(tireMapper).toResponseDTO(tire);
    }

    @Test
    void testFindByIdWithVehiclesNotFound() {
        when(tireRepository.findByIdWithVehicles(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            tireService.findByIdWithVehicles(1L);
        });

        assertEquals("Pneu não encontrado", exception.getMessage());
        verify(tireRepository).findByIdWithVehicles(1L);
        verify(tireMapper, never()).toResponseDTO(any());
    }

    @Test
    void testFindUnassignedTires() {
        when(tireRepository.findUnassignedTires()).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findUnassignedTires();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findUnassignedTires();
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testFindByMarcas() {
        List<String> marcas = Arrays.asList("Michelin", "Pirelli");
        when(tireRepository.findByMarcaIn(marcas)).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findByMarcas(marcas);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findByMarcaIn(marcas);
        verify(tireMapper).toListResponseDTO(tire);
    }

    @Test
    void testSave() {
        when(tireMapper.toEntity(createRequestDTO)).thenReturn(tire);
        when(tireRepository.save(tire)).thenReturn(tire);
        when(tireMapper.toResponseDTO(tire)).thenReturn(responseDTO);

        TireResponseDTO result = tireService.save(createRequestDTO);

        assertNotNull(result);
        assertEquals("12345ABC", result.getNumeroFogo());
        verify(tireMapper).toEntity(createRequestDTO);
        verify(tireRepository).save(tire);
        verify(tireMapper).toResponseDTO(tire);
    }

    @Test
    void testDeleteById() {
        doNothing().when(tireRepository).deleteById(1L);

        tireService.deleteById(1L);

        verify(tireRepository).deleteById(1L);
    }

    @Test
    void testFindById() {
        when(tireRepository.findById(1L)).thenReturn(Optional.of(tire));
        when(tireMapper.toResponseDTO(tire)).thenReturn(responseDTO);

        TireResponseDTO result = tireService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tireRepository).findById(1L);
        verify(tireMapper).toResponseDTO(tire);
    }

    @Test
    void testFindAll() {
        when(tireRepository.findAll()).thenReturn(Arrays.asList(tire));
        when(tireMapper.toListResponseDTO(tire)).thenReturn(listResponseDTO);

        List<TireListResponseDTO> result = tireService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tireRepository).findAll();
        verify(tireMapper).toListResponseDTO(tire);
    }
}
