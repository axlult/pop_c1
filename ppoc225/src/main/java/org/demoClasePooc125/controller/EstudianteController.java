package org.demoClasePooc125.controller;

import org.demoClasePooc125.dao.EstudianteDAO;
import org.demoClasePooc125.model.Estudiante;

import java.util.List;

public class EstudianteController {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    public List<Estudiante> obtenerEstudiantes() {
        return estudianteDAO.obtenerEstudiantes();
    }

    public void insertarEstudiante(Estudiante estudiante) {
        estudianteDAO.insertarEstudiante(estudiante);
    }


    public void actualizarEstudiante(Estudiante estudiante) {
        estudianteDAO.actualizarEstudiante(estudiante);
    }

    public void eliminarEstudiante(int id) {

        estudianteDAO.eliminarEstudiante(id);
    }
}




