package com.project.demo.logic.entity.reporte;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Table(name = "axel_reporte")
@Entity
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    @Lob // Para almacenar contenido grande (como PDF o CSV)
    @Column(nullable = false)
    private byte[] contenido;

    @Column(nullable = false)
    private String formato; // PDF, CSV, EXCEL, etc.

    // Relación con el usuario que generó el reporte
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    // Constructor por defecto
    public Reporte() {
        this.fechaGeneracion = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Reporte(String tipo, byte[] contenido, String formato, User usuario) {
        this.tipo = tipo;
        this.contenido = contenido;
        this.formato = formato;
        this.usuario = usuario;
        this.fechaGeneracion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reporte reporte = (Reporte) o;
        return Objects.equals(id, reporte.id) &&
                Objects.equals(tipo, reporte.tipo) &&
                Objects.equals(fechaGeneracion, reporte.fechaGeneracion) &&
                Objects.equals(formato, reporte.formato) &&
                Objects.equals(usuario, reporte.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, fechaGeneracion, formato, usuario);
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", fechaGeneracion=" + fechaGeneracion +
                ", formato='" + formato + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}