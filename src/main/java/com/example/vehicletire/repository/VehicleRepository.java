package com.example.vehicletire.repository;


import com.example.vehicletire.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v LEFT JOIN FETCH v.vehicleTires vt LEFT JOIN FETCH vt.tire WHERE v.id = :id")
    Optional<Vehicle> findByIdWithTires(@Param("id") Long id);

    Optional<Vehicle> findByPlaca(String placa);

    @Query("SELECT v FROM Vehicle v WHERE v.status = 'ATIVO'")
    List<Vehicle> findAllActive();
}
