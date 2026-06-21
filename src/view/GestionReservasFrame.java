package view;

import controllers.GestorReservas;
import db.Conexion;
import db.DAOAsiento;
import db.DAOCliente;
import db.DAOFuncion;
import db.DAOPelicula;
import db.DAOReserva;
import db.DAOSala;
import interfaces.MetodoPago;
import interfaces.ReservaObserver;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.Asiento;
import model.Cliente;
import model.Funcion;
import model.PagoEfectivo;
import model.PagoTarjeta;
import model.Pelicula;
import model.Reserva;
import model.ReservaFactory;
import model.Sala;

public class GestionReservasFrame extends JFrame implements ReservaObserver {

    private DAOPelicula daoPelicula;
    private DAOFuncion daoFuncion;
    private DAOCliente daoCliente;
    private DAOAsiento daoAsiento;
    private DAOReserva daoReserva;
    private DAOSala daoSala;
    
    private DefaultTableModel tableModel;

    public GestionReservasFrame() {
        initComponents();
        inicializarDAOs();
        validarYPopularDatosPrueba();
        cargarDatosCombo();
        cargarReservasTabla();
        
        GestorReservas.getInstancia().registrarObservador(this);
        
        registrarMensajeEnTerminal("[SISTEMA] Conectado exitosamente. Listo para gestionar reservas.");
    }

    private void inicializarDAOs() {
        try {
            daoPelicula = new DAOPelicula();
            daoFuncion = new DAOFuncion();
            daoCliente = new DAOCliente();
            daoAsiento = new DAOAsiento();
            daoReserva = new DAOReserva();
            daoSala = new DAOSala();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblHeader = new javax.swing.JLabel();
        sepHeader = new javax.swing.JSeparator();
        pnlForm = new javax.swing.JPanel();
        lblPeli = new javax.swing.JLabel();
        cmbPelicula = new javax.swing.JComboBox<>();
        lblFun = new javax.swing.JLabel();
        cmbFuncion = new javax.swing.JComboBox<>();
        lblCli = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        btnNuevoCliente = new javax.swing.JButton();
        lblAsi = new javax.swing.JLabel();
        cmbAsiento = new javax.swing.JComboBox<>();
        lblTipo = new javax.swing.JLabel();
        rdbGeneral = new javax.swing.JRadioButton();
        rdbVIP = new javax.swing.JRadioButton();
        lblPago = new javax.swing.JLabel();
        rdbTarjeta = new javax.swing.JRadioButton();
        rdbEfectivo = new javax.swing.JRadioButton();
        sepForm = new javax.swing.JSeparator();
        lblDetallePrecios = new javax.swing.JLabel();
        lblPrecioBase = new javax.swing.JLabel();
        lblSurchargeVIP = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblPrecioTotal = new javax.swing.JLabel();
        btnComprar = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        scrollTable = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        lblSim = new javax.swing.JLabel();
        txtIncidente = new javax.swing.JTextField();
        btnSimularIncidente = new javax.swing.JButton();
        pnlLog = new javax.swing.JPanel();
        scrollLog = new javax.swing.JScrollPane();
        txtNotificaciones = new javax.swing.JTextArea();
        btnCerrar = new javax.swing.JButton();
        lblObserverExplan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ES-CINE: Panel Premium de Gestión de Reservas y Notificaciones");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().setBackground(new java.awt.Color(20, 18, 30));

        lblHeader.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(110, 230, 240));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("ES-CINE - PANEL DE RESERVAS & NOTIFICACIONES (OBSERVER)");
        getContentPane().add(lblHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 1150, 35));

