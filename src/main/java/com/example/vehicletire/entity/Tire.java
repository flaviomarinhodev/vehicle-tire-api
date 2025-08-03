package com.example.vehicletire.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tires")
public class Tire extends AbstractModel {

    @Column(unique = true, nullable = false)
    private String numeroFogo;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private Double pressaoAtual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TireStatus status;

    @OneToMany(mappedBy = "tire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VehicleTire> vehicleTires = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
