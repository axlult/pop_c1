package com.project.demo.rest.membresia;

import com.project.demo.logic.entity.membresia.Membresia;
import com.project.demo.logic.entity.membresia.MembresiaRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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

    // Obtener membresía por ID de usuario
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<Membresia> getMembresiaByUsuario(@PathVariable Long userId) {
        Optional<Membresia> membresia = membresiaRepository.findByUserId(userId);
        return membresia.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva membresía
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Membresia> createMembresia(@RequestBody Membresia membresia) {
        // Validar que el usuario existe
        if (membresia.getUser() == null || membresia.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOpt = userRepository.findById(membresia.getUser().getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Validación básica de fechas
        if (membresia.getVencimiento() != null &&
                membresia.getInicio() != null &&
                membresia.getVencimiento().isBefore(membresia.getInicio())) {
            return ResponseEntity.badRequest().build();
        }

        // Establecer estado inicial si no viene definido
        if (membresia.getEstado() == null || membresia.getEstado().isEmpty()) {
            membresia.setEstado("Activa");
        }

        // Asignar el usuario completo
        membresia.setUser(userOpt.get());

        Membresia nuevaMembresia = membresiaRepository.save(membresia);
        return ResponseEntity.ok(nuevaMembresia);
    }

    // Actualizar membresía
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Membresia> updateMembresia(
            @PathVariable Long id,
            @RequestBody Membresia membresiaDetails) {

        return membresiaRepository.findById(id)
                .map(membresia -> {
                    // Actualizar campos básicos
                    if (membresiaDetails.getTipo() != null) {
                        membresia.setTipo(membresiaDetails.getTipo());
                    }

                    if (membresiaDetails.getInicio() != null) {
                        membresia.setInicio(membresiaDetails.getInicio());
                    }

                    if (membresiaDetails.getVencimiento() != null) {
                        // Validar fecha de vencimiento
                        if (membresia.getInicio() != null &&
                                membresiaDetails.getVencimiento().isBefore(membresia.getInicio())) {
                            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la de inicio");
                        }
                        membresia.setVencimiento(membresiaDetails.getVencimiento());
                    }

                    if (membresiaDetails.getEstado() != null) {
                        membresia.setEstado(membresiaDetails.getEstado());
                    }

                    // Actualizar usuario si se proporciona
                    if (membresiaDetails.getUser() != null && membresiaDetails.getUser().getId() != null) {
                        Optional<User> userOpt = userRepository.findById(membresiaDetails.getUser().getId());
                        if (userOpt.isPresent()) {
                            membresia.setUser(userOpt.get());
                        } else {
                            throw new IllegalArgumentException("Usuario no encontrado");
                        }
                    }

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
            Membresia updated = membresiaRepository.save(membresia);
            return ResponseEntity.ok(updated);
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
            Membresia updated = membresiaRepository.save(membresia);
            return ResponseEntity.ok(updated);
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

    // Obtener membresías activas con información de usuario
    @GetMapping("/activas")
    public ResponseEntity<List<Membresia>> getMembresiasActivas() {
        List<Membresia> activas = membresiaRepository.findMembresiasActivas();
        return ResponseEntity.ok(activas);
    }
}