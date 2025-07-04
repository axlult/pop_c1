package org.demoClasePooc125.controller;

import org.demoClasePooc125.dao.GrupoDAO;
import org.demoClasePooc125.model.Grupo;

import java.util.List;

public class GrupoController {

    private GrupoDAO grupoDAO = new GrupoDAO();

    public List<Grupo> obtenGrupo(){
        return grupoDAO.obtenGrupo();
    }

    public void insertarGrupo(Grupo itemGrupo) {
        grupoDAO.insertarGrupo(itemGrupo);
    }

    public void actualizarGrupo(Grupo itemGrupo) {
        grupoDAO.actualizarGrupo(itemGrupo);
    }

    public void eliminarGrupo(int idGrupo){
        grupoDAO.eliminarGrupo(idGrupo);
    }

}