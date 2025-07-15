package com.project.demo.rest.clasegimnasio;

import com.project.demo.logic.entity.clasegimnasio.ClaseGimnasio;
import com.project.demo.logic.entity.clasegimnasio.ClaseGimnasioRepository;
import com.project.demo.logic.entity.rol.RoleEnum;
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

    // Crear nueva clase
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'SUPER_ADMIN')")
    public ClaseGimnasio createClase(@RequestBody ClaseGimnasio clase) {
        return claseRepository.save(clase);
    }

    // Actualizar clase
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENTRENADOR', 'SUPER_ADMIN')")
    public ResponseEntity<ClaseGimnasio> updateClase(
            @PathVariable Long id,
            @RequestBody ClaseGimnasio claseDetails) {

        return claseRepository.findById(id)
                .map(clase -> {
                    clase.setNombre(claseDetails.getNombre());
                    clase.setHorario(claseDetails.getHorario());
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

    // Asignar entrenador a clase
    @PostMapping("/{claseId}/asignar-entrenador/{entrenadorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ClaseGimnasio> asignarEntrenador(
            @PathVariable Long claseId,
            @PathVariable Long entrenadorId) {

        Optional<ClaseGimnasio> claseOpt = claseRepository.findById(claseId);
        Optional<User> entrenadorOpt = userRepository.findById(entrenadorId);

        if (claseOpt.isEmpty() || entrenadorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ClaseGimnasio clase = claseOpt.get();
        User entrenador = entrenadorOpt.get();

        // CORRECCIÓN: Acceder al nombre del rol a través del enum
        if (entrenador.getRole().getName() != RoleEnum.ENTRENADOR) {
            return ResponseEntity.badRequest().build();
        }

        clase.asignarEntrenador(entrenador);
        claseRepository.save(clase);
        return ResponseEntity.ok(clase);
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