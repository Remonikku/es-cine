package model;

import interfaces.MetodoPago;
import javax.swing.JOptionPane;

public class PagoTransferencia implements MetodoPago {

    @Override
    public boolean procesarPago(double monto) {
        // Simulamos un pago exitoso por transferencia bancaria.
        System.out.println("Procesando pago de $" + (int)monto + " por Transferencia Bancaria...");
        JOptionPane.showMessageDialog(null, 
            "Pago de $" + (int)monto + " procesado por Transferencia Bancaria.\nEsperando validación de transferencia...", 
            "Pago por Transferencia", 
            JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
