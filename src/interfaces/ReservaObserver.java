package interfaces;

import model.Reserva;

public interface ReservaObserver {
    void onCompraRealizada(Reserva reserva, String mensaje);
    void onEstadoReservaActualizado(Reserva reserva, String mensajeEstado);
}
