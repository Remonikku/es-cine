package view;

import controllers.GestorReservas;
import db.DAOAsiento;
import db.DAOCliente;
import db.DAOFuncion;
import db.DAOPelicula;
import db.DAOReserva;
import interfaces.MetodoPago;
import interfaces.ReservaObserver;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Asiento;
import model.Cliente;
import model.Funcion;
import model.PagoEfectivo;
import model.PagoTarjeta;
import model.PagoTransferencia;
import model.Pelicula;
import model.Reserva;
import model.ReservaFactory;

public class ClienteFrame extends JFrame implements ReservaObserver {

    private DAOPelicula daoPelicula;
    private DAOFuncion daoFuncion;
    private DAOCliente daoCliente;
    private DAOAsiento daoAsiento;
    private DAOReserva daoReserva;

    public ClienteFrame() {
        initComponents();
        customInit();
        inicializarDAOs();
        cargarDatosCombo();
        
        GestorReservas.getInstancia().registrarObservador(this);
    }

    private void inicializarDAOs() {
        try {
            daoPelicula = new DAOPelicula();
            daoFuncion = new DAOFuncion();
            daoCliente = new DAOCliente();
            daoAsiento = new DAOAsiento();
            daoReserva = new DAOReserva();
        } catch (SQLException e) {
            System.err.println("Error al inicializar DAOs en ClienteFrame: " + e.getMessage());
        }
    }

    private void customInit() {
        getContentPane().setBackground(new Color(20, 18, 30));
        pnlMain.setBackground(new Color(30, 28, 45));
        pnlLog.setBackground(new Color(30, 28, 45));
        btnComprar.setBackground(new Color(130, 50, 180));
        btnComprar.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(50, 45, 65));
        btnVolver.setForeground(Color.WHITE);

        // Grouping
        buttonGroup1.add(rdbGeneral);
        buttonGroup1.add(rdbVIP);
        buttonGroup2.add(rdbTarjeta);
        buttonGroup2.add(rdbEfectivo);
        buttonGroup2.add(rdbTransferencia);

