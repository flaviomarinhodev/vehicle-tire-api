package com.example.vehicletire.business;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TireBusinessTest {

    @Mock
    private TireService tireService;

    @InjectMocks
    private TireBusiness tireBusiness;

    private TireCreateRequestDTO createRequestDTO;
    private TireResponseDTO responseDTO;
    private TireListResponseDTO listResponseDTO;

    @BeforeEach
    void setUp() {
        createRequestDTO = new TireCreateRequestDTO();
        createRequestDTO.setNumeroFogo("NF123");
        createRequestDTO.setMarca("Michelin");
        createRequestDTO.setPressaoAtual(32.5);
        createRequestDTO.setStatus(TireStatus.DISPONIVEL);

        responseDTO = new TireResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNumeroFogo("NF123");
        responseDTO.setMarca("Michelin");
        responseDTO.setPressaoAtual(32.5);
        responseDTO.setStatus(TireStatus.DISPONIVEL.name());

        listResponseDTO = new TireListResponseDTO();
        listResponseDTO.setId(1L);
        listResponseDTO.setNumeroFogo("NF123");
        listResponseDTO.setMarca("Michelin");
        listResponseDTO.setStatus(TireStatus.DISPONIVEL.name());
    }

    @Test
    void cadastrarNovoPneuDeveSalvarERetornarPneu() {
        when(tireService.existsByNumeroFogo("NF123")).thenReturn(false);
        when(tireService.save(any(TireCreateRequestDTO.class))).thenReturn(responseDTO);

        TireResponseDTO result = tireBusiness.cadastrarNovoPneu(createRequestDTO);

        assertNotNull(result);
        assertEquals("NF123", result.getNumeroFogo());
        assertEquals("Michelin", result.getMarca());
        assertEquals(TireStatus.DISPONIVEL, createRequestDTO.getStatus());
        verify(tireService, times(1)).save(any(TireCreateRequestDTO.class));
    }

    @Test
    void cadastrarNovoPneuDeveLancarExcecaoSeNumeroFogoJaExiste() {
        when(tireService.existsByNumeroFogo("NF123")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tireBusiness.cadastrarNovoPneu(createRequestDTO);
        });

        assertTrue(exception.getMessage().contains("Já existe um pneu com o número de fogo"));
        verify(tireService, never()).save(any(TireCreateRequestDTO.class));
    }

    @Test
    void removerPneuDeveRemoverPneuExistente() {
        when(tireService.findById(1L)).thenReturn(responseDTO);
        doNothing().when(tireService).deleteById(1L);

        assertDoesNotThrow(() -> tireBusiness.removerPneu(1L));

        verify(tireService, times(1)).findById(1L);
        verify(tireService, times(1)).deleteById(1L);
    }

    @Test
    void removerPneuDeveLancarExcecaoSeNaoExiste() {
        when(tireService.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tireBusiness.removerPneu(1L);
        });

        assertTrue(exception.getMessage().contains("Pneu não encontrado para remoção com ID"));
        verify(tireService, never()).deleteById(1L);
    }

    @Test
    void listarTodosDisponiveisDeveRetornarPneusDisponiveis() {
        when(tireService.findAllAvailable()).thenReturn(Arrays.asList(listResponseDTO));

        List<TireListResponseDTO> result = tireBusiness.listarTodosDisponiveis();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NF123", result.get(0).getNumeroFogo());
        verify(tireService, times(1)).findAllAvailable();
    }

    @Test
    void buscarPorMarcaDeveRetornarPneusPorMarca() {
        List<String> marcas = Arrays.asList("Michelin", "Pirelli");
        when(tireService.findByMarcas(marcas)).thenReturn(Arrays.asList(listResponseDTO));

        List<TireListResponseDTO> result = tireBusiness.buscarPorMarca(marcas);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Michelin", result.get(0).getMarca());
        verify(tireService, times(1)).findByMarcas(marcas);
    }

    @Test
    void contarDisponiveisDeveRetornarQuantidadeDisponiveis() {
        when(tireService.countAvailableTires()).thenReturn(5L);

        Long result = tireBusiness.contarDisponiveis();

        assertEquals(5L, result);
        verify(tireService, times(1)).countAvailableTires();
    }

    @Test
    void contarEmUsoDeveRetornarQuantidadeEmUso() {
        when(tireService.countTiresInUse()).thenReturn(3L);

        Long result = tireBusiness.contarEmUso();

        assertEquals(3L, result);
        verify(tireService, times(1)).countTiresInUse();
    }
}
