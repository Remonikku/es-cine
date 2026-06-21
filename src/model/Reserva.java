package model;

import java.sql.Date;

public abstract class Reserva {
    private int idReserva;
    private int idCliente;
    private int idFuncion;
    private Date fechaReserva;
    private String tipoEntrada;
    private int precioFinal;

    public Reserva() {
    }

    public Reserva(int idReserva, int idCliente, int idFuncion, Date fechaReserva) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idFuncion = idFuncion;
        this.fechaReserva = fechaReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public int getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(int precioFinal) {
        this.precioFinal = precioFinal;
    }

    public abstract void calcularPrecio(double precioBase);

    @Override
    public String toString() {
        return "Reserva{" + "idReserva=" + idReserva + ", idCliente=" + idCliente + ", idFuncion=" + idFuncion + ", fechaReserva=" + fechaReserva + ", tipoEntrada=" + tipoEntrada + ", precioFinal=" + precioFinal + '}';
    }
}