package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.GrupoCurso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoCursoDAO {

    public List<GrupoCurso> obtenGrupoCursos() {
        List<GrupoCurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM prof_GrupoCurso";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GrupoCurso grupoCurso = new GrupoCurso(
                        rs.getLong("id"),
                        rs.getLong("grupo_id"),
                        rs.getLong("curso_id")
                );
                lista.add(grupoCurso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void insertarGrupoCurso(GrupoCurso grupoCurso) {
        String sql = "INSERT INTO prof_GrupoCurso (grupo_id, curso_id) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, grupoCurso.getGrupoId());
            ps.setLong(2, grupoCurso.getCursoId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}