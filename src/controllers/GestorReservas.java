package controllers;

import interfaces.ReservaObserver;
import java.util.ArrayList;
import java.util.List;
import model.Reserva;

public class GestorReservas {
    private static GestorReservas instancia;
    private final List<ReservaObserver> observadores;

    private GestorReservas() {
        this.observadores = new ArrayList<>();
    }

    public static synchronized GestorReservas getInstancia() {
        if (instancia == null) {
            instancia = new GestorReservas();
        }
        return instancia;
    }

    public void registrarObservador(ReservaObserver obs) {
        if (obs != null && !observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public void removerObservador(ReservaObserver obs) {
        observadores.remove(obs);
    }

    public void notificarCompra(Reserva reserva, String mensaje) {
        for (ReservaObserver obs : new ArrayList<>(observadores)) {
            obs.onCompraRealizada(reserva, mensaje);
        }
    }

    public void notificarCambioEstado(Reserva reserva, String mensajeEstado) {
        for (ReservaObserver obs : new ArrayList<>(observadores)) {
            obs.onEstadoReservaActualizado(reserva, mensajeEstado);
        }
    }
}
