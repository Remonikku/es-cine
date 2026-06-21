package model;

import java.sql.Date;

public class ReservaFactory {

    /**
     * Crea una nueva reserva calculando el precio final con IVA según el tipo de entrada.
     */
    public static Reserva crearReserva(String tipo, int idCliente, int idFuncion, Date fechaReserva, double precioBase) {
        Reserva reserva;
        if ("VIP".equalsIgnoreCase(tipo)) {
            reserva = new ReservaVIP(0, idCliente, idFuncion, fechaReserva);
        } else {
            reserva = new ReservaGeneral(0, idCliente, idFuncion, fechaReserva);
        }
        reserva.calcularPrecio(precioBase);
        return reserva;
    }

    /**
     * Crea una instancia de reserva vacía correspondiente al tipo para lectura desde BD.
     */
    public static Reserva crearReservaDesdeDB(String tipo) {
        if ("VIP".equalsIgnoreCase(tipo)) {
            return new ReservaVIP();
        } else {
            return new ReservaGeneral();
        }
    }
}
