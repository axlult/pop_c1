package com.project.demo.logic.entity.asistencia;

import com.project.demo.logic.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // Buscar asistencias por usuario
    List<Asistencia> findByUser(User user);

    // Buscar asistencias por fecha
    List<Asistencia> findByFecha(LocalDate fecha);

    // Buscar asistencias de un usuario en una fecha específica
    List<Asistencia> findByUserAndFecha(User user, LocalDate fecha);

    // Contar asistencias de un usuario
    long countByUser(User user);
}