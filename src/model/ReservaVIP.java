package model;

import java.sql.Date;

public class ReservaVIP extends Reserva {

    public ReservaVIP() {
        super();
        setTipoEntrada("VIP");
    }

    public ReservaVIP(int idReserva, int idCliente, int idFuncion, Date fechaReserva) {
        super(idReserva, idCliente, idFuncion, fechaReserva);
        setTipoEntrada("VIP");
    }

    @Override
    public void calcularPrecio(double precioBase) {
        // En Chile, el precio final se calcula con el 19% de IVA incluido.
        // Las entradas VIP tienen un recargo del 50% (1.5x) sobre el precio base neto.
        // Redondeamos al entero más cercano ya que el peso chileno (CLP) no tiene centavos.
        double precioNeto = precioBase * 1.5;
        int precioConIva = (int) Math.round(precioNeto * 1.19);
        setPrecioFinal(precioConIva);
    }
}
