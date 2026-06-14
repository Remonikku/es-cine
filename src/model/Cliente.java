package model;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String correo;
    private String rut;
    
    public Cliente(){
    }
    public Cliente(int idCliente, String nombre, String correo, String rut){
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.rut = rut;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", nombre=" + nombre + ", correo=" + correo + ", rut=" + rut + '}';
    }
}

