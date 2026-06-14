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
public class DAOClientes {

    private Conexion conexion;
  

    /*
     constructor de DAO
     Genera la conexion entregando los datos
     */
    public DAOClientes() throws SQLException {
        conexion = new Conexion(
                "localhost",
                "es_cine",
                "root",
                ""//pass
        );
    }
    
    

}
