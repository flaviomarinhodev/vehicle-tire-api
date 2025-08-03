package com.example.vehicletire.business;

import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import com.example.vehicletire.service.TireService;
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
class TireBusinessTest {

    @Mock
    private TireService tireService;

    @InjectMocks
    private TireBusiness tireBusiness;

    private Tire tire;

    @BeforeEach
    void setUp() {
        tire = new Tire();
        tire.setId(1L);
        tire.setNumeroFogo("NF123");
        tire.setMarca("Michelin");
        tire.setPressaoAtual(32.5);
        tire.setStatus(TireStatus.DISPONIVEL);
    }

    @Test
    void cadastrarNovoPneuDeveSalvarERetornarPneu() {
        when(tireService.existsByNumeroFogo("NF123")).thenReturn(false);  // Adicionar este mock
        when(tireService.save(any(Tire.class))).thenReturn(tire);  // Alterado para tireService

        Tire result = tireBusiness.cadastrarNovoPneu(tire);

        assertNotNull(result);
        assertEquals("NF123", result.getNumeroFogo());
        assertEquals("Michelin", result.getMarca());
        verify(tireService, times(1)).save(any(Tire.class));  // Alterado para tireService
    }

    @Test
    void listarTodosDisponiveisDeveRetornarPneusDisponiveis() {
        when(tireService.findAllAvailable()).thenReturn(Arrays.asList(tire));  // Alterado para tireService

        List<Tire> result = tireBusiness.listarTodosDisponiveis();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NF123", result.get(0).getNumeroFogo());
    }

    @Test
    void buscarPorNumeroFogoDeveRetornarPneuQuandoExiste() {
        when(tireService.findByNumeroFogo("NF123")).thenReturn(Optional.of(tire));  // Alterado para tireService

        Optional<Tire> result = tireBusiness.buscarPorNumeroFogo("NF123");

        assertTrue(result.isPresent());
        assertEquals("NF123", result.get().getNumeroFogo());
    }

    @Test
    void atualizarPneuDeveAtualizarERetornarPneu() {
        when(tireService.findById(1L)).thenReturn(Optional.of(tire));  // Alterado para tireService
        when(tireService.save(any(Tire.class))).thenReturn(tire);  // Alterado para tireService

        Tire dadosAtualizados = new Tire();
        dadosAtualizados.setMarca("Pirelli");
        dadosAtualizados.setPressaoAtual(35.0);

        Tire result = tireBusiness.atualizarPneu(1L, dadosAtualizados);

        assertNotNull(result);
        verify(tireService, times(1)).save(any(Tire.class));  // Alterado para tireService
    }

    @Test
    void cadastrarNovoPneuDeveLancarExcecaoSeNumeroFogoJaExiste() {
        when(tireService.existsByNumeroFogo("NF123")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tireBusiness.cadastrarNovoPneu(tire);
        });

        assertTrue(exception.getMessage().contains("Já existe um pneu com o número de fogo"));
        verify(tireService, never()).save(any(Tire.class));
    }
}