package model;

public class Asiento {
    private int idAsiento;
    private int idSala;
    private String fila;
    private int numero;

    public Asiento() {
    }

    public Asiento(int idAsiento, int idSala, String fila, int numero) {
        this.idAsiento = idAsiento;
        this.idSala = idSala;
        this.fila = fila;
        this.numero = numero;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Asiento{" + "idAsiento=" + idAsiento + ", idSala=" + idSala + ", fila=" + fila + ", numero=" + numero + '}';
    }

}
