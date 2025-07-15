package com.project.demo.logic.entity.pago;

import com.project.demo.logic.entity.membresia.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByMembresia(Membresia membresia);
    List<Pago> findByFecha(LocalDate fecha);
    List<Pago> findByFechaBetween(LocalDate start, LocalDate end);
    List<Pago> findByMetodo(String metodo);
}