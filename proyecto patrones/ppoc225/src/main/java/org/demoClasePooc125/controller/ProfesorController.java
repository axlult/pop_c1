package org.demoClasePooc125.controller;

import org.demoClasePooc125.dao.ProfesorDAO;
import org.demoClasePooc125.model.Profesor;

import java.util.List;

public class ProfesorController {

    private ProfesorDAO profesorDAO = new ProfesorDAO();

    public List<Profesor> obtenerProfesores() {
        return profesorDAO.obtenerProfesores();
    }

    public void insertarProfesor(Profesor profesor) {
        profesorDAO.insertarProfesor(profesor);
    }

    public void actualizarProfesor(Profesor profesor) {
        profesorDAO.actualizarProfesor(profesor);
    }
    public void eliminarProfesor(int idProfesor) {
        profesorDAO.eliminarProfesor(idProfesor);
    }
}
