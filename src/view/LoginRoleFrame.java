package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class LoginRoleFrame extends JFrame {

    public LoginRoleFrame() {
        initComponents();
        customInit();
    }

    private void customInit() {
        // Estilo Oscuro Premium
        getContentPane().setBackground(new Color(20, 18, 30));
        btnCliente.setBackground(new Color(130, 50, 180));
        btnCliente.setForeground(Color.WHITE);
        btnCajero.setBackground(new Color(60, 50, 80));
        btnCajero.setForeground(Color.WHITE);
        btnAdmin.setBackground(new Color(180, 50, 80));
        btnAdmin.setForeground(Color.WHITE);

        // Listeners
        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteFrame().setVisible(true);
                dispose();
            }
        });

        btnCajero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CajeroFrame().setVisible(true);
                dispose();
            }
        });

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminFrame().setVisible(true);
                dispose();
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnCliente = new javax.swing.JButton();
        btnCajero = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        lblSpacer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CineVerse MultiPlex: Acceso al Sistema");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(110, 230, 240));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("SELECCIONE ROL DE ACCESO");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 480, 30));

        btnCliente.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnCliente.setText("Cliente (Comprar Entradas)");
        getContentPane().add(btnCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 320, 50));

        btnCajero.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnCajero.setText("Cajero (Venta en Caja)");
        getContentPane().add(btnCajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 320, 50));

        btnAdmin.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnAdmin.setText("Administrador (Gestión Total)");
        getContentPane().add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 320, 50));
        getContentPane().add(lblSpacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 480, 30));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginRoleFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnCajero;
    private javax.swing.JButton btnCliente;
    private javax.swing.JLabel lblSpacer;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
