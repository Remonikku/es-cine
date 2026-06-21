/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Sala;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOSala {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOSala() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearSala(Sala sala) {
        //id_sala, capacidad, nombre_sala
        String sql = "insert into salas (id_sala, capacidad, nombre_sala) values (?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, null);
            ps.setInt(2, sala.getCapacidad());
            ps.setString(3, sala.getNombreSala());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }

    public List<Sala> getSala() throws SQLException {
        ArrayList<Sala> listaSala = new ArrayList<>();
        String sql = "select * from salas;";
        conexion.rs = conexion.ejecutarSelect(sql);
        Sala sala;
        while (conexion.rs.next()) {
            sala = new Sala();
            sala.setIdSala(conexion.rs.getInt("id_sala"));
            sala.setCapacidad(conexion.rs.getInt("capacidad"));
            sala.setNombreSala(conexion.rs.getString("nombre_sala"));
            listaSala.add(sala);
        }
        return listaSala;
    }

    public void eliminarSala(Sala sala) {
        String sql = "delete from salas where id_sala = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, sala.getIdSala());
            if (sala.getIdSala() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarSala(Sala sala) {
        String sql = "update clientes set capacidad = ?, nombre_sala = ?, where id_sala = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, sala.getCapacidad());
            ps.setString(2, sala.getNombreSala());
            ps.setInt(3, sala.getIdSala());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }
}
