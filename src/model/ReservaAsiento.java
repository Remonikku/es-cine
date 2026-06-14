package model;

public class ReservaAsiento {
    private int idReservaAsiento;
    private Reserva reserva;
    private Asiento asiento;

    public ReservaAsiento() {
    }
    public ReservaAsiento(int idReservaAsiento, Reserva reserva, Asiento asiento) {
        this.idReservaAsiento = idReservaAsiento;
        this.reserva = reserva;
        this.asiento = asiento;
    }
    public int getIdReservaAsiento() {
        return idReservaAsiento;
    }
    public void setIdReservaAsiento(int idReservaAsiento) {
        this.idReservaAsiento = idReservaAsiento;
    }
    public Reserva getReserva() {
        return reserva;
    }
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    public Asiento getAsiento() {
        return asiento;
    }
    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }
    @Override
    public String toString() {
        return "ReservaAsiento{" + "idReservaAsiento=" + idReservaAsiento + ", reserva=" + reserva + ", asiento=" + asiento + '}';
    }
}
