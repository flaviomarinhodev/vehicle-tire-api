package com.example.vehicletire.repository;

import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<Tire, Long> {

    Optional<Tire> findByNumeroFogo(String numeroFogo);
    boolean existsByNumeroFogo(String numeroFogo);
    List<Tire> findByStatus(TireStatus status);

    @Query("SELECT t FROM Tire t WHERE t.status = 'DISPONIVEL'")
    List<Tire> findAllAvailable();

    @Query("SELECT t FROM Tire t WHERE t.status = 'EM_USO'")
    List<Tire> findAllInUse();

    List<Tire> findByMarcaIgnoreCaseContaining(String marca);

    List<Tire> findByMarcaIgnoreCaseContainingAndStatus(String marca, TireStatus status);

    @Query("SELECT t FROM Tire t WHERE t.pressaoAtual BETWEEN :minPressao AND :maxPressao")
    List<Tire> findByPressaoAtualBetween(@Param("minPressao") Double minPressao,
                                         @Param("maxPressao") Double maxPressao);


    @Query("SELECT t FROM Tire t WHERE t.pressaoAtual < :pressaoMinima AND t.status = 'EM_USO'")
    List<Tire> findWithLowPressure(@Param("pressaoMinima") Double pressaoMinima);

    @Query("SELECT COUNT(t) FROM Tire t WHERE t.status = 'DISPONIVEL'")
    long countAvailableTires();

    @Query("SELECT COUNT(t) FROM Tire t WHERE t.status = 'EM_USO'")
    long countTiresInUse();

    List<Tire> findAllByOrderByMarcaAscNumeroFogoAsc();

    @Query("SELECT t FROM Tire t LEFT JOIN FETCH t.vehicleTires vt LEFT JOIN FETCH vt.vehicle WHERE t.id = :id")
    Optional<Tire> findByIdWithVehicles(@Param("id") Long id);

    @Query("SELECT t FROM Tire t WHERE t.id NOT IN (SELECT vt.tire.id FROM VehicleTire vt)")
    List<Tire> findUnassignedTires();

    @Query("SELECT t FROM Tire t WHERE UPPER(t.marca) IN :marcas")
    List<Tire> findByMarcaIn(@Param("marcas") List<String> marcas);
}
