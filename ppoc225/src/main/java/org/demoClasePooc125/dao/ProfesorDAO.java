package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Profesor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {

    public List<Profesor> obtenProfesores() {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT * FROM prof_Profesor";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Profesor profesor = new Profesor(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("email"),
                        rs.getString("departamento"),
                        rs.getBoolean("estado")
                );
                lista.add(profesor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void insertarProfesor(Profesor profesor) {
        String sql = "INSERT INTO prof_Profesor (nombre, identificacion, email, departamento, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getIdentificacion());
            ps.setString(3, profesor.getEmail());
            ps.setString(4, profesor.getDepartamento());
            ps.setBoolean(5, profesor.getEstado());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}