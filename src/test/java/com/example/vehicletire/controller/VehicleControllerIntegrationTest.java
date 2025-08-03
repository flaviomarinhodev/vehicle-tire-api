package com.example.vehicletire.controller;

import com.example.vehicletire.dto.request.VehicleCreateRequestDTO;
import com.example.vehicletire.entity.VehicleStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVehiclesDeveRetornarListaDeVeiculos() throws Exception {
        mockMvc.perform(get("/api/v1/veiculos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createVehicleDeveCriarNovoVeiculo() throws Exception {
        String placaUnica = "AAA" + (int)(Math.random() * 9000 + 1000);

        VehicleCreateRequestDTO vehicle = new VehicleCreateRequestDTO();
        vehicle.setPlaca(placaUnica);
        vehicle.setMarca("Toyota");
        vehicle.setQuilometragem(1000);
        vehicle.setStatus(VehicleStatus.ATIVO);

        mockMvc.perform(post("/api/v1/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.placa", is(placaUnica)))
                .andExpect(jsonPath("$.marca", is("Toyota")));
    }

    @Test
    void getVehicleByIdDeveRetornarVeiculoQuandoExiste() throws Exception {
        String placaUnica = "BBB" + (int)(Math.random() * 9000 + 1000);

        VehicleCreateRequestDTO vehicle = new VehicleCreateRequestDTO();
        vehicle.setPlaca(placaUnica);
        vehicle.setMarca("Honda");
        vehicle.setQuilometragem(2000);
        vehicle.setStatus(VehicleStatus.ATIVO);

        MvcResult createResult = mockMvc.perform(post("/api/v1/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/api/v1/veiculos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.placa", is(placaUnica)))
                .andExpect(jsonPath("$.marca", is("Honda")));
    }

}