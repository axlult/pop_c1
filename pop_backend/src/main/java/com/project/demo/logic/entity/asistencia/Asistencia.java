package com.project.demo.logic.entity.asistencia;

import com.project.demo.logic.entity.user.User; // Usando la entidad User existente
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
@Table(name = "axel_asistencia")
@Entity
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    // Relación con User existente (muchas asistencias pertenecen a un usuario)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor por defecto
    public Asistencia() {
    }

    // Constructor con parámetros
    public Asistencia(User user) {
        this.user = user;
        this.registrarIngreso();
    }

    // Método para registrar el ingreso (establece fecha y hora actual)
    public void registrarIngreso() {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asistencia that = (Asistencia) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(hora, that.hora) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, hora, user);
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", user=" + user +
                '}';
    }
}