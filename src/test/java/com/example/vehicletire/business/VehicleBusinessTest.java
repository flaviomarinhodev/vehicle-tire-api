package com.example.vehicletire.business;

import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.dto.response.VehicleListResponseDTO;
import com.example.vehicletire.dto.response.VehicleResponseDTO;
import com.example.vehicletire.entity.VehicleStatus;
import com.example.vehicletire.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleBusinessTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleBusiness vehicleBusiness;

    private VehicleCreateRequestDTO createDTO;
    private VehicleResponseDTO responseDTO;
    private VehicleListResponseDTO listResponseDTO;

    @BeforeEach
    void setUp() {
        // Configurar DTO de criação
        createDTO = new VehicleCreateRequestDTO();
        createDTO.setPlaca("ABC1234");
        createDTO.setMarca("Toyota");
        createDTO.setQuilometragem(1000);
        createDTO.setStatus(VehicleStatus.ATIVO);

        // Configurar DTO de resposta
        responseDTO = new VehicleResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPlaca("ABC1234");
        responseDTO.setMarca("Toyota");
        responseDTO.setQuilometragem(1000);
        responseDTO.setStatus(VehicleStatus.ATIVO);
        responseDTO.setPneus(new ArrayList<>());

        // Configurar DTO de lista
        listResponseDTO = new VehicleListResponseDTO();
        listResponseDTO.setId(1L);
        listResponseDTO.setPlaca("ABC1234");
        listResponseDTO.setMarca("Toyota");
        listResponseDTO.setQuilometragem(1000);
        responseDTO.setStatus(VehicleStatus.ATIVO);
    }

    @Test
    void listarTodosDeveRetornarListaDeVeiculos() {
        // Arrange
        when(vehicleService.findAll()).thenReturn(Arrays.asList(listResponseDTO));

        // Act
        List<VehicleListResponseDTO> result = vehicleBusiness.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ABC1234", result.get(0).getPlaca());
        assertEquals("Toyota", result.get(0).getMarca());
        verify(vehicleService, times(1)).findAll();
    }

    @Test
    void buscarPorIdDeveRetornarVeiculoQuandoExiste() throws Exception {
        // Arrange
        when(vehicleService.findById(1L)).thenReturn(responseDTO);

        // Act
        VehicleResponseDTO result = vehicleBusiness.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("ABC1234", result.getPlaca());
        assertEquals("Toyota", result.getMarca());
        verify(vehicleService, times(1)).findById(1L);
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoVeiculoNaoExiste() throws Exception {
        // Arrange
        when(vehicleService.findById(anyLong()))
                .thenThrow(new RuntimeException("Veículo não encontrado"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleBusiness.buscarPorId(1L);
        });

        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(vehicleService, times(1)).findById(1L);
    }


    @Test
    void cadastrarDeveSalvarERetornarNovoVeiculo() throws Exception {
        // Arrange
        when(vehicleService.create(any(VehicleCreateRequestDTO.class))).thenReturn(responseDTO);

        // Act
        VehicleResponseDTO result = vehicleBusiness.cadastrar(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals("ABC1234", result.getPlaca());
        assertEquals("Toyota", result.getMarca());
        verify(vehicleService, times(1)).create(any(VehicleCreateRequestDTO.class));
    }

    @Test
    void cadastrarDeveLancarExcecaoQuandoServiceLancaExcecao() throws Exception {
        // Arrange
        when(vehicleService.create(any(VehicleCreateRequestDTO.class)))
                .thenThrow(new Exception("Já existe um veículo com a placa: ABC1234"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            vehicleBusiness.cadastrar(createDTO);
        });

        assertTrue(exception.getMessage().contains("Já existe um veículo"));
        verify(vehicleService, times(1)).create(any(VehicleCreateRequestDTO.class));
    }

    @Test
    void associarPneuDeveVincularPneuAoVeiculo() throws Exception {
        // Arrange
        doNothing().when(vehicleService).attachTire(anyLong(), anyLong(), anyString());

        // Act
        vehicleBusiness.associarPneu(1L, 1L, "A");

        // Assert
        verify(vehicleService, times(1)).attachTire(1L, 1L, "A");
    }

    @Test
    void associarPneuDeveLancarExcecaoQuandoServiceLancaExcecao() throws Exception {
        // Arrange
        doThrow(new Exception("Posição já está ocupada no veículo"))
                .when(vehicleService).attachTire(anyLong(), anyLong(), anyString());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            vehicleBusiness.associarPneu(1L, 1L, "A");
        });

        assertTrue(exception.getMessage().contains("Posição já está ocupada"));
        verify(vehicleService, times(1)).attachTire(1L, 1L, "A");
    }

    @Test
    void desassociarPneuDeveRemoverVinculo() throws Exception {
        // Arrange
        doNothing().when(vehicleService).detachTire(anyLong(), anyLong());

        // Act
        vehicleBusiness.desassociarPneu(1L, 1L);

        // Assert
        verify(vehicleService, times(1)).detachTire(1L, 1L);
    }

    @Test
    void desassociarPneuDeveLancarExcecaoQuandoServiceLancaExcecao() throws Exception {
        // Arrange
        doThrow(new Exception("Associação não encontrada"))
                .when(vehicleService).detachTire(anyLong(), anyLong());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            vehicleBusiness.desassociarPneu(1L, 1L);
        });

        assertTrue(exception.getMessage().contains("Associação não encontrada"));
        verify(vehicleService, times(1)).detachTire(1L, 1L);
    }

    @Test
    void listarTodosDeveRetornarListaVaziaQuandoNaoHaVeiculos() {
        // Arrange
        when(vehicleService.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<VehicleListResponseDTO> result = vehicleBusiness.listarTodos();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(vehicleService, times(1)).findAll();
    }
}