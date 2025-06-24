package org.demoClasePooc125.controller;

import org.demoClasePooc125.dao.GrupoCursoDAO;
import org.demoClasePooc125.model.GrupoCurso;

import java.util.List;

public class GrupoCursoController {

    private GrupoCursoDAO grupoCursoDAO = new GrupoCursoDAO();

    public List<GrupoCurso> obtenerGrupoCurso(){
        return grupoCursoDAO.obtenerGrupoCurso();
    }

    public void insertarGrupoCurso(GrupoCurso itemGrupoCurso){
        grupoCursoDAO.insertarGrupoCurso(itemGrupoCurso);
    }

    public void actualizarGrupoCurso(GrupoCurso itemGrupoCurso){
        grupoCursoDAO.actualizarGrupoCurso(itemGrupoCurso);
    }

    public void eliminarGrupoCurso(int idGrupoCurso){
        grupoCursoDAO.eliminarGrupoCurso(idGrupoCurso);
    }



}
