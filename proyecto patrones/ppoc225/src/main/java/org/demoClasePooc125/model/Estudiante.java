package org.demoClasePooc125.model;

import java.time.LocalDate;

public class Estudiante {

    private int id;
    private String nombre;
    private String identificacion;
    private String email;
    private LocalDate fecha_nacimiento;
    private String estado;

    // Constructor con ID (útil para lecturas desde la base de datos)
    public Estudiante(int id, String nombre, String identificacion, String email, LocalDate fecha_nacimiento, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado = estado;
    }

    // Constructor sin ID (útil para insertar nuevos registros)
    public Estudiante(String nombre, String identificacion, String email, LocalDate fecha_nacimiento, String estado) {
        this.id = 0;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado = estado;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getfecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setfecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
