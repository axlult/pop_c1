package com.project.demo.logic.entity.membresia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    // Buscar membresías por estado
    List<Membresia> findByEstado(String estado);

    // Buscar membresía por ID de usuario
    Optional<Membresia> findByUserId(Long userId);

    // Buscar membresías que vencen antes de cierta fecha
    List<Membresia> findByVencimientoBefore(LocalDate fecha);

    // Buscar membresías activas
    @Query("SELECT m FROM Membresia m WHERE m.estado = 'Activa' AND m.vencimiento > CURRENT_DATE")
    List<Membresia> findMembresiasActivas();

    // Buscar por tipo de membresía
    Optional<Membresia> findByTipo(String tipo);
}