package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    // Obtener todos los estudiantes
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();

        String sql = "SELECT * FROM dario_Estudiante";

        try {
            Connection con = Conexion.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql); {

                while (rs.next()) {
                    Estudiante estudiante = new Estudiante(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("identificacion"),
                            rs.getString("email"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("estado")
                    );
                    lista.add(estudiante);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Insertar un nuevo estudiante
    public void insertarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO dario_Estudiante (nombre, identificacion, email, fecha_nacimiento, estado) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {

                ps.setString(1, estudiante.getNombre());
                ps.setString(2, estudiante.getIdentificacion());
                ps.setString(3, estudiante.getEmail());
                ps.setDate(4, Date.valueOf(estudiante.getfecha_nacimiento()));
                ps.setString(5, estudiante.getEstado());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizar un estudiante existente
    public void actualizarEstudiante(Estudiante estudiante) {
        String sql = "UPDATE dario_Estudiante SET nombre = ?, identificacion = ?, email = ?, fecha_nacimiento = ?, estado = ? WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {

                ps.setString(1, estudiante.getNombre());
                ps.setString(2, estudiante.getIdentificacion());
                ps.setString(3, estudiante.getEmail());
                ps.setDate(4, Date.valueOf(estudiante.getfecha_nacimiento()));
                ps.setString(5, estudiante.getEstado());
                ps.setInt(6, estudiante.getId());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un estudiante por ID
    public void eliminarEstudiante(int id) {
        String sql = "DELETE FROM dario_Estudiante WHERE id = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql); {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
