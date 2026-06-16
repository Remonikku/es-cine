/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
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
    
    public void crearFuncion(Funcion funcion, Pelicula pelicula, Sala sala) {
        //id_funcion, fk_id_pelicula, fk_id_sala, horario, precio
        String sql = "insert into funciones (id_funcion, fk_id_pelicula, fk_id_sala, horario, precio);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, funcion.getIdFuncion());
            ps.setInt(2, pelicula.getIdPelicula());
            ps.setInt(3, sala.getIdSala());
            ps.setTime(4, Time.valueOf(funcion.getHorario()));
            ps.setDouble(5, funcion.getPrecio());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println(""+ex);
        }
    }
    
    public List<Funcion> getFuncion(){
        String sql = "select * from funciones;";
        try {
            conexion.rs = conexion.ejecutarSelect(sql);
            Funcion funcion;
            
        } catch (SQLException ex) {
            System.err.println(""+ex);
        }
        
    }


}