        // Listeners
        cmbPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarFuncionesDePeliculaSeleccionada();
            }
        });

        cmbFuncion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPreciosDesglose();
            }
        });

        rdbGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPreciosDesglose();
            }
        });

        rdbVIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPreciosDesglose();
            }
        });

        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarCompraCliente();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestorReservas.getInstancia().removerObservador(ClienteFrame.this);
                new LoginRoleFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void cargarDatosCombo() {
        try {
            cmbPelicula.removeAllItems();
            List<Pelicula> peliculas = daoPelicula.getPelicula();
            for (Pelicula p : peliculas) {
                cmbPelicula.addItem(new PeliculaComboItem(p));
            }

            cmbAsiento.removeAllItems();
            List<Asiento> asientos = daoAsiento.getAsiento();
            if (asientos.isEmpty()) {
                cmbAsiento.addItem("Asiento A-1 (Sala 1)");
                cmbAsiento.addItem("Asiento A-2 (Sala 1)");
                cmbAsiento.addItem("Asiento B-1 (Sala 2)");
            } else {
                for (Asiento a : asientos) {
                    cmbAsiento.addItem("Fila " + a.getFila() + " - N° " + a.getNumero() + " (Sala " + a.getIdSala() + ")");
                }
            }

            cargarFuncionesDePeliculaSeleccionada();
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarFuncionesDePeliculaSeleccionada() {
        PeliculaComboItem selectedPeli = (PeliculaComboItem) cmbPelicula.getSelectedItem();
        if (selectedPeli == null) return;

        cmbFuncion.removeAllItems();
        List<Funcion> funciones = daoFuncion.getFuncion();
        boolean funcionesEncontradas = false;
        
        for (Funcion f : funciones) {
            if (f.getIdPelicula() == selectedPeli.getPelicula().getIdPelicula()) {
                cmbFuncion.addItem(new FuncionComboItem(f));
                funcionesEncontradas = true;
            }
        }
        
        if (!funcionesEncontradas) {
            Funcion dummyFun = new Funcion(0, selectedPeli.getPelicula().getIdPelicula(), 1, LocalTime.of(18, 30), 4500.0);
            cmbFuncion.addItem(new FuncionComboItem(dummyFun));
        }

        actualizarPreciosDesglose();
    }

    private void actualizarPreciosDesglose() {
        FuncionComboItem selectedFun = (FuncionComboItem) cmbFuncion.getSelectedItem();
        if (selectedFun == null) {
            lblPrecioBase.setText("Precio Base Neto: $0 CLP");
            lblSurchargeVIP.setText("Recargo VIP (50%): $0 CLP");
            lblIva.setText("IVA Chileno (19%): $0 CLP");
            lblPrecioTotal.setText("Precio Total: $0 CLP");
            return;
        }

        double precioBase = selectedFun.getFuncion().getPrecio();
        boolean isVip = rdbVIP.isSelected();

        double baseNeto = precioBase;
        double recargoVip = isVip ? (precioBase * 0.5) : 0.0;
        double netoTotal = baseNeto + recargoVip;
        double iva = netoTotal * 0.19;
        double total = netoTotal * 1.19;
        
        lblPrecioBase.setText("Precio Base Neto: $" + String.format("%,d", (int)Math.round(baseNeto)) + " CLP");
        lblSurchargeVIP.setText("Recargo VIP (50%): $" + String.format("%,d", (int)Math.round(recargoVip)) + " CLP");
        lblIva.setText("IVA Chileno (19%): $" + String.format("%,d", (int)Math.round(iva)) + " CLP");
        lblPrecioTotal.setText("Precio Total (Redondeado): $" + String.format("%,d", (int)Math.round(total)) + " CLP");
    }

    private void procesarCompraCliente() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String rut = txtRut.getText().trim();

        if (nombre.isEmpty() || rut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor introduce tu Nombre y RUT para realizar la compra.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PeliculaComboItem peli = (PeliculaComboItem) cmbPelicula.getSelectedItem();
        FuncionComboItem fun = (FuncionComboItem) cmbFuncion.getSelectedItem();

        if (peli == null || fun == null) {
            JOptionPane.showMessageDialog(this, "No hay una película o función seleccionada.", "Error Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Obtener o Registrar Cliente
        int idCliente = 0;
        try {
            List<Cliente> clientes = daoCliente.getCliente();
            for (Cliente c : clientes) {
                if (c.getRut().equalsIgnoreCase(rut)) {
                    idCliente = c.getIdCliente();
                    break;
                }
            }

            if (idCliente == 0) {
                // Registrar cliente nuevo
                Cliente nuevoCli = new Cliente(0, nombre, correo.isEmpty() ? "Sin correo" : correo, rut);
                daoCliente.crearCliente(nuevoCli);
                // Recuperar ID
                List<Cliente> listaActualizada = daoCliente.getCliente();
                idCliente = listaActualizada.get(listaActualizada.size() - 1).getIdCliente();
            }
        } catch (SQLException ex) {
            System.err.println("Error al gestionar cliente en BD: " + ex.getMessage());
        }

        // 2. Instanciar Estrategia de Pago (Strategy)
        MetodoPago estrategiaPago;
        String metDesc;
        if (rdbTarjeta.isSelected()) {
            estrategiaPago = new PagoTarjeta();
            metDesc = "Tarjeta";
        } else if (rdbEfectivo.isSelected()) {
            estrategiaPago = new PagoEfectivo();
            metDesc = "Efectivo";
        } else {
            estrategiaPago = new PagoTransferencia();
            metDesc = "Transferencia";
        }

        // 3. Crear Reserva usando Factory
        String tipoEntrada = rdbGeneral.isSelected() ? "General" : "VIP";
        Date fechaActual = new Date(System.currentTimeMillis());
        double precioBase = fun.getFuncion().getPrecio();

        Reserva reserva = ReservaFactory.crearReserva(
            tipoEntrada, 
            idCliente, 
            fun.getFuncion().getIdFuncion(), 
            fechaActual, 
            precioBase
        );

        // 4. Procesar el pago
        boolean pagoExitoso = estrategiaPago.procesarPago(reserva.getPrecioFinal());

        if (pagoExitoso) {
            daoReserva.crearReserva(reserva);
            
            try {
                List<Reserva> lista = daoReserva.getReserva();
                if (!lista.isEmpty()) {
                    reserva.setIdReserva(lista.get(lista.size() - 1).getIdReserva());
                }
            } catch (SQLException ex) {
                System.err.println("" + ex);
            }

            // 5. Notificar a observadores
            String msj = "Reserva cliente: " + nombre + " (ID: " + idCliente + ") creada con éxito. Pago procesado vía: " + metDesc;
            GestorReservas.getInstancia().notificarCompra(reserva, msj);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblHeader = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblRut = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        lblPeli = new javax.swing.JLabel();
        cmbPelicula = new javax.swing.JComboBox<>();
        lblFun = new javax.swing.JLabel();
        cmbFuncion = new javax.swing.JComboBox<>();
        lblAsi = new javax.swing.JLabel();
        cmbAsiento = new javax.swing.JComboBox<>();
        lblTipo = new javax.swing.JLabel();
        rdbGeneral = new javax.swing.JRadioButton();
        rdbVIP = new javax.swing.JRadioButton();
        lblPago = new javax.swing.JLabel();
        rdbTarjeta = new javax.swing.JRadioButton();
        rdbEfectivo = new javax.swing.JRadioButton();
        rdbTransferencia = new javax.swing.JRadioButton();
        sepDesglose = new javax.swing.JSeparator();
        lblPrecioBase = new javax.swing.JLabel();
        lblSurchargeVIP = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblPrecioTotal = new javax.swing.JLabel();
        btnComprar = new javax.swing.JButton();
        pnlLog = new javax.swing.JPanel();
        scrollLog = new javax.swing.JScrollPane();
        txtNotificaciones = new javax.swing.JTextArea();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ES-CINE: Portal de Reservas - Cliente");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHeader.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(110, 230, 240));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("COMPRA Y RESERVA DE ENTRADAS - CLIENTE");
        getContentPane().add(lblHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 600, 30));

        pnlMain.setBackground(new java.awt.Color(30, 28, 45));
        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)), "FORMULARIO DE ADQUISICIÓN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(185, 110, 240))); // NOI18N
        pnlMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombre.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(245, 240, 240));
        lblNombre.setText("Tu Nombre:");
        pnlMain.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, 130, 20));
        pnlMain.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 25, 360, 22));

        lblCorreo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(245, 240, 240));
        lblCorreo.setText("Tu Correo:");
        pnlMain.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 130, 20));
        pnlMain.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 360, 22));

        lblRut.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRut.setForeground(new java.awt.Color(245, 240, 240));
        lblRut.setText("Tu RUT:");
        pnlMain.add(lblRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 85, 130, 20));
        pnlMain.add(txtRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 85, 360, 22));

        lblPeli.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPeli.setForeground(new java.awt.Color(245, 240, 240));
        lblPeli.setText("Película:");
        pnlMain.add(lblPeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 115, 130, 20));

        pnlMain.add(cmbPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 115, 360, 22));

        lblFun.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFun.setForeground(new java.awt.Color(245, 240, 240));
        lblFun.setText("Función y Horario:");
        pnlMain.add(lblFun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 145, 130, 20));

        pnlMain.add(cmbFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 145, 360, 22));

        lblAsi.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAsi.setForeground(new java.awt.Color(245, 240, 240));
        lblAsi.setText("Asiento:");
        pnlMain.add(lblAsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 175, 130, 20));

        pnlMain.add(cmbAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 175, 360, 22));

        lblTipo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(245, 240, 240));
        lblTipo.setText("Tipo Entrada:");
        pnlMain.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 205, 130, 20));

        rdbGeneral.setBackground(new java.awt.Color(30, 28, 45));
        rdbGeneral.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rdbGeneral.setForeground(new java.awt.Color(245, 240, 240));
        rdbGeneral.setSelected(true);
        rdbGeneral.setText("General");
        pnlMain.add(rdbGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 205, 100, 20));

        rdbVIP.setBackground(new java.awt.Color(30, 28, 45));
        rdbVIP.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rdbVIP.setForeground(new java.awt.Color(245, 240, 240));
        rdbVIP.setText("VIP (+50%)");
        pnlMain.add(rdbVIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 205, 100, 20));

        lblPago.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPago.setForeground(new java.awt.Color(245, 240, 240));
        lblPago.setText("Pago:");
        pnlMain.add(lblPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 235, 130, 20));

        rdbTarjeta.setBackground(new java.awt.Color(30, 28, 45));
        rdbTarjeta.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rdbTarjeta.setForeground(new java.awt.Color(245, 240, 240));
        rdbTarjeta.setSelected(true);
        rdbTarjeta.setText("Tarjeta");
        pnlMain.add(rdbTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 235, 90, 20));

        rdbEfectivo.setBackground(new java.awt.Color(30, 28, 45));
        rdbEfectivo.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rdbEfectivo.setForeground(new java.awt.Color(245, 240, 240));
        rdbEfectivo.setText("Efectivo");
        pnlMain.add(rdbEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 235, 90, 20));

        rdbTransferencia.setBackground(new java.awt.Color(30, 28, 45));
        rdbTransferencia.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        rdbTransferencia.setForeground(new java.awt.Color(245, 240, 240));
        rdbTransferencia.setText("Transferencia");
        pnlMain.add(rdbTransferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 235, 130, 20));

        sepDesglose.setForeground(new java.awt.Color(130, 50, 180));
        pnlMain.add(sepDesglose, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 500, 5));

        lblPrecioBase.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPrecioBase.setForeground(new java.awt.Color(245, 240, 240));
        lblPrecioBase.setText("Precio Base Neto: $0 CLP");
        pnlMain.add(lblPrecioBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 220, 20));

        lblSurchargeVIP.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSurchargeVIP.setForeground(new java.awt.Color(245, 240, 240));
        lblSurchargeVIP.setText("Recargo VIP (50%): $0 CLP");
        pnlMain.add(lblSurchargeVIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 305, 220, 20));

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblIva.setForeground(new java.awt.Color(245, 240, 240));
        lblIva.setText("IVA Chileno (19%): $0 CLP");
        pnlMain.add(lblIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 220, 20));

        lblPrecioTotal.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        lblPrecioTotal.setForeground(new java.awt.Color(185, 110, 240));
        lblPrecioTotal.setText("Precio Total: $0 CLP");
        pnlMain.add(lblPrecioTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 355, 250, 25));

        btnComprar.setBackground(new java.awt.Color(130, 50, 180));
        btnComprar.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnComprar.setText("CONFIRMAR RESERVA Y PAGAR");
        pnlMain.add(btnComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 250, 90));

        getContentPane().add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 60, 550, 420));

        pnlLog.setBackground(new java.awt.Color(30, 28, 45));
        pnlLog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)), "BUZÓN DE NOTIFICACIONES RECIBIDAS (OBSERVER)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11), new java.awt.Color(185, 110, 240))); // NOI18N
        pnlLog.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNotificaciones.setBackground(new java.awt.Color(12, 10, 20));
        txtNotificaciones.setColumns(20);
        txtNotificaciones.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        txtNotificaciones.setForeground(new java.awt.Color(140, 255, 170));
        txtNotificaciones.setRows(5);
        txtNotificaciones.setEditable(false);
        txtNotificaciones.setLineWrap(true);
        txtNotificaciones.setWrapStyleWord(true);
        scrollLog.setViewportView(txtNotificaciones);

        pnlLog.add(scrollLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 520, 115));

        getContentPane().add(pnlLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 490, 550, 150));

        btnVolver.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnVolver.setText("Volver");
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 650, 550, 30));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registrarMensajeEnTerminal(String log) {
        txtNotificaciones.append(log + "\n");
        txtNotificaciones.setCaretPosition(txtNotificaciones.getDocument().getLength());
    }

    // ================= CALLBACKS OBSERVER =================
    @Override
    public void onCompraRealizada(Reserva reserva, String mensaje) {
        String log = String.format("🎟️ [OBSERVER] ¡Compra Exitosa! ID Reserva: %d | Total con IVA: $%d | Detalles: %s", 
                reserva.getIdReserva(), reserva.getPrecioFinal(), mensaje);
        registrarMensajeEnTerminal(log);
    }

    @Override
    public void onEstadoReservaActualizado(Reserva reserva, String mensajeEstado) {
        String log = String.format("⚠️ [OBSERVER] Alerta en Reserva #%d: %s", reserva.getIdReserva(), mensajeEstado);
        registrarMensajeEnTerminal(log);
        
        JOptionPane.showMessageDialog(this, 
                "🔔 [Notificación para ti]\nReserva: #" + reserva.getIdReserva() + "\nDetalles: " + mensajeEstado, 
                "Aviso del Cine", 
                JOptionPane.WARNING_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComprar;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<ClienteFrame.FuncionComboItem> cmbFuncion;
    private javax.swing.JComboBox<ClienteFrame.PeliculaComboItem> cmbPelicula;
    private javax.swing.JComboBox<String> cmbAsiento;
    private javax.swing.JLabel lblAsi;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblFun;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblPeli;
    private javax.swing.JLabel lblPrecioBase;
    private javax.swing.JLabel lblPrecioTotal;
    private javax.swing.JLabel lblRut;
    private javax.swing.JLabel lblSurchargeVIP;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlLog;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JSeparator sepDesglose;
    private javax.swing.JRadioButton rdbEfectivo;
    private javax.swing.JRadioButton rdbGeneral;
    private javax.swing.JRadioButton rdbTarjeta;
    private javax.swing.JRadioButton rdbTransferencia;
    private javax.swing.JRadioButton rdbVIP;
    private javax.swing.JScrollPane scrollLog;
    private javax.swing.JTextArea txtNotificaciones;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables

    // Combo Items
    private static class PeliculaComboItem {
        private final Pelicula pelicula;
        public PeliculaComboItem(Pelicula p) { this.pelicula = p; }
        public Pelicula getPelicula() { return pelicula; }
        @Override public String toString() { return pelicula.getTitulo() + " (" + pelicula.getClasificacion() + ")"; }
    }

    private static class FuncionComboItem {
        private final Funcion funcion;
        public FuncionComboItem(Funcion f) { this.funcion = f; }
        public Funcion getFuncion() { return funcion; }
        @Override public String toString() { return "Hora: " + funcion.getHorario() + " | Sala " + funcion.getIdSala() + " | Base: $" + String.format("%,d", (int)funcion.getPrecio().doubleValue()); }
    }
}
