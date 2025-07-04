package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    // CRUD

    public List<Grupo> obtenGrupo() {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT * FROM dario_Grupo";

        try {
            Connection con = Conexion.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Grupo items = new Grupo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado")
                );
                lista.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Insertar un nuevo grupo
    public void insertarGrupo(Grupo itemGrupo) {
        String sql = "INSERT INTO dario_Grupo (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, itemGrupo.getNombre());
            ps.setString(2, itemGrupo.getDescripcion());
            ps.setString(3, itemGrupo.getEstado());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modificar un grupo existente
    public void actualizarGrupo(Grupo itemGrupo) {
        String sql = "UPDATE dario_Grupo SET nombre = ?, descripcion = ?, estado = ? WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, itemGrupo.getNombre());
            ps.setString(2, itemGrupo.getDescripcion());
            ps.setString(3, itemGrupo.getEstado());
            ps.setInt(4, itemGrupo.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un grupo por ID
    public void eliminarGrupo(int id) {
        String sql = "DELETE FROM dario_Grupo WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
