package org.demoClasePooc125.controller;

import org.demoClasePooc125.dao.CursoDAO;
import org.demoClasePooc125.model.Curso;

import java.util.List;
public class CursoController {

    private CursoDAO cursoDAO = new CursoDAO();

    public List<Curso> obtenCurso(){
        return cursoDAO.obtenCurso();
    }

    public void insertarCurso(Curso itemCurso){
        cursoDAO.insertarCurso(itemCurso);
    }

    public void actualizarCurso(Curso itemCurso){
        cursoDAO.actualizarCurso(itemCurso);
    }

    public void eliminarCurso(int idCurso){
        cursoDAO.eliminarCurso(idCurso);
    }

}
