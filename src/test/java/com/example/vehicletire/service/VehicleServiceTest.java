package com.example.vehicletire.service;

import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.dto.response.VehicleListResponseDTO;
import com.example.vehicletire.dto.response.VehicleResponseDTO;
import com.example.vehicletire.entity.*;
import com.example.vehicletire.mapper.VehicleMapper;
import com.example.vehicletire.repository.TireRepository;
import com.example.vehicletire.repository.VehicleRepository;
import com.example.vehicletire.repository.VehicleTireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private TireRepository tireRepository;

    @Mock
    private VehicleTireRepository vehicleTireRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldCreateVehicleSuccessfully() throws Exception {
        VehicleCreateRequestDTO requestDTO = new VehicleCreateRequestDTO();
        requestDTO.setPlaca("ABC1234");
        requestDTO.setMarca("Toyota");
        requestDTO.setQuilometragem(50000);
        requestDTO.setStatus(VehicleStatus.ATIVO);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.findByPlaca("ABC1234")).thenReturn(Optional.empty());
        when(vehicleMapper.toEntity(requestDTO)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(new VehicleResponseDTO());

        VehicleResponseDTO result = vehicleService.create(requestDTO);

        assertNotNull(result);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldThrowExceptionWhenPlacaAlreadyExists() {
        VehicleCreateRequestDTO requestDTO = new VehicleCreateRequestDTO();
        requestDTO.setPlaca("ABC1234");

        when(vehicleRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(new Vehicle()));

        Exception exception = assertThrows(Exception.class, () -> {
            vehicleService.create(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Já existe um veículo com a placa"));
    }

    @Test
    void shouldReturnAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle1, vehicle2));
        when(vehicleMapper.toListResponseDTO(vehicle1)).thenReturn(new VehicleListResponseDTO());
        when(vehicleMapper.toListResponseDTO(vehicle2)).thenReturn(new VehicleListResponseDTO());

        List<VehicleListResponseDTO> result = vehicleService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldFindVehicleById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.findByIdWithTires(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toResponseDTO(vehicle)).thenReturn(new VehicleResponseDTO());

        VehicleResponseDTO response = vehicleService.findById(1L);

        assertNotNull(response);
    }

    @Test
    void shouldThrowExceptionWhenVehicleNotFoundById() {
        when(vehicleRepository.findByIdWithTires(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> vehicleService.findById(1L));
    }

    @Test
    void shouldAttachTireSuccessfully() throws Exception {
        Vehicle vehicle = new Vehicle();
        Tire tire = new Tire();
        tire.setStatus(TireStatus.DISPONIVEL);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(2L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByVehicleIdAndPosicao(1L, "Dianteiro")).thenReturn(false);

        vehicleService.attachTire(1L, 2L, "Dianteiro");

        verify(vehicleTireRepository).save(any(VehicleTire.class));
        assertEquals(TireStatus.EM_USO, tire.getStatus());
        verify(tireRepository).save(tire);
    }

    @Test
    void shouldThrowExceptionWhenVehicleNotFoundInAttachTire() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> vehicleService.attachTire(1L, 2L, "Dianteiro"));
        assertEquals("Veículo não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTireNotFoundInAttachTire() {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> vehicleService.attachTire(1L, 2L, "Dianteiro"));
        assertEquals("Pneu não encontrado", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTireNotAvailable() {
        Vehicle vehicle = new Vehicle();
        Tire tire = new Tire();
        tire.setStatus(TireStatus.EM_USO);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(2L)).thenReturn(Optional.of(tire));

        Exception ex = assertThrows(Exception.class, () -> vehicleService.attachTire(1L, 2L, "Dianteiro"));
        assertEquals("Pneu não está disponível", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPositionAlreadyOccupied() {
        Vehicle vehicle = new Vehicle();
        Tire tire = new Tire();
        tire.setStatus(TireStatus.DISPONIVEL);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(2L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByVehicleIdAndPosicao(1L, "Dianteiro")).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> vehicleService.attachTire(1L, 2L, "Dianteiro"));
        assertEquals("Posição já está ocupada no veículo", ex.getMessage());
    }

    @Test
    void shouldDetachTireSuccessfully() throws Exception {
        VehicleTire vehicleTire = new VehicleTire();
        Tire tire = new Tire();
        tire.setStatus(TireStatus.EM_USO);
        vehicleTire.setTire(tire);

        when(vehicleTireRepository.findByVehicleIdAndTireId(1L, 2L)).thenReturn(Optional.of(vehicleTire));

        vehicleService.detachTire(1L, 2L);

        verify(vehicleTireRepository).delete(vehicleTire);
        assertEquals(TireStatus.DISPONIVEL, tire.getStatus());
        verify(tireRepository).save(tire);
    }

    @Test
    void shouldThrowExceptionWhenAssociationNotFoundInDetachTire() {
        when(vehicleTireRepository.findByVehicleIdAndTireId(1L, 2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> vehicleService.detachTire(1L, 2L));
        assertEquals("Associação não encontrada", ex.getMessage());
    }
}
