package model;

public class ReservaAsiento {

    private int idReservaAsiento;
    private int idReserva;
    private int idAsiento;

    public ReservaAsiento() {
    }

    public ReservaAsiento(int idReservaAsiento, int idReserva, int idAsiento) {
        this.idReservaAsiento = idReservaAsiento;
        this.idReserva = idReserva;
        this.idAsiento = idAsiento;
    }

    public int getIdReservaAsiento() {
        return idReservaAsiento;
    }

    public void setIdReservaAsiento(int idReservaAsiento) {
        this.idReservaAsiento = idReservaAsiento;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    @Override
    public String toString() {
        return "ReservaAsiento{" + "idReservaAsiento=" + idReservaAsiento + ", idReserva=" + idReserva + ", idAsiento=" + idAsiento + '}';
    }

}
