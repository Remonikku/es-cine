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
import model.ReservaAsiento;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOReservaAsiento {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOReservaAsiento() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearResAsiento(ReservaAsiento resasiento) {
        //id_reserva_asiento, fk_id_reserva, fk_id_asiento
        String sql = "insert into reservas_asientos (id_reserva_asiento, fk_id_reserva, fk_id_asiento) values (?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, null);
            ps.setInt(2, resasiento.getIdReserva());
            ps.setInt(3, resasiento.getIdAsiento());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }

    public List<ReservaAsiento> getResAsiento() throws SQLException {
        ArrayList<ReservaAsiento> listaResAsiento = new ArrayList<>();
        String sql = "select * from reservas_asientos;";
        conexion.rs = conexion.ejecutarSelect(sql);
        ReservaAsiento resasiento;
        while (conexion.rs.next()) {
            resasiento = new ReservaAsiento();
            resasiento.setIdReservaAsiento(conexion.rs.getInt("id_reserva_asiento"));
            resasiento.setIdReserva(conexion.rs.getInt("fk_id_reserva"));
            resasiento.setIdAsiento(conexion.rs.getInt("fk_id_asiento"));
            listaResAsiento.add(resasiento);
        }
        return listaResAsiento;
    }

    public void eliminarResAsiento(ReservaAsiento resasiento) {
        String sql = "delete from reservas_asientos where id_reserva_asiento = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, resasiento.getIdReservaAsiento());
            if (resasiento.getIdReservaAsiento() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarResAsiento(ReservaAsiento resasiento) {
        String sql = "update reservas_asientos set fk_id_reserva = ?, fk_id_asiento = ? where id_reserva_asiento = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, resasiento.getIdReserva());
            ps.setInt(2, resasiento.getIdAsiento());
            ps.setInt(3, resasiento.getIdReservaAsiento());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

}
