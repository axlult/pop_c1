package com.project.demo.logic.entity.membresia;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
@Table(name = "axel_membresia")
@Entity
public class Membresia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, name = "fecha_inicio")
    private LocalDate inicio;

    @Column(nullable = false, name = "fecha_vencimiento")
    private LocalDate vencimiento;

    @Column(nullable = false)
    private String estado;

    // Constructor por defecto
    public Membresia() {
    }

    // Constructor con parámetros
    public Membresia(String tipo, LocalDate inicio, LocalDate vencimiento, String estado) {
        this.tipo = tipo;
        this.inicio = inicio;
        this.vencimiento = vencimiento;
        this.estado = estado;
    }

    // Métodos de negocio
    public void renovar(int diasExtension) {
        this.vencimiento = this.vencimiento.plusDays(diasExtension);
        this.estado = "Activa";
    }

    public void cancelar() {
        this.estado = "Cancelada";
    }

    public boolean esActiva() {
        return "Activa".equals(estado) && vencimiento.isAfter(LocalDate.now());
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

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membresia membresia = (Membresia) o;
        return Objects.equals(id, membresia.id) &&
                Objects.equals(tipo, membresia.tipo) &&
                Objects.equals(inicio, membresia.inicio) &&
                Objects.equals(vencimiento, membresia.vencimiento) &&
                Objects.equals(estado, membresia.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, inicio, vencimiento, estado);
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", inicio=" + inicio +
                ", vencimiento=" + vencimiento +
                ", estado='" + estado + '\'' +
                '}';
    }
}