package com.example.vehicletire.controller;

import com.example.vehicletire.entity.Tire;
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

import java.util.Arrays;
import java.util.List;

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

        Tire tire = new Tire();
        tire.setNumeroFogo(numeroFogoUnico);
        tire.setMarca("Michelin");
        tire.setPressaoAtual(32.0);
        tire.setStatus(TireStatus.DISPONIVEL);

        mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tire)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroFogo", is(numeroFogoUnico)))
                .andExpect(jsonPath("$.marca", is("Michelin")));
    }

    @Test
    void atualizarPneuDeveAtualizarPneuExistente() throws Exception {
        String numeroFogoUnico = "UPD" + (int)(Math.random() * 9000 + 1000);

        Tire tireOriginal = new Tire();
        tireOriginal.setNumeroFogo(numeroFogoUnico);
        tireOriginal.setMarca("Bridgestone");
        tireOriginal.setPressaoAtual(30.0);
        tireOriginal.setStatus(TireStatus.DISPONIVEL);

        MvcResult createResult = mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tireOriginal)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        Tire tireAtualizado = new Tire();
        tireAtualizado.setNumeroFogo(numeroFogoUnico);
        tireAtualizado.setMarca("Goodyear");
        tireAtualizado.setPressaoAtual(35.0);
        tireAtualizado.setStatus(TireStatus.EM_USO);

        mockMvc.perform(put("/api/v1/tires/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tireAtualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca", is("Goodyear")));
    }

    @Test
    void removerPneuDeveRemoverPneuExistente() throws Exception {
        String numeroFogoUnico = "DEL" + (int)(Math.random() * 9000 + 1000);

        Tire tire = new Tire();
        tire.setNumeroFogo(numeroFogoUnico);
        tire.setMarca("Continental");
        tire.setPressaoAtual(28.0);
        tire.setStatus(TireStatus.DISPONIVEL);

        MvcResult createResult = mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tire)))
                .andExpectAll(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/api/v1/tires/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorNumeroFogoDeveRetornar404QuandoNaoExiste() throws Exception {
        mockMvc.perform(get("/api/v1/tires/numero-fogo/{numeroFogo}", "INEXISTENTE123"))
                .andExpect(status().isNotFound());
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

    @Test
    void buscarPorMarcasDeveBuscarPorListaDeMarcas() throws Exception {
        List<String> marcas = Arrays.asList("Michelin", "Bridgestone", "Goodyear");

        mockMvc.perform(post("/api/v1/tires/buscar-por-marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marcas)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarPorIdComVeiculosDeveRetornarPneuComVeiculosQuandoExiste() throws Exception {
        String numeroFogoUnico = "VEH" + (int)(Math.random() * 9000 + 1000);

        Tire tire = new Tire();
        tire.setNumeroFogo(numeroFogoUnico);
        tire.setMarca("Yokohama");
        tire.setPressaoAtual(31.0);
        tire.setStatus(TireStatus.DISPONIVEL);

        MvcResult createResult = mockMvc.perform(post("/api/v1/tires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tire)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/api/v1/tires/{id}/veiculos", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroFogo", is(numeroFogoUnico)));
    }

    @Test
    void buscarPorIdComVeiculosDeveRetornar404QuandoNaoExiste() throws Exception {
        mockMvc.perform(get("/api/v1/tires/{id}/veiculos", 99999L))
                .andExpectAll(status().isNotFound());
    }
}
