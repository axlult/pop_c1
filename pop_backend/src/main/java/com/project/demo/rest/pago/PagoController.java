package com.project.demo.rest.pago;

import com.project.demo.logic.entity.pago.Pago;
import com.project.demo.logic.entity.pago.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoRepository pagoRepository;

    // Registrar un nuevo pago
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'SUPER_ADMIN')")
    public ResponseEntity<Pago> registrarPago(@RequestBody PagoRequest request) {
        // Crear entidad Pago directamente
        Pago pago = new Pago();
        pago.setMonto(request.getMonto());
        pago.setFecha(request.getFecha());
        pago.setMetodo(request.getMetodo());
        pago.setMembresiaId(request.getMembresiaId());

        // Guardar en base de datos
        Pago nuevoPago = pagoRepository.save(pago);

        return ResponseEntity.ok(nuevoPago);
    }

    // Clase DTO para recibir la solicitud de pago
    public static class PagoRequest {
        private double monto;
        private LocalDate fecha;
        private String metodo;
        private Long membresiaId;

        // Getters y Setters
        public double getMonto() {
            return monto;
        }

        public void setMonto(double monto) {
            this.monto = monto;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public String getMetodo() {
            return metodo;
        }

        public void setMetodo(String metodo) {
            this.metodo = metodo;
        }

        public Long getMembresiaId() {
            return membresiaId;
        }

        public void setMembresiaId(Long membresiaId) {
            this.membresiaId = membresiaId;
        }
    }

    // Obtener todos los pagos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'SUPER_ADMIN')")
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    // Obtener pago por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'SUPER_ADMIN')")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        Optional<Pago> pago = pagoRepository.findById(id);
        return pago.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Obtener pagos por fecha
    @GetMapping("/fecha/{fecha}")
    public List<Pago> getPagosByFecha(@PathVariable String fecha) {
        return pagoRepository.findByFecha(LocalDate.parse(fecha));
    }

    // Obtener pagos por rango de fechas
    @GetMapping("/rango-fechas")
    public List<Pago> getPagosByRangoFechas(
            @RequestParam String inicio,
            @RequestParam String fin) {

        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);
        return pagoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    // Eliminar pago
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}