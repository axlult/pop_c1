package com.project.demo.rest.pago;

import com.project.demo.logic.entity.membresia.Membresia;
import com.project.demo.logic.entity.membresia.MembresiaRepository;
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

    @Autowired
    private MembresiaRepository membresiaRepository;

    // Registrar un nuevo pago
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'SUPER_ADMIN')")
    public ResponseEntity<Pago> registrarPago(
            @RequestBody Pago pago,
            @RequestParam Long membresiaId) {

        Optional<Membresia> membresiaOpt = membresiaRepository.findById(membresiaId);
        if (membresiaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        pago.setMembresia(membresiaOpt.get());
        pago.registrarPago(); // Ejecutar lógica de negocio
        Pago nuevoPago = pagoRepository.save(pago);
        return ResponseEntity.ok(nuevoPago);
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

    // Obtener pagos por membresía
    @GetMapping("/membresia/{membresiaId}")
    public List<Pago> getPagosByMembresia(@PathVariable Long membresiaId) {
        Membresia membresia = new Membresia();
        membresia.setId(membresiaId);
        return pagoRepository.findByMembresia(membresia);
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

    // Eliminar pago (solo super admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}