        sepHeader.setForeground(new java.awt.Color(130, 50, 180));
        getContentPane().add(sepHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 55, 1090, 10));

        pnlForm.setBackground(new java.awt.Color(30, 28, 45));
        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)), "NUEVA RESERVA (FACTORY & STRATEGY)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), new java.awt.Color(185, 110, 240))); // NOI18N
        pnlForm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPeli.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPeli.setForeground(new java.awt.Color(240, 240, 245));
        lblPeli.setText("Seleccionar Película:");
        pnlForm.add(lblPeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, 25));

        cmbPelicula.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarFuncionesDePeliculaSeleccionada();
            }
        });
        pnlForm.add(cmbPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 300, 25));

        lblFun.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblFun.setForeground(new java.awt.Color(240, 240, 245));
        lblFun.setText("Horario y Sala:");
        pnlForm.add(lblFun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 150, 25));

        cmbFuncion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarPreciosDesglose();
            }
        });
        pnlForm.add(cmbFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 300, 25));

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblCli.setForeground(new java.awt.Color(240, 240, 245));
        lblCli.setText("Seleccionar Cliente:");
        pnlForm.add(lblCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 25));

        cmbCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        pnlForm.add(cmbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 180, 25));

        btnNuevoCliente.setBackground(new java.awt.Color(60, 50, 80));
        btnNuevoCliente.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnNuevoCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCliente.setText("Rápido +");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirDialogoNuevoCliente();
            }
        });
        pnlForm.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 110, 25));

        lblAsi.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblAsi.setForeground(new java.awt.Color(240, 240, 245));
        lblAsi.setText("Seleccionar Asiento:");
        pnlForm.add(lblAsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 150, 25));

        cmbAsiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        pnlForm.add(cmbAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 300, 25));

        lblTipo.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(240, 240, 245));
        lblTipo.setText("Tipo de Entrada (Factory):");
        pnlForm.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 160, 25));

        rdbGeneral.setBackground(new java.awt.Color(30, 28, 45));
        buttonGroup1.add(rdbGeneral);
        rdbGeneral.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rdbGeneral.setForeground(new java.awt.Color(240, 240, 245));
        rdbGeneral.setSelected(true);
        rdbGeneral.setText("General");
        rdbGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarPreciosDesglose();
            }
        });
        pnlForm.add(rdbGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 100, 25));

        rdbVIP.setBackground(new java.awt.Color(30, 28, 45));
        buttonGroup1.add(rdbVIP);
        rdbVIP.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rdbVIP.setForeground(new java.awt.Color(240, 240, 245));
        rdbVIP.setText("VIP (+50%)");
        rdbVIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarPreciosDesglose();
            }
        });
        pnlForm.add(rdbVIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 120, 25));

        lblPago.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPago.setForeground(new java.awt.Color(240, 240, 245));
        lblPago.setText("Método Pago (Strategy):");
        pnlForm.add(lblPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 160, 25));

        rdbTarjeta.setBackground(new java.awt.Color(30, 28, 45));
        buttonGroup2.add(rdbTarjeta);
        rdbTarjeta.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rdbTarjeta.setForeground(new java.awt.Color(240, 240, 245));
        rdbTarjeta.setSelected(true);
        rdbTarjeta.setText("Tarjeta Cred/Deb");
        pnlForm.add(rdbTarjeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 150, 25));

        rdbEfectivo.setBackground(new java.awt.Color(30, 28, 45));
        buttonGroup2.add(rdbEfectivo);
        rdbEfectivo.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        rdbEfectivo.setForeground(new java.awt.Color(240, 240, 245));
        rdbEfectivo.setText("Efectivo");
        pnlForm.add(rdbEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, 100, 25));

        sepForm.setForeground(new java.awt.Color(130, 50, 180));
        pnlForm.add(sepForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 275, 460, 5));

        lblDetallePrecios.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblDetallePrecios.setForeground(new java.awt.Color(110, 230, 240));
        lblDetallePrecios.setText("Desglose de Compra (IVA incluido):");
        pnlForm.add(lblDetallePrecios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 300, 20));

        lblPrecioBase.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPrecioBase.setForeground(new java.awt.Color(240, 240, 245));
        lblPrecioBase.setText("Precio Base Neto: $0 CLP");
        pnlForm.add(lblPrecioBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 250, 20));

        lblSurchargeVIP.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSurchargeVIP.setForeground(new java.awt.Color(240, 240, 245));
        lblSurchargeVIP.setText("Recargo VIP (50%): $0 CLP");
        pnlForm.add(lblSurchargeVIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 345, 250, 20));

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblIva.setForeground(new java.awt.Color(240, 240, 245));
        lblIva.setText("IVA Chileno (19%): $0 CLP");
        pnlForm.add(lblIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 250, 20));

        lblPrecioTotal.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        lblPrecioTotal.setForeground(new java.awt.Color(185, 110, 240));
        lblPrecioTotal.setText("Precio Total: $0 CLP");
        pnlForm.add(lblPrecioTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 300, 25));

        btnComprar.setBackground(new java.awt.Color(130, 50, 180));
        btnComprar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnComprar.setForeground(new java.awt.Color(255, 255, 255));
        btnComprar.setText("COMPRAR ENTRADAS & RESERVAR");
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procesarFlujoCompraYReserva();
            }
        });
        pnlForm.add(btnComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 460, 45));

        getContentPane().add(pnlForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 500, 505));

        pnlList.setBackground(new java.awt.Color(30, 28, 45));
        pnlList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)), "RESERVAS REALIZADAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), new java.awt.Color(185, 110, 240))); // NOI18N
        pnlList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID Reserva", "Cliente", "Película", "Fecha", "Tipo Entrada", "Total CLP"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblReservas.setBackground(new java.awt.Color(25, 23, 35));
        tblReservas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tblReservas.setForeground(new java.awt.Color(240, 240, 245));
        tblReservas.setGridColor(new java.awt.Color(130, 50, 180));
        tblReservas.setModel(tableModel);
        scrollTable.setViewportView(tblReservas);

        pnlList.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 25, 540, 200));

        lblSim.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblSim.setForeground(new java.awt.Color(110, 230, 240));
        lblSim.setText("Notificar Incidente / Retraso (Observer):");
        pnlList.add(lblSim, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 235, 300, 20));

        txtIncidente.setBackground(new java.awt.Color(20, 18, 30));
        txtIncidente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtIncidente.setForeground(new java.awt.Color(240, 240, 245));
        txtIncidente.setText("La función tiene un retraso de 15 minutos debido a mantenimiento de proyector.");
        txtIncidente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)));
        pnlList.add(txtIncidente, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 260, 390, 25));

        btnSimularIncidente.setBackground(new java.awt.Color(180, 50, 80));
        btnSimularIncidente.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnSimularIncidente.setForeground(new java.awt.Color(255, 255, 255));
        btnSimularIncidente.setText("Enviar Aviso 🔔");
        btnSimularIncidente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarAvisoIncidenteObserver();
            }
        });
        pnlList.add(btnSimularIncidente, new org.netbeans.lib.awtextra.AbsoluteConstraints(415, 260, 140, 25));

        getContentPane().add(pnlList, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 570, 300));

        pnlLog.setBackground(new java.awt.Color(30, 28, 45));
        pnlLog.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(130, 50, 180)), "TERMINAL DE NOTIFICACIONES EN VIVO (OBSERVER)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), new java.awt.Color(185, 110, 240))); // NOI18N
        pnlLog.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNotificaciones.setBackground(new java.awt.Color(12, 10, 20));
        txtNotificaciones.setColumns(20);
        txtNotificaciones.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtNotificaciones.setForeground(new java.awt.Color(140, 255, 170));
        txtNotificaciones.setRows(5);
        txtNotificaciones.setEditable(false);
        txtNotificaciones.setLineWrap(true);
        txtNotificaciones.setWrapStyleWord(true);
        scrollLog.setViewportView(txtNotificaciones);

        pnlLog.add(scrollLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 25, 540, 140));

        getContentPane().add(pnlLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, 570, 185));

        btnCerrar.setBackground(new java.awt.Color(50, 45, 65));
        btnCerrar.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("VOLVER A CARTELERA");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestorReservas.getInstancia().removerObservador(GestionReservasFrame.this);
                dispose();
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, 1090, 40));

        lblObserverExplan.setFont(new java.awt.Font("SansSerif", 2, 11)); // NOI18N
        lblObserverExplan.setForeground(new java.awt.Color(160, 160, 180));
        lblObserverExplan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblObserverExplan.setText("<html>Tip: Registre compras (Strategy) y cree entradas (Factory). El buzón verde demuestra el patrón Observer en tiempo real notificando transacciones e incidencias.</html>");
        getContentPane().add(lblObserverExplan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 660, 1090, 25));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void cargarDatosCombo() {
        try {
            cmbPelicula.removeAllItems();
            List<Pelicula> peliculas = daoPelicula.getPelicula();
            for (Pelicula p : peliculas) {
                cmbPelicula.addItem(new PeliculaComboItem(p));
            }

            cmbCliente.removeAllItems();
            List<Cliente> clientes = daoCliente.getCliente();
            for (Cliente c : clientes) {
                cmbCliente.addItem(new ClienteComboItem(c));
            }

            cmbAsiento.removeAllItems();
            List<Asiento> asientos = daoAsiento.getAsiento();
            if (asientos.isEmpty()) {
                cmbAsiento.addItem("Asiento A-1 (Sala 1)");
                cmbAsiento.addItem("Asiento A-2 (Sala 1)");
                cmbAsiento.addItem("Asiento B-1 (Sala 2)");
                cmbAsiento.addItem("Asiento B-2 (Sala 2)");
            } else {
                for (Asiento a : asientos) {
                    cmbAsiento.addItem("Fila " + a.getFila() + " - N° " + a.getNumero() + " (Sala " + a.getIdSala() + ")");
                }
            }

            cargarFuncionesDePeliculaSeleccionada();
        } catch (SQLException e) {
            System.err.println("Error al cargar datos en comboboxes: " + e.getMessage());
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

    private void cargarReservasTabla() {
        try {
            tableModel.setRowCount(0);
            List<Reserva> reservas = daoReserva.getReserva();
            List<Cliente> clientes = daoCliente.getCliente();
            List<Funcion> funciones = daoFuncion.getFuncion();
            List<Pelicula> peliculas = daoPelicula.getPelicula();

            for (Reserva r : reservas) {
                String nombreCliente = "Desconocido (ID " + r.getIdCliente() + ")";
                for (Cliente c : clientes) {
                    if (c.getIdCliente() == r.getIdCliente()) {
                        nombreCliente = c.getNombre();
                        break;
                    }
                }

                String tituloPeli = "Función ID " + r.getIdFuncion();
                for (Funcion f : funciones) {
                    if (f.getIdFuncion() == r.getIdFuncion()) {
                        for (Pelicula p : peliculas) {
                            if (p.getIdPelicula() == f.getIdPelicula()) {
                                tituloPeli = p.getTitulo();
                                break;
                            }
                        }
                        break;
                    }
                }

                tableModel.addRow(new Object[]{
                    r.getIdReserva(),
                    nombreCliente,
                    tituloPeli,
                    r.getFechaReserva().toString(),
                    r.getTipoEntrada(),
                    "$" + String.format("%,d", r.getPrecioFinal())
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar tabla de reservas: " + e.getMessage());
        }
    }

    private void abrirDialogoNuevoCliente() {
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtRut = new JTextField();

        Object[] message = {
            "Nombre Completo:", txtNombre,
            "Correo Electrónico:", txtCorreo,
            "RUT (e.g. 12.345.678-9):", txtRut
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String rut = txtRut.getText().trim();

            if (nombre.isEmpty() || rut.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y RUT son campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente c = new Cliente(0, nombre, correo, rut);
            daoCliente.crearCliente(c);
            
            cargarDatosCombo();
            
            try {
                List<Cliente> clientes = daoCliente.getCliente();
                if (!clientes.isEmpty()) {
                    Cliente ultimo = clientes.get(clientes.size() - 1);
                    for (int i = 0; i < cmbCliente.getItemCount(); i++) {
                        if (cmbCliente.getItemAt(i).getCliente().getIdCliente() == ultimo.getIdCliente()) {
                            cmbCliente.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (SQLException ex) {
                System.err.println("" + ex);
            }
        }
    }

    private void procesarFlujoCompraYReserva() {
        PeliculaComboItem peli = (PeliculaComboItem) cmbPelicula.getSelectedItem();
        FuncionComboItem fun = (FuncionComboItem) cmbFuncion.getSelectedItem();
        ClienteComboItem cli = (ClienteComboItem) cmbCliente.getSelectedItem();
        
        if (peli == null || fun == null || cli == null) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los datos para comprar.", "Datos Faltantes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MetodoPago estrategiaPago;
        String metodoPagoDesc;
        if (rdbTarjeta.isSelected()) {
            estrategiaPago = new PagoTarjeta();
            metodoPagoDesc = "Tarjeta";
        } else {
            estrategiaPago = new PagoEfectivo();
            metodoPagoDesc = "Efectivo";
        }

        String tipoEntrada = rdbGeneral.isSelected() ? "General" : "VIP";
        Date fechaActual = new Date(System.currentTimeMillis());
        double precioBase = fun.getFuncion().getPrecio();
        
        Reserva reserva = ReservaFactory.crearReserva(
            tipoEntrada, 
            cli.getCliente().getIdCliente(), 
            fun.getFuncion().getIdFuncion(), 
            fechaActual, 
            precioBase
        );

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

            String mensajeCompra = "Reserva #" + reserva.getIdReserva() + " (" + reserva.getTipoEntrada() + ") creada exitosamente por cliente " + cli.getCliente().getNombre() + " utilizando pago con " + metodoPagoDesc + ".";
            GestorReservas.getInstancia().notificarCompra(reserva, mensajeCompra);

            cargarReservasTabla();
        }
    }

    private void enviarAvisoIncidenteObserver() {
        int selectedRow = tblReservas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una reserva de la tabla superior para notificar al cliente.", "Seleccione Reserva", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) tableModel.getValueAt(selectedRow, 0);
        String incidente = txtIncidente.getText().trim();

        if (incidente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor escribe un aviso de incidente válido.", "Texto vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Reserva> lista = daoReserva.getReserva();
            Reserva reservaSeleccionada = null;
            for (Reserva r : lista) {
                if (r.getIdReserva() == idReserva) {
                    reservaSeleccionada = r;
                    break;
                }
            }

            if (reservaSeleccionada != null) {
                GestorReservas.getInstancia().notificarCambioEstado(reservaSeleccionada, incidente);
            }
        } catch (SQLException ex) {
            System.err.println("Error al simular incidente: " + ex.getMessage());
        }
    }

    private void registrarMensajeEnTerminal(String log) {
        txtNotificaciones.append(log + "\n");
        txtNotificaciones.setCaretPosition(txtNotificaciones.getDocument().getLength());
    }

    @Override
    public void onCompraRealizada(Reserva reserva, String mensaje) {
        String log = String.format("[OBSERVER - COMPRA] Exito! ID: %d | Tipo: %s | Pago Total: $%d | Info: %s", 
                reserva.getIdReserva(), reserva.getTipoEntrada(), reserva.getPrecioFinal(), mensaje);
        registrarMensajeEnTerminal(log);
    }

    @Override
    public void onEstadoReservaActualizado(Reserva reserva, String mensajeEstado) {
        String log = String.format("[OBSERVER - ALERTA] Percance en Reserva #%d! Cliente ID %d. Notificación: %s", 
                reserva.getIdReserva(), reserva.getIdCliente(), mensajeEstado);
        registrarMensajeEnTerminal(log);
        
        JOptionPane.showMessageDialog(this, 
                "🔔 [Notificación Enviada al Cliente]\nReserva: #" + reserva.getIdReserva() + "\nMensaje: " + mensajeEstado, 
                "Alerta del Sistema de Reservas", 
                JOptionPane.WARNING_MESSAGE);
    }

    private void validarYPopularDatosPrueba() {
        try {
            List<Pelicula> peliculas = daoPelicula.getPelicula();
            if (peliculas.isEmpty()) {
                System.out.println("Base de datos vacía. Cargando datos de prueba...");
                
                Sala sala1 = new Sala(0, 50, "Sala 1");
                Sala sala2 = new Sala(0, 80, "Sala 2");
                daoSala.crearSala(sala1);
                daoSala.crearSala(sala2);
                
                List<Sala> salas = daoSala.getSala();
                int idSala1 = salas.get(0).getIdSala();
                int idSala2 = salas.get(1).getIdSala();

                Pelicula p1 = new Pelicula(0, "Phenomenon (Fenomeno)", LocalTime.of(1, 45), "R");
                Pelicula p2 = new Pelicula(0, "REC", LocalTime.of(1, 20), "R");
                Pelicula p3 = new Pelicula(0, "Hellraiser", LocalTime.of(1, 35), "18+");
                Pelicula p4 = new Pelicula(0, "Wall-E", LocalTime.of(1, 38), "TE");
                daoPelicula.crearPelicula(p1);
                daoPelicula.crearPelicula(p2);
                daoPelicula.crearPelicula(p3);
                daoPelicula.crearPelicula(p4);
                
                List<Pelicula> peliLista = daoPelicula.getPelicula();
                
                Funcion f1 = new Funcion(0, peliLista.get(0).getIdPelicula(), idSala1, LocalTime.of(15, 0), 4000.0);
                Funcion f2 = new Funcion(0, peliLista.get(0).getIdPelicula(), idSala1, LocalTime.of(18, 30), 4500.0);
                Funcion f3 = new Funcion(0, peliLista.get(1).getIdPelicula(), idSala2, LocalTime.of(20, 0), 3500.0);
                Funcion f4 = new Funcion(0, peliLista.get(2).getIdPelicula(), idSala2, LocalTime.of(22, 15), 3800.0);
                daoFuncion.crearFuncion(f1);
                daoFuncion.crearFuncion(f2);
                daoFuncion.crearFuncion(f3);
                daoFuncion.crearFuncion(f4);

                daoAsiento.crearAsiento(new Asiento(0, idSala1, "A", 1));
                daoAsiento.crearAsiento(new Asiento(0, idSala1, "A", 2));
                daoAsiento.crearAsiento(new Asiento(0, idSala1, "B", 1));
                daoAsiento.crearAsiento(new Asiento(0, idSala2, "C", 5));
                daoAsiento.crearAsiento(new Asiento(0, idSala2, "C", 6));

                daoCliente.crearCliente(new Cliente(0, "Nicolas Alpuche", "nicolas@correo.cl", "19.876.543-2"));
                daoCliente.crearCliente(new Cliente(0, "Josefa Donoso", "josefa@correo.cl", "20.123.456-7"));
                
                System.out.println("Población de datos de prueba exitosa!");
            }
        } catch (SQLException e) {
            System.err.println("Error al popular datos de prueba: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionReservasFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnComprar;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnSimularIncidente;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<ClienteComboItem> cmbCliente;
    private javax.swing.JComboBox<String> cmbAsiento;
    private javax.swing.JComboBox<FuncionComboItem> cmbFuncion;
    private javax.swing.JComboBox<PeliculaComboItem> cmbPelicula;
    private javax.swing.JLabel lblAsi;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDetallePrecios;
    private javax.swing.JLabel lblFun;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObserverExplan;
    private javax.swing.JLabel lblPago;
    private javax.swing.JLabel lblPeli;
    private javax.swing.JLabel lblPrecioBase;
    private javax.swing.JLabel lblPrecioTotal;
    private javax.swing.JLabel lblSim;
    private javax.swing.JLabel lblSurchargeVIP;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlLog;
    private javax.swing.JRadioButton rdbEfectivo;
    private javax.swing.JRadioButton rdbGeneral;
    private javax.swing.JRadioButton rdbTarjeta;
    private javax.swing.JRadioButton rdbVIP;
    private javax.swing.JScrollPane scrollLog;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JSeparator sepForm;
    private javax.swing.JSeparator sepHeader;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtIncidente;
    private javax.swing.JTextArea txtNotificaciones;
    // End of variables declaration//GEN-END:variables

    

    private static class PeliculaComboItem {
        private final Pelicula pelicula;

        public PeliculaComboItem(Pelicula pelicula) {
            this.pelicula = pelicula;
        }

        public Pelicula getPelicula() {
            return pelicula;
        }

        @Override
        public String toString() {
            return pelicula.getTitulo() + " (" + pelicula.getClasificacion() + ")";
        }
    }

    private static class FuncionComboItem {
        private final Funcion funcion;

        public FuncionComboItem(Funcion funcion) {
            this.funcion = funcion;
        }

        public Funcion getFuncion() {
            return funcion;
        }

        @Override
        public String toString() {
            return "Horario: " + funcion.getHorario().toString() + " | Sala: " + funcion.getIdSala() + " | Base: $" + String.format("%,d", (int)funcion.getPrecio().doubleValue());
        }
    }

    private static class ClienteComboItem {
        private final Cliente cliente;

        public ClienteComboItem(Cliente cliente) {
            this.cliente = cliente;
        }

        public Cliente getCliente() {
            return cliente;
        }

        @Override
        public String toString() {
            return cliente.getNombre() + " (RUT: " + cliente.getRut() + ")";
        }
    }
}
