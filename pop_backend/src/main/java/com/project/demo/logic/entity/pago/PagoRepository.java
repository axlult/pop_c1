package com.project.demo.logic.entity.pago;

import com.project.demo.logic.entity.membresia.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Método actualizado para usar membresiaId
    List<Pago> findByMembresiaId(Long membresiaId);

    // Métodos existentes actualizados
    List<Pago> findByFecha(LocalDate fecha);

    List<Pago> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}