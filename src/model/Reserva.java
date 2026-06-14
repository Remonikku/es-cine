package model;

import java.sql.Date;

public class Reserva {
    private int idReserva;
    private Cliente cliente;
    private Funcion funcion;
    private Date fechaReserva;

    public Reserva() {
    }
    public Reserva(int idReserva, Cliente cliente, Funcion funcion, Date fechaReserva) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.funcion = funcion;
        this.fechaReserva = fechaReserva;
    }
    public int getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Funcion getFuncion() {
        return funcion;
    }
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    public Date getFechaReserva() {
        return fechaReserva;
    }
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    @Override
    public String toString() {
        return "Reserva{" + "idReserva=" + idReserva + ", cliente=" + cliente + ", funcion=" + funcion + ", fechaReserva=" + fechaReserva + '}';
    }
}