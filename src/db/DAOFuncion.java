/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Funcion;
import model.Pelicula;
import model.Sala;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOFuncion {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOFuncion() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearFuncion(Funcion funcion) {
        //id_funcion, fk_id_pelicula, fk_id_sala, horario, precio
        String sql = "insert into funciones (fk_id_pelicula, fk_id_sala, horario, precio) values (?,?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, funcion.getIdPelicula());
            ps.setInt(2, funcion.getIdSala());
            ps.setTime(3, Time.valueOf(funcion.getHorario()));
            ps.setDouble(4, funcion.getPrecio());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public List<Funcion> getFuncion() {
        ArrayList<Funcion> listaFuncion = new ArrayList<>();
        String sql = "select * from funciones;";
        try {
            conexion.rs = conexion.ejecutarSelect(sql);
            Funcion funcion;
            while (conexion.rs.next()) {
                funcion = new Funcion();
                funcion.setIdFuncion(conexion.rs.getInt("id_funcion"));
                funcion.setIdPelicula(conexion.rs.getInt("fk_id_pelicula"));
                funcion.setIdSala(conexion.rs.getInt("fk_id_sala"));
                funcion.setHorario(conexion.rs.getTime("horario").toLocalTime());
                funcion.setPrecio(conexion.rs.getDouble("precio"));
                listaFuncion.add(funcion);
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
        return listaFuncion;
    }

    public void eliminarFuncion(Funcion funcion) {
        String sql = "delete from funciones where id_funcion = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, funcion.getIdFuncion());
            if (funcion.getIdFuncion() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarFuncion(Funcion funcion) {
        //id_funcion, fk_id_pelicula, fk_id_sala, horario, precio
        String sql = "update funciones set fk_id_pelicula = ?, fk_id_sala = ?, horario = ?, precio = ? where id_funcion = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, funcion.getIdPelicula());
            ps.setInt(2, funcion.getIdSala());
            ps.setTime(3, Time.valueOf(funcion.getHorario()));
            ps.setDouble(4, funcion.getPrecio());
            ps.setInt(5, funcion.getIdFuncion());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println(""+ex);
        }
        
    }
}
