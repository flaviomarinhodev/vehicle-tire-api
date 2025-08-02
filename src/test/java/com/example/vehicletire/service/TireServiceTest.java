package com.example.vehicletire.service;

import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.repository.TireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TireServiceTest {

    @Mock
    private TireRepository tireRepository;

    @InjectMocks
    private TireService tireService;

    private Tire tire;

    @BeforeEach
    void setUp() {
        tire = new Tire();
        tire.setId(1L);
        tire.setMarca("Michelin");
        tire.setStatus(TireStatus.DISPONIVEL);
        tire.setNumeroFogo("12345ABC");
    }

    @Test
    void testFindByNumeroFogo() {
        when(tireRepository.findByNumeroFogo("12345ABC")).thenReturn(Optional.of(tire));

        Optional<Tire> result = tireService.findByNumeroFogo("12345ABC");

        assertTrue(result.isPresent());
        assertEquals(tire.getId(), result.get().getId());
        verify(tireRepository).findByNumeroFogo("12345ABC");
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
        when(tireRepository.findByStatus(TireStatus.DISPONIVEL)).thenReturn(List.of(tire));

        List<Tire> tires = tireService.findByStatus(TireStatus.DISPONIVEL);

        assertFalse(tires.isEmpty());
        assertEquals(1, tires.size());
        verify(tireRepository).findByStatus(TireStatus.DISPONIVEL);
    }

    @Test
    void testSave() {
        when(tireRepository.save(tire)).thenReturn(tire);

        Tire saved = tireService.save(tire);

        assertNotNull(saved);
        assertEquals(tire.getId(), saved.getId());
        verify(tireRepository).save(tire);
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

        Optional<Tire> found = tireService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(tire.getId(), found.get().getId());
        verify(tireRepository).findById(1L);
    }

    @Test
    void testFindAll() {
        when(tireRepository.findAll()).thenReturn(List.of(tire));

        List<Tire> allTires = tireService.findAll();

        assertEquals(1, allTires.size());
        verify(tireRepository).findAll();
    }

}
