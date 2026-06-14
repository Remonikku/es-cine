/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;


/**
 *
 * @author A
 *
 * @author Zemekis
 */
public class DAO {

    private Conexion conexion;
  

    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAO() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }
    


}
