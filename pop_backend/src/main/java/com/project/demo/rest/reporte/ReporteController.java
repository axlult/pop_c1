package com.project.demo.rest.reporte;

import com.project.demo.logic.entity.reporte.Reporte;
import com.project.demo.logic.entity.reporte.ReporteRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.service.reporte.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteRepository reporteRepository;

    // Generar reporte de asistencias
    @PostMapping("/asistencias")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Reporte> generarReporteAsistencias(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = (User) authentication.getPrincipal();

        LocalDate inicio = fechaInicio != null ? LocalDate.parse(fechaInicio) : LocalDate.now().minusMonths(1);
        LocalDate fin = fechaFin != null ? LocalDate.parse(fechaFin) : LocalDate.now();

        Reporte reporte = reporteService.generarReporteAsistencias(inicio, fin, usuario);
        return ResponseEntity.ok(reporte);
    }

    // Generar reporte de pagos
    @PostMapping("/pagos")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'SUPER_ADMIN')")
    public ResponseEntity<Reporte> generarReportePagos(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = (User) authentication.getPrincipal();

        LocalDate inicio = fechaInicio != null ? LocalDate.parse(fechaInicio) : LocalDate.now().minusMonths(1);
        LocalDate fin = fechaFin != null ? LocalDate.parse(fechaFin) : LocalDate.now();

        Reporte reporte = reporteService.generarReportePagos(inicio, fin, usuario);
        return ResponseEntity.ok(reporte);
    }

    // Generar reporte de membresías
    @PostMapping("/membresias")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Reporte> generarReporteMembresias() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = (User) authentication.getPrincipal();

        Reporte reporte = reporteService.generarReporteMembresias(usuario);
        return ResponseEntity.ok(reporte);
    }

    // Descargar un reporte generado
    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarReporte(@PathVariable Long id) {
        return reporteRepository.findById(id)
                .map(reporte -> {
                    String filename = "reporte_" + reporte.getTipo().toLowerCase() + "." + reporte.getFormato().toLowerCase();

                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                            .body(reporte.getContenido());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener todos los reportes generados
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    // Obtener reportes por tipo
    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Reporte> getReportesByTipo(@PathVariable String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    // Obtener reportes por usuario
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Reporte> getReportesByUsuario(@PathVariable Long usuarioId) {
        User usuario = new User();
        usuario.setId(usuarioId);
        return reporteRepository.findByUsuario(usuario);
    }

    // Eliminar reporte
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        if (reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}