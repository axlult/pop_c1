package com.project.demo.logic.entity.clasegimnasio;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.util.Objects;
@Table(name = "axel_clasegimnacio")
@Entity
public class ClaseGimnasio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String horario;

    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private User entrenador;

    // Constructor por defecto
    public ClaseGimnasio() {
    }

    // Constructor con parámetros
    public ClaseGimnasio(String nombre, String horario) {
        this.nombre = nombre;
        this.horario = horario;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(User entrenador) {
        this.entrenador = entrenador;
    }

    // Métodos de negocio
    public void asignarEntrenador(User entrenador) {
        this.entrenador = entrenador;
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaseGimnasio that = (ClaseGimnasio) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(horario, that.horario) &&
                Objects.equals(entrenador, that.entrenador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, horario, entrenador);
    }

    @Override
    public String toString() {
        return "ClaseGimnasio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horario='" + horario + '\'' +
                ", entrenador=" + (entrenador != null ? entrenador.getUsername() : "null") +
                '}';
    }
}