package model;

import java.sql.Time;
import java.math.BigDecimal;
import java.time.LocalTime;

public class Funcion {
    private int idFuncion;
    private int idPelicula;
    private int idSala;
    private LocalTime horario;
    private Double precio;
    
    public Funcion(){
    }

    public Funcion(int idFuncion, int idPelicula, int idSala, LocalTime horario, Double precio) {
        this.idFuncion = idFuncion;
        this.idPelicula = idPelicula;
        this.idSala = idSala;
        this.horario = horario;
        this.precio = precio;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public Double calcularIva(Double precio){
        Double precioOriginal = precio;
        Double precioConIva = precioOriginal * 0.19;
        return precioConIva;
    }

    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", idPelicula=" + idPelicula + ", idSala=" + idSala + ", horario=" + horario + ", precio=" + precio + '}';
    }
    
    
    
}
