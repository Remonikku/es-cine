package model;

public class Sala{
    private int idSala;
    private int capacidad;
    private String nombreSala;
    
    public Sala(){
    }
    public Sala(int idSala, int capacidad, String nombreSala){
        this.idSala = idSala;
        this.capacidad = capacidad;
        this.nombreSala = nombreSala;
    }
    public int getIdSala() {
        return idSala;
    }
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }
    public int getCapacidad() {
        return capacidad;
    }    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    public String getNombreSala() {
        return nombreSala;
    }
    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }
    @Override
    public String toString() {
        return "Sala{" + "idSala=" + idSala + ", capacidad=" + capacidad + ", nombreSala=" + nombreSala + '}';
    }
 }

