package model;

import java.sql.Time;
import java.math.BigDecimal;

public class Funcion {
    private int idFuncion;
    private Pelicula pelicula;
    private Sala sala;
    private Time horario;
    private BigDecimal precio;
    
    public Funcion(){
    }
    public Funcion(int idFuncion, Pelicula pelicula, Sala sala, Time horario, BigDecimal precio){
        this.idFuncion = idFuncion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.horario = horario;
        this.precio = precio;
    }
    public int getIdFuncion() {
        return idFuncion;
    }
    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }
    public Pelicula getPelicula() {
        return pelicula;
    }
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
    public Sala getSala() {
        return sala;
    }
    public void setSala(Sala sala) {
        this.sala = sala;
    }
    public Time getHorario() {
        return horario;
    }
    public void setHorario(Time horario) {
        this.horario = horario;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", pelicula=" + pelicula + ", sala=" + sala + ", horario=" + horario + ", precio=" + precio + '}';
    }
}
