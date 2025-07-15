package com.project.demo.logic.entity.reporte;

import com.project.demo.logic.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
    List<Reporte> findByUsuario(User usuario);
    List<Reporte> findByFechaGeneracionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Reporte> findByFormato(String formato);
}