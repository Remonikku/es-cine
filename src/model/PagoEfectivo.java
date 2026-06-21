package model;

import interfaces.MetodoPago;
import javax.swing.JOptionPane;

public class PagoEfectivo implements MetodoPago {

    @Override
    public boolean procesarPago(double monto) {
        // Simulamos un pago exitoso con efectivo.
        System.out.println("Procesando pago de $" + (int)monto + " en Efectivo...");
        JOptionPane.showMessageDialog(null, 
            "Pago de $" + (int)monto + " procesado en efectivo en caja.\nPor favor entregar comprobante al cliente.", 
            "Pago en Efectivo", 
            JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
