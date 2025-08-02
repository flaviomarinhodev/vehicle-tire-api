package com.example.vehicletire.repository;

import com.example.vehicletire.entity.VehicleTire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTireRepository extends JpaRepository<VehicleTire, Long> {

    @Query("SELECT COUNT(vt) > 0 FROM VehicleTire vt WHERE vt.vehicle.id = :vehicleId AND vt.posicao = :posicao")
    boolean existsByVehicleIdAndPosicao(@Param("vehicleId") Long vehicleId, @Param("posicao") String posicao);

    @Query("SELECT vt FROM VehicleTire vt WHERE vt.vehicle.id = :vehicleId AND vt.tire.id = :tireId")
    Optional<VehicleTire> findByVehicleIdAndTireId(@Param("vehicleId") Long vehicleId, @Param("tireId") Long tireId);

    @Modifying
    @Query("DELETE FROM VehicleTire vt WHERE vt.vehicle.id = :vehicleId AND vt.tire.id = :tireId")
    void deleteByVehicleIdAndTireId(@Param("vehicleId") Long vehicleId, @Param("tireId") Long tireId);
}
