package com.project.demo.rest.asistencia;

import com.project.demo.logic.entity.asistencia.Asistencia;
import com.project.demo.logic.entity.asistencia.AsistenciaRepository;
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
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UserRepository userRepository;

    // Registrar nueva asistencia
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'SUPER_ADMIN')")
    public ResponseEntity<Asistencia> registrarAsistencia(@RequestBody AsistenciaRequest request) {
        // Extraer el ID de usuario del objeto recibido
        Long userId = request.getUserId();

        // Si no viene en el campo directo, intentar extraerlo del objeto user
        if(userId == null && request.getUser() != null) {
            userId = request.getUser().getId();
        }

        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Asistencia asistencia = new Asistencia(userOpt.get());
        Asistencia nuevaAsistencia = asistenciaRepository.save(asistencia);
        return ResponseEntity.ok(nuevaAsistencia);
    }

    // Clase DTO mejorada
    public static class AsistenciaRequest {
        private Long userId;
        private UserIdDTO user;

        // Getters y Setters
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public UserIdDTO getUser() {
            return user;
        }

        public void setUser(UserIdDTO user) {
            this.user = user;
        }

        // DTO interno para el usuario
        public static class UserIdDTO {
            private Long id;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }
        }
    }


    // Obtener todas las asistencias
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'SUPER_ADMIN')")
    public List<Asistencia> getAllAsistencias() {
        return asistenciaRepository.findAll();
    }

    // Obtener asistencia por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'SUPER_ADMIN')")
    public ResponseEntity<Asistencia> getAsistenciaById(@PathVariable Long id) {
        Optional<Asistencia> asistencia = asistenciaRepository.findById(id);
        return asistencia.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener asistencias de un usuario
    @GetMapping("/usuario/{userId}")
    public List<Asistencia> getAsistenciasByUsuario(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return asistenciaRepository.findByUser(user);
    }

    // Obtener asistencias por fecha
    @GetMapping("/fecha/{fecha}")
    public List<Asistencia> getAsistenciasByFecha(@PathVariable String fecha) {
        return asistenciaRepository.findByFecha(LocalDate.parse(fecha));
    }

    // Contar asistencias de un usuario
    @GetMapping("/usuario/{userId}/count")
    public long countAsistenciasByUsuario(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return asistenciaRepository.countByUser(user);
    }

    // Eliminar asistencia
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAsistencia(@PathVariable Long id) {
        if (asistenciaRepository.existsById(id)) {
            asistenciaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}