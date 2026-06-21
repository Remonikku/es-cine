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
import model.Reserva;
import model.ReservaFactory;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOReserva {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOReserva() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearReserva(Reserva reserva) {
        //fk_id_cliente, fk_id_funcion, fecha_reserva, tipo_entrada, precio_final
        String sql = "insert into reservas (fk_id_cliente, fk_id_funcion, fecha_reserva, tipo_entrada, precio_final) values (?,?,?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getIdFuncion());
            ps.setDate(3, reserva.getFechaReserva());
            ps.setString(4, reserva.getTipoEntrada());
            ps.setInt(5, reserva.getPrecioFinal());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }

    public List<Reserva> getReserva() throws SQLException {
        ArrayList<Reserva> listaReserva = new ArrayList<>();
        String sql = "select * from reservas;";
        conexion.rs = conexion.ejecutarSelect(sql);
        Reserva reserva;
        while (conexion.rs.next()) {
            String tipo = conexion.rs.getString("tipo_entrada");
            reserva = ReservaFactory.crearReservaDesdeDB(tipo);
            reserva.setIdReserva(conexion.rs.getInt("id_reserva"));
            reserva.setIdCliente(conexion.rs.getInt("fk_id_cliente"));
            reserva.setIdFuncion(conexion.rs.getInt("fk_id_funcion"));
            reserva.setFechaReserva(conexion.rs.getDate("fecha_reserva"));
            reserva.setPrecioFinal(conexion.rs.getInt("precio_final"));
            listaReserva.add(reserva);
        }
        return listaReserva;
    }

    public void eliminarReserva(Reserva reserva) {
        String sql = "delete from reservas where id_reserva = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, reserva.getIdReserva());
            if (reserva.getIdReserva() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarReserva(Reserva reserva) {
        String sql = "update reservas set fk_id_cliente = ?, fk_id_funcion = ?, fecha_reserva = ?, tipo_entrada = ?, precio_final = ? where id_reserva = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, reserva.getIdCliente());
            ps.setInt(2, reserva.getIdFuncion());
            ps.setDate(3, reserva.getFechaReserva());
            ps.setString(4, reserva.getTipoEntrada());
            ps.setInt(5, reserva.getPrecioFinal());
            ps.setInt(6, reserva.getIdReserva());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

}
