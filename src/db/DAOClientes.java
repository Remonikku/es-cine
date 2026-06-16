/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.PreparedStatement;
import java.util.List;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cliente;

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

    public void crearClientes(Cliente cliente) {
        //nombre, correo, rut
        String sql = "insert into clientes (id_camion, nombre, correo, rut) values (?,?,?,?);";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getRut());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }

    public List<Cliente> getClientes() throws SQLException {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        String sql = "select * from clientes;";
        conexion.rs = conexion.ejecutarSelect(sql);
        Cliente cliente;
        while (conexion.rs.next()) {
            cliente = new Cliente();
            cliente.setIdCliente(conexion.rs.getInt("id_cliente"));
            cliente.setNombre(conexion.rs.getString("nombre"));
            cliente.setCorreo(conexion.rs.getString("correo"));
            cliente.setRut(conexion.rs.getString("rut"));
            listaClientes.add(cliente);
        }
        return listaClientes;
    }

    public void eliminarCliente(Cliente cliente) {
        String sql = "delete from clientes where id_cliente = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setInt(1, cliente.getIdCliente());
            if (cliente.getIdCliente() > 0) {
                ps.executeUpdate();
                ps.close();
            } else {
                System.err.println("ID inválida");
            }
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    public void actualizarCliente(Cliente cliente) {
        String sql = "update clientes set nombre = ?, correo = ?, rut = ? where id_cliente = ?;";
        try {
            PreparedStatement ps = (PreparedStatement) conexion.preparar(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCorreo());
            ps.setString(3, cliente.getRut());
            ps.setInt(4, cliente.getIdCliente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.err.println(""+ex);
        }
    }
    
    

}
