package com.project.demo.rest.membresia;

import com.project.demo.logic.entity.membresia.Membresia;
import com.project.demo.logic.entity.membresia.MembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/membresias")
public class MembresiaController {

    @Autowired
    private MembresiaRepository membresiaRepository;

    // Obtener todas las membresías
    @GetMapping
    public List<Membresia> getAllMembresias() {
        return membresiaRepository.findAll();
    }

    // Obtener membresía por ID
    @GetMapping("/{id}")
    public ResponseEntity<Membresia> getMembresiaById(@PathVariable Long id) {
        Optional<Membresia> membresia = membresiaRepository.findById(id);
        return membresia.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva membresía
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Membresia createMembresia(@RequestBody Membresia membresia) {
        // Validación básica de fechas
        if (membresia.getVencimiento().isBefore(membresia.getInicio())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la de inicio");
        }

        // Establecer estado inicial si no viene definido
        if (membresia.getEstado() == null || membresia.getEstado().isEmpty()) {
            membresia.setEstado("Activa");
        }

        return membresiaRepository.save(membresia);
    }

    // Actualizar membresía
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Membresia> updateMembresia(
            @PathVariable Long id,
            @RequestBody Membresia membresiaDetails) {

        return membresiaRepository.findById(id)
                .map(membresia -> {
                    membresia.setTipo(membresiaDetails.getTipo());
                    membresia.setInicio(membresiaDetails.getInicio());
                    membresia.setVencimiento(membresiaDetails.getVencimiento());
                    membresia.setEstado(membresiaDetails.getEstado());

                    Membresia updatedMembresia = membresiaRepository.save(membresia);
                    return ResponseEntity.ok(updatedMembresia);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar membresía
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteMembresia(@PathVariable Long id) {
        if (membresiaRepository.existsById(id)) {
            membresiaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Renovar membresía
    @PostMapping("/{id}/renovar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Membresia> renovarMembresia(
            @PathVariable Long id,
            @RequestParam int diasExtension) {

        Optional<Membresia> optionalMembresia = membresiaRepository.findById(id);
        if (optionalMembresia.isPresent()) {
            Membresia membresia = optionalMembresia.get();
            membresia.renovar(diasExtension);
            membresiaRepository.save(membresia);
            return ResponseEntity.ok(membresia);
        }
        return ResponseEntity.notFound().build();
    }

    // Cancelar membresía
    @PostMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Membresia> cancelarMembresia(@PathVariable Long id) {
        Optional<Membresia> optionalMembresia = membresiaRepository.findById(id);
        if (optionalMembresia.isPresent()) {
            Membresia membresia = optionalMembresia.get();
            membresia.cancelar();
            membresiaRepository.save(membresia);
            return ResponseEntity.ok(membresia);
        }
        return ResponseEntity.notFound().build();
    }

    // Verificar si membresía está activa
    @GetMapping("/{id}/activa")
    public ResponseEntity<Boolean> isMembresiaActiva(@PathVariable Long id) {
        Optional<Membresia> optionalMembresia = membresiaRepository.findById(id);
        if (optionalMembresia.isPresent()) {
            return ResponseEntity.ok(optionalMembresia.get().esActiva());
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener membresías por estado
    @GetMapping("/estado/{estado}")
    public List<Membresia> getMembresiasByEstado(@PathVariable String estado) {
        return membresiaRepository.findByEstado(estado);
    }

    // Obtener membresías próximas a vencer
    @GetMapping("/proximas-vencer")
    public List<Membresia> getMembresiasProximasAVencer() {
        LocalDate fechaLimite = LocalDate.now().plusDays(7);
        return membresiaRepository.findByVencimientoBefore(fechaLimite);
    }
}