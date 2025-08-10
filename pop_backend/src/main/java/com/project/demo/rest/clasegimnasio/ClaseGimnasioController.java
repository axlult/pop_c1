package com.project.demo.rest.clasegimnasio;

import com.project.demo.logic.entity.clasegimnasio.ClaseGimnasio;
import com.project.demo.logic.entity.clasegimnasio.ClaseGimnasioRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clases")
public class ClaseGimnasioController {

    @Autowired
    private ClaseGimnasioRepository claseRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear nueva clase
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'SUPER_ADMIN')")
    public ResponseEntity<ClaseGimnasio> createClase(@RequestBody ClaseGimnasioRequest request) {
        // Extraer el ID de entrenador del request
        Long entrenadorId = request.getEntrenadorId();

        if (entrenadorId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> entrenadorOpt = userRepository.findById(entrenadorId);
        if (entrenadorOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ClaseGimnasio clase = new ClaseGimnasio();
        clase.setNombre(request.getNombre());
        clase.setHorario(request.getHorario());
        clase.setEntrenador(entrenadorOpt.get());

        ClaseGimnasio nuevaClase = claseRepository.save(clase);
        return ResponseEntity.ok(nuevaClase);
    }

    // Clase DTO para request
    public static class ClaseGimnasioRequest {
        private String nombre;
        private String horario;
        private Long entrenadorId;

        // Getters y Setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getHorario() {
            return horario;
        }

        public void setHorario(String horario) {
            this.horario = horario;
        }

        public Long getEntrenadorId() {
            return entrenadorId;
        }

        public void setEntrenadorId(Long entrenadorId) {
            this.entrenadorId = entrenadorId;
        }
    }

    // Obtener todas las clases
    @GetMapping
    public List<ClaseGimnasio> getAllClases() {
        return claseRepository.findAll();
    }

    // Obtener clase por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClaseGimnasio> getClaseById(@PathVariable Long id) {
        Optional<ClaseGimnasio> clase = claseRepository.findById(id);
        return clase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar clase
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'SUPER_ADMIN')")
    public ResponseEntity<ClaseGimnasio> updateClase(
            @PathVariable Long id,
            @RequestBody ClaseGimnasioRequest request) {

        return claseRepository.findById(id)
                .map(clase -> {
                    clase.setNombre(request.getNombre());
                    clase.setHorario(request.getHorario());

                    if (request.getEntrenadorId() != null) {
                        Optional<User> entrenadorOpt = userRepository.findById(request.getEntrenadorId());
                        entrenadorOpt.ifPresent(clase::setEntrenador);
                    }

                    ClaseGimnasio updatedClase = claseRepository.save(clase);
                    return ResponseEntity.ok(updatedClase);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar clase
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Buscar clases por nombre
    @GetMapping("/buscar")
    public List<ClaseGimnasio> buscarClasesPorNombre(@RequestParam String nombre) {
        return claseRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener clases de un entrenador específico
    @GetMapping("/entrenador/{entrenadorId}")
    public List<ClaseGimnasio> getClasesPorEntrenador(@PathVariable Long entrenadorId) {
        User entrenador = new User();
        entrenador.setId(entrenadorId);
        return claseRepository.findByEntrenador(entrenador);
    }
}