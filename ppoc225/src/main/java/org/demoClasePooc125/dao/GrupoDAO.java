package org.demoClasePooc125.dao;

import org.demoClasePooc125.misc.Conexion;
import org.demoClasePooc125.model.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    //CRUD

    public List<Grupo> obtenGrupo(){
        List<Grupo> lista = new ArrayList<Grupo>();

        String sql = "select * from prof_Grupo";

        try {
            Connection con = Conexion.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);{
                while (rs.next()) {
                    Grupo items = new Grupo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("estado")
                    );
                    //insertamos en la lista
                    lista.add(items);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    public void insertarGrupo(Grupo itemGrupo){
        String sql = "INSERT INTO prof_Grupo (nombre, descripcion, estado) VALUES (?, ?, ?)";


        try{
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);{

                ps.setString(1, itemGrupo.getNombre());
                ps.setString(2, itemGrupo.getDescripcion());
                ps.setString(3, itemGrupo.getEstado());
            }
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
