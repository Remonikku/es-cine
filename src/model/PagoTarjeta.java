package model;

import interfaces.MetodoPago;
import javax.swing.JOptionPane;

public class PagoTarjeta implements MetodoPago {
    
    private String numeroTarjeta;

    public PagoTarjeta() {
        this.numeroTarjeta = "XXXX-XXXX-XXXX-1234";
    }

    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean procesarPago(double monto) {
        // En un caso real, aquí iría la integración con Transbank/Webpay.
        // Simulamos un pago exitoso con tarjeta.
        System.out.println("Procesando pago de $" + (int)monto + " con Tarjeta de Crédito/Débito (" + numeroTarjeta + ")...");
        JOptionPane.showMessageDialog(null, 
            "Pago de $" + (int)monto + " Aprobado por Transbank.\nTarjeta: " + numeroTarjeta, 
            "Pago con Tarjeta", 
            JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}
