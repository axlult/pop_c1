package org.demoClasePooc125.model;

public class GrupoCurso {

    private int id;
    private int grupoId;
    private int cursoId;
    private String groupName;
    private String cursoName;
    public GrupoCurso(int id, int grupoId, int cursoId) {
        this.id = id;
        this.grupoId = grupoId;
        this.cursoId = cursoId;
    }

    // Constructor sin ID (cuando vas a insertar uno nuevo)
    public GrupoCurso(int grupoId, int cursoId) {
        this.id = 0;
        this.grupoId = grupoId;
        this.cursoId = cursoId;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCursoName() {
        return cursoName;
    }

    public void setCursoName(String cursoName) {
        this.cursoName = cursoName;
    }

}

