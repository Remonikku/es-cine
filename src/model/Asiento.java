package model;

public class Asiento {
    private int idAsiento;
    private Sala sala;
    private String fila;
    private int numero;

    public Asiento(){
    }
    public Asiento(int idAsiento, Sala sala, String fila, int numero){
        this.idAsiento = idAsiento;
        this.sala = sala;
        this.fila = fila;
        this.numero = numero;
    }
    public int getIdAsiento() {
        return idAsiento;
    }
    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }
    public Sala getSala() {
        return sala;
    }
    public void setSala(Sala sala) {
        this.sala = sala;
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
        return "Asiento{" + "idAsiento=" + idAsiento + ", sala=" + sala + ", fila=" + fila + ", numero=" + numero + '}';
    }
}
