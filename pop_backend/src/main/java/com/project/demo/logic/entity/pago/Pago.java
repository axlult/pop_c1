package com.project.demo.logic.entity.pago;

import com.project.demo.logic.entity.membresia.Membresia;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
@Table(name = "axel_pago")
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String metodo;

    @ManyToOne
    @JoinColumn(name = "membresia_id", nullable = false)
    private Membresia membresia;

    // Constructor por defecto
    public Pago() {
    }

    // Constructor con parámetros
    public Pago(double monto, LocalDate fecha, String metodo, Membresia membresia) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.membresia = membresia;
    }

    // Método para registrar el pago
    public void registrarPago() {
        // Lógica adicional si es necesario, como actualizar la membresía
        if (this.membresia != null) {
            this.membresia.renovar(30); // Ejemplo: renovar 30 días al pagar
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pago pago = (Pago) o;
        return Double.compare(pago.monto, monto) == 0 &&
                Objects.equals(id, pago.id) &&
                Objects.equals(fecha, pago.fecha) &&
                Objects.equals(metodo, pago.metodo) &&
                Objects.equals(membresia, pago.membresia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, monto, fecha, metodo, membresia);
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", metodo='" + metodo + '\'' +
                ", membresia=" + membresia +
                '}';
    }
}