package model;

import java.sql.Date;

public class ReservaGeneral extends Reserva {

    public ReservaGeneral() {
        super();
        setTipoEntrada("General");
    }

    public ReservaGeneral(int idReserva, int idCliente, int idFuncion, Date fechaReserva) {
        super(idReserva, idCliente, idFuncion, fechaReserva);
        setTipoEntrada("General");
    }

    @Override
    public void calcularPrecio(double precioBase) {
        // En Chile, el precio final se calcula con el 19% de IVA incluido.
        // Redondeamos al entero más cercano ya que el peso chileno (CLP) no tiene centavos.
        double precioNeto = precioBase;
        int precioConIva = (int) Math.round(precioNeto * 1.19);
        setPrecioFinal(precioConIva);
    }
}
