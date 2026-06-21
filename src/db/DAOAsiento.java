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
import model.Asiento;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOAsiento {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOAsiento() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearAsiento(Asiento asiento) {
        // id_asiento, fk_id_sala, fila, numero
        String sql = "insert into asientos (id_asiento, fk_id_sala, fila, numero) values (?,?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, null);
            ps.setInt(2, asiento.getIdSala());
            ps.setString(3, asiento.getFila());
            ps.setInt(4, asiento.getNumero());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public List<Asiento> getAsiento() throws SQLException {
        ArrayList<Asiento> listaAsientos = new ArrayList<>();
        String sql = "select * from asientos;";
        conexion.rs = conexion.ejecutarSelect(sql);
        Asiento asiento;
        while (conexion.rs.next()) {
            asiento = new Asiento();
            asiento.setIdAsiento(conexion.rs.getInt("id_asiento"));
            asiento.setIdSala(conexion.rs.getInt("fk_id_sala"));
            asiento.setFila(conexion.rs.getString("fila"));
            asiento.setNumero(conexion.rs.getInt("numero"));
            listaAsientos.add(asiento);
        }
        return listaAsientos;
    }

    public void eliminarAsiento(Asiento asiento) {
        String sql = "delete from asientos where id_asiento = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, asiento.getIdAsiento());
            if (asiento.getIdAsiento() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarAsiento(Asiento asiento) {
        String sql = "update asientos set fk_id_sala = ?, fila = ?, numero = ? where id_asiento = ?;";
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, asiento.getIdSala());
            ps.setString(2, asiento.getFila());
            ps.setInt(3, asiento.getNumero());
            ps.setString(4, null);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

}
