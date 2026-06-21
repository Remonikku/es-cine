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
import model.Pelicula;

/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAOPelicula {

    private Conexion conexion;


    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOPelicula() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }

    public void crearPelicula(Pelicula pelicula) {
        //id_pelicula, titulo, duracion, clasificacion
        String sql = "insert into peliculas (titulo, duracion, clasificacion) values (?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, pelicula.getTitulo());
            ps.setTime(2, Time.valueOf(pelicula.getDuracion()));
            ps.setString(3, pelicula.getClasificacion());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public List<Pelicula> getPelicula() throws SQLException {
        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
        String sql = "select * from peliculas;";
        conexion.rs = conexion.ejecutarSelect(sql);
        Pelicula pelicula;
        while (conexion.rs.next()) {
            pelicula = new Pelicula();
            pelicula.setIdPelicula(conexion.rs.getInt("id_pelicula"));
            pelicula.setTitulo(conexion.rs.getString("titulo"));
            pelicula.setDuracion(conexion.rs.getTime("duracion").toLocalTime());
            pelicula.setClasificacion(conexion.rs.getString("clasificacion"));
            listaPeliculas.add(pelicula);
        }
        return listaPeliculas;
    }

    public void eliminarPelicula(Pelicula pelicula) {
        String sql = "delete from peliculas where id_pelicula = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, pelicula.getIdPelicula());
            if (pelicula.getIdPelicula() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarPelicula(Pelicula pelicula) {
        String sql = "update peliculas set titulo = ?, duracion = ?, clasificacion = ? where id_pelicula = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, pelicula.getTitulo());
            ps.setTime(2, Time.valueOf(pelicula.getDuracion()));
            ps.setString(3, pelicula.getClasificacion());
            ps.setInt(4, pelicula.getIdPelicula());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println(""+ex);
        }

    }

}
