package org.demoClasePooc125.model;

import java.time.LocalDate;

public class Profesor {

    private int id;
    private String nombre;
    private String identificacion;
    private String email;
    private String departamento;
    private String estado;

    public Profesor(int id, String nombre, String identificacion, String email, String departamento, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
        this.departamento = departamento;
        this.estado = estado;
    }

    public Profesor(String nombre, String identificacion, String email, String departamento, String estado) {
        this.id = 0;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
        this.departamento = departamento;
        this.estado = estado;
    }

    //Getters y Setters
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;

    }
}
