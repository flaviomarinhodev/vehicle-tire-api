package com.example.vehicletire.mapper;

import com.example.vehicletire.dto.request.TireCreateRequestDTO;
import com.example.vehicletire.dto.response.TireResponseDTO;
import com.example.vehicletire.dto.response.TireListResponseDTO;
import com.example.vehicletire.entity.Tire;
import org.springframework.stereotype.Component;

    @Component
    public class TireMapper {

        public Tire toEntity(TireCreateRequestDTO requestDTO) {
            Tire tire = new Tire();
            tire.setNumeroFogo(requestDTO.getNumeroFogo());
            tire.setMarca(requestDTO.getMarca());
            tire.setPressaoAtual(requestDTO.getPressaoAtual());
            tire.setStatus(requestDTO.getStatus());
            return tire;
        }

        public TireResponseDTO toResponseDTO(Tire tire) {
            TireResponseDTO dto = new TireResponseDTO();
            dto.setId(tire.getId());
            dto.setNumeroFogo(tire.getNumeroFogo());
            dto.setMarca(tire.getMarca());
            dto.setPressaoAtual(tire.getPressaoAtual());
            dto.setStatus(tire.getStatus().name());

            return dto;
        }

        public TireListResponseDTO toListResponseDTO(Tire tire) {
            TireListResponseDTO dto = new TireListResponseDTO();
            dto.setId(tire.getId());
            dto.setNumeroFogo(tire.getNumeroFogo());
            dto.setMarca(tire.getMarca());
            dto.setPressaoAtual(tire.getPressaoAtual());
            dto.setStatus(tire.getStatus().name());
            return dto;
        }
    }



