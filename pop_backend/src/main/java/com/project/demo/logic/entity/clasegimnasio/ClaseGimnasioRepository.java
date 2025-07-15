package com.project.demo.logic.entity.clasegimnasio;

import com.project.demo.logic.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseGimnasioRepository extends JpaRepository<ClaseGimnasio, Long> {
    List<ClaseGimnasio> findByNombre(String nombre);
    List<ClaseGimnasio> findByEntrenador(User entrenador);
    List<ClaseGimnasio> findByNombreContainingIgnoreCase(String keyword);
}