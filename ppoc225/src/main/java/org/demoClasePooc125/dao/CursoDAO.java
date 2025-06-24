package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // Obtener todos los cursos
    public List<Curso> obtenCurso() {
        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM dario_Curso";

        try {
            Connection con = Conexion.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql); {
                while (rs.next()) {
                    Curso items = new Curso(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("estado")
                    );
                    lista.add(items);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Insertar un nuevo curso
    public void insertarCurso(Curso itemCurso) {
        String sql = "INSERT INTO dario_Curso(nombre, descripcion, estado) VALUES(?, ?, ?)";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {
                ps.setString(1, itemCurso.getNombre());
                ps.setString(2, itemCurso.getDescripcion());
                ps.setString(3, itemCurso.getEstado());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizar un curso existente
    public void actualizarCurso(Curso itemCurso) {
        String sql = "UPDATE dario_Curso SET nombre = ?, descripcion = ?, estado = ? WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {
                ps.setString(1, itemCurso.getNombre());
                ps.setString(2, itemCurso.getDescripcion());
                ps.setString(3, itemCurso.getEstado());
                ps.setInt(4, itemCurso.getId());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un curso por ID
    public void eliminarCurso(int id) {
        String sql = "DELETE FROM dario_Curso WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {
                ps.setInt(1, id);
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
