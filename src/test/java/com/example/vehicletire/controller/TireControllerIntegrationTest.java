package com.example.vehicletire.controller;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.entity.TireStatus;
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
class TireControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarPneuDeveCriarNovoPneu() throws Exception {
        String numeroFogoUnico = "PNU" + (int)(Math.random() * 9000 + 1000);

        TireCreateRequestDTO dto = new TireCreateRequestDTO();
        dto.setNumeroFogo(numeroFogoUnico);
        dto.setMarca("Michelin");
        dto.setPressaoAtual(32.0);
        dto.setStatus(TireStatus.DISPONIVEL);

        mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroFogo", is(numeroFogoUnico)))
                .andExpect(jsonPath("$.marca", is("Michelin")));
    }

    @Test
    void removerPneuDeveRemoverPneuExistente() throws Exception {
        String numeroFogoUnico = "DEL" + (int)(Math.random() * 9000 + 1000);

        TireCreateRequestDTO dto = new TireCreateRequestDTO();
        dto.setNumeroFogo(numeroFogoUnico);
        dto.setMarca("Continental");
        dto.setPressaoAtual(28.0);
        dto.setStatus(TireStatus.DISPONIVEL);

        MvcResult createResult = mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/api/v1/tires/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void listarDisponiveisDeveRetornarListaDisponiveis() throws Exception {
        mockMvc.perform(get("/api/v1/tires/disponiveis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(java.util.List.class)));
    }

    @Test
    void listarEmUsoDeveRetornarListaEmUso() throws Exception {
        mockMvc.perform(get("/api/v1/tires/em-uso"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(java.util.List.class)));
    }

    @Test
    void buscarPorMarcaDeveBuscarPorMarca() throws Exception {
        mockMvc.perform(get("/api/v1/tires/marca")
                        .param("marca", "Michelin", "Bridgestone"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(java.util.List.class)));
    }

    @Test
    void buscarPorMarcaEStatusDeveBuscarPorMarcaEStatus() throws Exception {
        mockMvc.perform(get("/api/v1/tires/marca-status")
                        .param("marca", "Michelin")
                        .param("status", "DISPONIVEL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(java.util.List.class)));
    }

    @Test
    void contarDisponiveisDeveRetornarQuantidadeDisponiveis() throws Exception {
        mockMvc.perform(get("/api/v1/tires/contar/disponiveis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(Number.class)));
    }

    @Test
    void contarEmUsoDeveRetornarQuantidadeEmUso() throws Exception {
        mockMvc.perform(get("/api/v1/tires/contar/em-uso"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", instanceOf(Number.class)));
    }
}
