package view;

import db.DAOAsiento;
import db.DAOCliente;
import db.DAOFuncion;
import db.DAOPelicula;
import db.DAOReserva;
import db.DAOReservaAsiento;
import db.DAOSala;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Asiento;
import model.Cliente;
import model.Funcion;
import model.Pelicula;
import model.Reserva;
import model.ReservaAsiento;
import model.ReservaFactory;
import model.Sala;

public class AdminFrame extends JFrame {

    private DAOPelicula daoPelicula;
    private DAOSala daoSala;
    private DAOFuncion daoFuncion;
    private DAOAsiento daoAsiento;
    private DAOCliente daoCliente;
    private DAOReserva daoReserva;
    private DAOReservaAsiento daoReservaAsiento;

    // Track selected IDs for modification/deletion
    private int selectedPeliId = -1;
    private int selectedSalaId = -1;
    private int selectedFunId = -1;
    private int selectedAsiId = -1;
    private int selectedCliId = -1;
    private int selectedResId = -1;
    private int selectedRaId = -1;

    public AdminFrame() {
        initComponents();
        customInit();
        inicializarDAOs();
        
        // Cargar todo
        cargarTodo();
    }

    private void inicializarDAOs() {
        try {
            daoPelicula = new DAOPelicula();
            daoSala = new DAOSala();
            daoFuncion = new DAOFuncion();
            daoAsiento = new DAOAsiento();
            daoCliente = new DAOCliente();
            daoReserva = new DAOReserva();
            daoReservaAsiento = new DAOReservaAsiento();
        } catch (SQLException e) {
            System.err.println("Error al inicializar DAOs en AdminFrame: " + e.getMessage());
        }
    }

    private void customInit() {
        // Estilo Oscuro Premium
        getContentPane().setBackground(new Color(20, 18, 30));
        tabbedPane.setBackground(new Color(30, 28, 45));
        tabbedPane.setForeground(Color.WHITE);

        pnlPeliculas.setBackground(new Color(30, 28, 45));
        pnlSalas.setBackground(new Color(30, 28, 45));
        pnlFunciones.setBackground(new Color(30, 28, 45));
        pnlAsientos.setBackground(new Color(30, 28, 45));
        pnlClientes.setBackground(new Color(30, 28, 45));
        pnlReservas.setBackground(new Color(30, 28, 45));
        pnlResAsientos.setBackground(new Color(30, 28, 45));

        // Estilos de botones
        JButton[] buttons = {
            btnPeliCrear, btnPeliModificar, btnPeliEliminar, btnPeliLimpiar,
            btnSalaCrear, btnSalaModificar, btnSalaEliminar, btnSalaLimpiar,
            btnFunCrear, btnFunModificar, btnFunEliminar, btnFunLimpiar,
            btnAsiCrear, btnAsiModificar, btnAsiEliminar, btnAsiLimpiar,
            btnCliCrear, btnCliModificar, btnCliEliminar, btnCliLimpiar,
            btnResCrear, btnResModificar, btnResEliminar, btnResLimpiar,
            btnRaCrear, btnRaModificar, btnRaEliminar, btnRaLimpiar,
            btnVolver
        };

        for (JButton btn : buttons) {
            if (btn == btnVolver) {
                btn.setBackground(new Color(50, 45, 65));
            } else if (btn.getText().equals("Crear")) {
                btn.setBackground(new Color(130, 50, 180));
            } else if (btn.getText().equals("Modificar")) {
                btn.setBackground(new Color(60, 110, 180));
            } else if (btn.getText().equals("Eliminar")) {
                btn.setBackground(new Color(180, 50, 80));
            } else {
                btn.setBackground(new Color(60, 50, 80));
            }
            btn.setForeground(Color.WHITE);
        }

        // ================= SELECTION LISTENERS =================
        tblPeliculas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblPeliculas.getSelectedRow() != -1) {
                    seleccionarFilaPelicula();
                }
            }
        });

        tblSalas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblSalas.getSelectedRow() != -1) {
                    seleccionarFilaSala();
                }
            }
        });

        tblFunciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblFunciones.getSelectedRow() != -1) {
                    seleccionarFilaFuncion();
                }
            }
        });

        tblAsientos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblAsientos.getSelectedRow() != -1) {
                    seleccionarFilaAsiento();
                }
            }
        });

        tblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblClientes.getSelectedRow() != -1) {
                    seleccionarFilaCliente();
                }
            }
        });

        tblReservas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblReservas.getSelectedRow() != -1) {
                    seleccionarFilaReserva();
                }
            }
        });

        tblResAsientos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblResAsientos.getSelectedRow() != -1) {
                    seleccionarFilaResAsiento();
                }
            }
        });

        // ================= CRUD ACTION LISTENERS =================
        // Películas
        btnPeliCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearPelicula(); } });
        btnPeliModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarPelicula(); } });
        btnPeliEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarPelicula(); } });
        btnPeliLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarPeliculas(); } });

        // Salas
        btnSalaCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearSala(); } });
        btnSalaModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarSala(); } });
        btnSalaEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarSala(); } });
        btnSalaLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarSalas(); } });

        // Funciones
        btnFunCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearFuncion(); } });
        btnFunModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarFuncion(); } });
        btnFunEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarFuncion(); } });
        btnFunLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarFunciones(); } });

        // Asientos
        btnAsiCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearAsiento(); } });
        btnAsiModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarAsiento(); } });
        btnAsiEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarAsiento(); } });
        btnAsiLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarAsientos(); } });

        // Clientes
        btnCliCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearCliente(); } });
        btnCliModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarCliente(); } });
        btnCliEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarCliente(); } });
        btnCliLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarClientes(); } });

        // Reservas
        btnResCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearReserva(); } });
        btnResModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarReserva(); } });
        btnResEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarReserva(); } });
        btnResLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarReservas(); } });

        // Reservas Asientos
        btnRaCrear.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { crearResAsiento(); } });
        btnRaModificar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { modificarResAsiento(); } });
        btnRaEliminar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { eliminarResAsiento(); } });
        btnRaLimpiar.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { limpiarResAsientos(); } });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginRoleFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void cargarTodo() {
        cargarTablaPeliculas();
        cargarTablaSalas();
        cargarTablaFunciones();
        cargarTablaAsientos();
        cargarTablaClientes();
        cargarTablaReservas();
        cargarTablaResAsientos();
        
        cargarCombosFK();
    }

    private void cargarCombosFK() {
        try {
            // Películas
            cmbFunPelicula.removeAllItems();
            List<Pelicula> pelis = daoPelicula.getPelicula();
            for (Pelicula p : pelis) {
                cmbFunPelicula.addItem(new view.CajeroFrame.PeliculaComboItem(p));
            }

            // Salas
            cmbFunSala.removeAllItems();
            cmbAsiSala.removeAllItems();
            List<Sala> salas = daoSala.getSala();
            for (Sala s : salas) {
                cmbFunSala.addItem(new SalaComboItem(s));
                cmbAsiSala.addItem(new SalaComboItem(s));
            }

            // Clientes
            cmbResCliente.removeAllItems();
            List<Cliente> clis = daoCliente.getCliente();
            for (Cliente c : clis) {
                cmbResCliente.addItem(new view.CajeroFrame.ClienteComboItem(c));
            }

            // Funciones
            cmbResFuncion.removeAllItems();
            List<Funcion> funs = daoFuncion.getFuncion();
            for (Funcion f : funs) {
                cmbResFuncion.addItem(new view.CajeroFrame.FuncionComboItem(f));
            }

            // Reservas
            cmbRaReserva.removeAllItems();
            List<Reserva> res = daoReserva.getReserva();
            for (Reserva r : res) {
                cmbRaReserva.addItem(new ReservaComboItem(r));
            }

            // Asientos
            cmbRaAsiento.removeAllItems();
            List<Asiento> asis = daoAsiento.getAsiento();
            for (Asiento a : asis) {
                cmbRaAsiento.addItem(new AsientoComboItem(a));
            }

        } catch (SQLException ex) {
            System.err.println("Error al cargar comboboxes FK: " + ex.getMessage());
        }
    }

    // ================= HELPER TABLA LOADING =================
    private void cargarTablaPeliculas() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Título", "Clasificación", "Duración"}, 0);
            List<Pelicula> lista = daoPelicula.getPelicula();
            for (Pelicula p : lista) {
                model.addRow(new Object[]{p.getIdPelicula(), p.getTitulo(), p.getClasificacion(), p.getDuracion()});
            }
            tblPeliculas.setModel(model);
            limpiarPeliculas();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    private void cargarTablaSalas() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Capacidad"}, 0);
            List<Sala> lista = daoSala.getSala();
            for (Sala s : lista) {
                model.addRow(new Object[]{s.getIdSala(), s.getNombreSala(), s.getCapacidad()});
            }
            tblSalas.setModel(model);
            limpiarSalas();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    private void cargarTablaFunciones() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Pelicula ID", "Sala ID", "Horario", "Precio"}, 0);
        List<Funcion> lista = daoFuncion.getFuncion();
        for (Funcion f : lista) {
            model.addRow(new Object[]{f.getIdFuncion(), f.getIdPelicula(), f.getIdSala(), f.getHorario(), f.getPrecio()});
        }
        tblFunciones.setModel(model);
        limpiarFunciones();
    }

    private void cargarTablaAsientos() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Sala ID", "Fila", "Número"}, 0);
            List<Asiento> lista = daoAsiento.getAsiento();
            for (Asiento a : lista) {
                model.addRow(new Object[]{a.getIdAsiento(), a.getIdSala(), a.getFila(), a.getNumero()});
            }
            tblAsientos.setModel(model);
            limpiarAsientos();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    private void cargarTablaClientes() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo", "RUT"}, 0);
            List<Cliente> lista = daoCliente.getCliente();
            for (Cliente c : lista) {
                model.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getCorreo(), c.getRut()});
            }
            tblClientes.setModel(model);
            limpiarClientes();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    private void cargarTablaReservas() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Cliente ID", "Función ID", "Fecha", "Tipo Entrada", "Precio Final"}, 0);
            List<Reserva> lista = daoReserva.getReserva();
            for (Reserva r : lista) {
                model.addRow(new Object[]{r.getIdReserva(), r.getIdCliente(), r.getIdFuncion(), r.getFechaReserva(), r.getTipoEntrada(), r.getPrecioFinal()});
            }
            tblReservas.setModel(model);
            limpiarReservas();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    private void cargarTablaResAsientos() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Reserva ID", "Asiento ID"}, 0);
            List<ReservaAsiento> lista = daoReservaAsiento.getResAsiento();
            for (ReservaAsiento ra : lista) {
                model.addRow(new Object[]{ra.getIdReservaAsiento(), ra.getIdReserva(), ra.getIdAsiento()});
            }
            tblResAsientos.setModel(model);
            limpiarResAsientos();
        } catch (SQLException ex) {
            System.err.println("" + ex);
        }
    }

    // ================= SELECTION ACTIONS =================
    private void seleccionarFilaPelicula() {
        int row = tblPeliculas.getSelectedRow();
        selectedPeliId = (int) tblPeliculas.getValueAt(row, 0);
        txtPeliTitulo.setText((String) tblPeliculas.getValueAt(row, 1));
        txtPeliClasificacion.setText((String) tblPeliculas.getValueAt(row, 2));
        txtPeliDuracion.setText(tblPeliculas.getValueAt(row, 3).toString());
    }

    private void seleccionarFilaSala() {
        int row = tblSalas.getSelectedRow();
        selectedSalaId = (int) tblSalas.getValueAt(row, 0);
        txtSalaNombre.setText((String) tblSalas.getValueAt(row, 1));
        txtSalaCapacidad.setText(tblSalas.getValueAt(row, 2).toString());
    }

    private void seleccionarFilaFuncion() {
        int row = tblFunciones.getSelectedRow();
        selectedFunId = (int) tblFunciones.getValueAt(row, 0);
        int peliId = (int) tblFunciones.getValueAt(row, 1);
        int salaId = (int) tblFunciones.getValueAt(row, 2);
        
        // Select Pelicula Combo
        for (int i = 0; i < cmbFunPelicula.getItemCount(); i++) {
            if (cmbFunPelicula.getItemAt(i).getPelicula().getIdPelicula() == peliId) {
                cmbFunPelicula.setSelectedIndex(i);
                break;
            }
        }

        // Select Sala Combo
        for (int i = 0; i < cmbFunSala.getItemCount(); i++) {
            if (cmbFunSala.getItemAt(i).getSala().getIdSala() == salaId) {
                cmbFunSala.setSelectedIndex(i);
                break;
            }
        }

        txtFunHorario.setText(tblFunciones.getValueAt(row, 3).toString());
        txtFunPrecio.setText(tblFunciones.getValueAt(row, 4).toString());
    }

    private void seleccionarFilaAsiento() {
        int row = tblAsientos.getSelectedRow();
        selectedAsiId = (int) tblAsientos.getValueAt(row, 0);
        int salaId = (int) tblAsientos.getValueAt(row, 1);

        for (int i = 0; i < cmbAsiSala.getItemCount(); i++) {
            if (cmbAsiSala.getItemAt(i).getSala().getIdSala() == salaId) {
                cmbAsiSala.setSelectedIndex(i);
                break;
            }
        }

        txtAsiFila.setText((String) tblAsientos.getValueAt(row, 2));
        txtAsiNumero.setText(tblAsientos.getValueAt(row, 3).toString());
    }

    private void seleccionarFilaCliente() {
        int row = tblClientes.getSelectedRow();
        selectedCliId = (int) tblClientes.getValueAt(row, 0);
        txtCliNombre.setText((String) tblClientes.getValueAt(row, 1));
        txtCliCorreo.setText((String) tblClientes.getValueAt(row, 2));
        txtCliRut.setText((String) tblClientes.getValueAt(row, 3));
    }

    private void seleccionarFilaReserva() {
        int row = tblReservas.getSelectedRow();
        selectedResId = (int) tblReservas.getValueAt(row, 0);
        int cliId = (int) tblReservas.getValueAt(row, 1);
        int funId = (int) tblReservas.getValueAt(row, 2);

        for (int i = 0; i < cmbResCliente.getItemCount(); i++) {
            if (cmbResCliente.getItemAt(i).getCliente().getIdCliente() == cliId) {
                cmbResCliente.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cmbResFuncion.getItemCount(); i++) {
            if (cmbResFuncion.getItemAt(i).getFuncion().getIdFuncion() == funId) {
                cmbResFuncion.setSelectedIndex(i);
                break;
            }
        }

        txtResFecha.setText(tblReservas.getValueAt(row, 3).toString());
        cmbResTipo.setSelectedItem(tblReservas.getValueAt(row, 4).toString());
        txtResPrecio.setText(tblReservas.getValueAt(row, 5).toString());
    }

    private void seleccionarFilaResAsiento() {
        int row = tblResAsientos.getSelectedRow();
        selectedRaId = (int) tblResAsientos.getValueAt(row, 0);
        int resId = (int) tblResAsientos.getValueAt(row, 1);
        int asiId = (int) tblResAsientos.getValueAt(row, 2);

        for (int i = 0; i < cmbRaReserva.getItemCount(); i++) {
            if (cmbRaReserva.getItemAt(i).getReserva().getIdReserva() == resId) {
                cmbRaReserva.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cmbRaAsiento.getItemCount(); i++) {
            if (cmbRaAsiento.getItemAt(i).getAsiento().getIdAsiento() == asiId) {
                cmbRaAsiento.setSelectedIndex(i);
                break;
            }
        }
    }

    // ================= CRUD IMPLEMENTATION =================
    // PELICULAS
    private void crearPelicula() {
        try {
            String titulo = txtPeliTitulo.getText().trim();
            String clas = txtPeliClasificacion.getText().trim();
            String durStr = txtPeliDuracion.getText().trim();

            if (titulo.isEmpty() || clas.isEmpty() || durStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            LocalTime duracion;
            if (!durStr.contains(":")) {
                int mins = Integer.parseInt(durStr);
                duracion = LocalTime.of(mins / 60, mins % 60, 0);
            } else {
                String[] p = durStr.split(":");
                if (p.length == 2) duracion = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]), 0);
                else duracion = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            }

            Pelicula p = new Pelicula(0, titulo, duracion, clas);
            daoPelicula.crearPelicula(p);
            JOptionPane.showMessageDialog(this, "Película creada con éxito.");
            cargarTablaPeliculas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarPelicula() {
        if (selectedPeliId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una película."); return; }
        try {
            String titulo = txtPeliTitulo.getText().trim();
            String clas = txtPeliClasificacion.getText().trim();
            String durStr = txtPeliDuracion.getText().trim();

            LocalTime duracion;
            if (!durStr.contains(":")) {
                int mins = Integer.parseInt(durStr);
                duracion = LocalTime.of(mins / 60, mins % 60, 0);
            } else {
                String[] p = durStr.split(":");
                if (p.length == 2) duracion = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]), 0);
                else duracion = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            }

            Pelicula p = new Pelicula(selectedPeliId, titulo, duracion, clas);
            daoPelicula.actualizarPelicula(p);
            JOptionPane.showMessageDialog(this, "Película modificada.");
            cargarTablaPeliculas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarPelicula() {
        if (selectedPeliId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una película."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar esta película? (Puede fallar si tiene funciones asociadas)", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Pelicula p = new Pelicula();
            p.setIdPelicula(selectedPeliId);
            daoPelicula.eliminarPelicula(p);
            JOptionPane.showMessageDialog(this, "Película eliminada.");
            cargarTablaPeliculas();
            cargarCombosFK();
        }
    }

    private void limpiarPeliculas() {
        selectedPeliId = -1;
        txtPeliTitulo.setText("");
        txtPeliClasificacion.setText("");
        txtPeliDuracion.setText("");
        tblPeliculas.clearSelection();
    }

    // SALAS
    private void crearSala() {
        try {
            String nombre = txtSalaNombre.getText().trim();
            String capStr = txtSalaCapacidad.getText().trim();

            if (nombre.isEmpty() || capStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            if (nombre.length() > 10) {
                JOptionPane.showMessageDialog(this, "El nombre de la sala no puede superar los 10 caracteres.");
                return;
            }

            int cap = Integer.parseInt(capStr);
            Sala s = new Sala(0, cap, nombre);
            daoSala.crearSala(s);
            JOptionPane.showMessageDialog(this, "Sala creada con éxito.");
            cargarTablaSalas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarSala() {
        if (selectedSalaId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una sala."); return; }
        try {
            String nombre = txtSalaNombre.getText().trim();
            String capStr = txtSalaCapacidad.getText().trim();

            if (nombre.length() > 10) {
                JOptionPane.showMessageDialog(this, "El nombre de la sala no puede superar los 10 caracteres.");
                return;
            }

            int cap = Integer.parseInt(capStr);
            Sala s = new Sala(selectedSalaId, cap, nombre);
            daoSala.actualizarSala(s);
            JOptionPane.showMessageDialog(this, "Sala modificada.");
            cargarTablaSalas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarSala() {
        if (selectedSalaId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una sala."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar esta sala? (Puede fallar si tiene funciones o asientos)", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Sala s = new Sala();
            s.setIdSala(selectedSalaId);
            daoSala.eliminarSala(s);
            JOptionPane.showMessageDialog(this, "Sala eliminada.");
            cargarTablaSalas();
            cargarCombosFK();
        }
    }

    private void limpiarSalas() {
        selectedSalaId = -1;
        txtSalaNombre.setText("");
        txtSalaCapacidad.setText("");
        tblSalas.clearSelection();
    }

    // FUNCIONES
    private void crearFuncion() {
        try {
            view.CajeroFrame.PeliculaComboItem peliItem = (view.CajeroFrame.PeliculaComboItem) cmbFunPelicula.getSelectedItem();
            SalaComboItem salaItem = (SalaComboItem) cmbFunSala.getSelectedItem();
            String horStr = txtFunHorario.getText().trim();
            String precStr = txtFunPrecio.getText().trim();

            if (peliItem == null || salaItem == null || horStr.isEmpty() || precStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            String[] p = horStr.split(":");
            LocalTime horario = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
            double precio = Double.parseDouble(precStr);

            Funcion f = new Funcion(0, peliItem.getPelicula().getIdPelicula(), salaItem.getSala().getIdSala(), horario, precio);
            daoFuncion.crearFuncion(f);
            JOptionPane.showMessageDialog(this, "Función creada.");
            cargarTablaFunciones();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarFuncion() {
        if (selectedFunId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una función."); return; }
        try {
            view.CajeroFrame.PeliculaComboItem peliItem = (view.CajeroFrame.PeliculaComboItem) cmbFunPelicula.getSelectedItem();
            SalaComboItem salaItem = (SalaComboItem) cmbFunSala.getSelectedItem();
            String horStr = txtFunHorario.getText().trim();
            String precStr = txtFunPrecio.getText().trim();

            String[] p = horStr.split(":");
            LocalTime horario = LocalTime.of(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
            double precio = Double.parseDouble(precStr);

            Funcion f = new Funcion(selectedFunId, peliItem.getPelicula().getIdPelicula(), salaItem.getSala().getIdSala(), horario, precio);
            daoFuncion.actualizarFuncion(f);
            JOptionPane.showMessageDialog(this, "Función modificada.");
            cargarTablaFunciones();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarFuncion() {
        if (selectedFunId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una función."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar esta función?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Funcion f = new Funcion();
            f.setIdFuncion(selectedFunId);
            daoFuncion.eliminarFuncion(f);
            JOptionPane.showMessageDialog(this, "Función eliminada.");
            cargarTablaFunciones();
            cargarCombosFK();
        }
    }

    private void limpiarFunciones() {
        selectedFunId = -1;
        txtFunHorario.setText("");
        txtFunPrecio.setText("");
        tblFunciones.clearSelection();
    }

    // ASIENTOS
    private void crearAsiento() {
        try {
            SalaComboItem salaItem = (SalaComboItem) cmbAsiSala.getSelectedItem();
            String fila = txtAsiFila.getText().trim();
            String numStr = txtAsiNumero.getText().trim();

            if (salaItem == null || fila.isEmpty() || numStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            int num = Integer.parseInt(numStr);
            Asiento a = new Asiento(0, salaItem.getSala().getIdSala(), fila, num);
            daoAsiento.crearAsiento(a);
            JOptionPane.showMessageDialog(this, "Asiento creado.");
            cargarTablaAsientos();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarAsiento() {
        if (selectedAsiId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un asiento."); return; }
        try {
            SalaComboItem salaItem = (SalaComboItem) cmbAsiSala.getSelectedItem();
            String fila = txtAsiFila.getText().trim();
            String numStr = txtAsiNumero.getText().trim();

            int num = Integer.parseInt(numStr);
            Asiento a = new Asiento(selectedAsiId, salaItem.getSala().getIdSala(), fila, num);
            daoAsiento.actualizarAsiento(a);
            JOptionPane.showMessageDialog(this, "Asiento modificado.");
            cargarTablaAsientos();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarAsiento() {
        if (selectedAsiId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un asiento."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar asiento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Asiento a = new Asiento();
            a.setIdAsiento(selectedAsiId);
            daoAsiento.eliminarAsiento(a);
            JOptionPane.showMessageDialog(this, "Asiento eliminado.");
            cargarTablaAsientos();
            cargarCombosFK();
        }
    }

    private void limpiarAsientos() {
        selectedAsiId = -1;
        txtAsiFila.setText("");
        txtAsiNumero.setText("");
        tblAsientos.clearSelection();
    }

    // CLIENTES
    private void crearCliente() {
        try {
            String nombre = txtCliNombre.getText().trim();
            String correo = txtCliCorreo.getText().trim();
            String rut = txtCliRut.getText().trim();

            if (nombre.isEmpty() || rut.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y RUT son obligatorios.");
                return;
            }

            Cliente c = new Cliente(0, nombre, correo.isEmpty() ? "Sin correo" : correo, rut);
            daoCliente.crearCliente(c);
            JOptionPane.showMessageDialog(this, "Cliente creado.");
            cargarTablaClientes();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarCliente() {
        if (selectedCliId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un cliente."); return; }
        try {
            String nombre = txtCliNombre.getText().trim();
            String correo = txtCliCorreo.getText().trim();
            String rut = txtCliRut.getText().trim();

            Cliente c = new Cliente(selectedCliId, nombre, correo, rut);
            daoCliente.actualizarCliente(c);
            JOptionPane.showMessageDialog(this, "Cliente modificado.");
            cargarTablaClientes();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarCliente() {
        if (selectedCliId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un cliente."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Cliente c = new Cliente();
            c.setIdCliente(selectedCliId);
            daoCliente.eliminarCliente(c);
            JOptionPane.showMessageDialog(this, "Cliente eliminado.");
            cargarTablaClientes();
            cargarCombosFK();
        }
    }

    private void limpiarClientes() {
        selectedCliId = -1;
        txtCliNombre.setText("");
        txtCliCorreo.setText("");
        txtCliRut.setText("");
        tblClientes.clearSelection();
    }

    // RESERVAS
    private void crearReserva() {
        try {
            view.CajeroFrame.ClienteComboItem cliItem = (view.CajeroFrame.ClienteComboItem) cmbResCliente.getSelectedItem();
            view.CajeroFrame.FuncionComboItem funItem = (view.CajeroFrame.FuncionComboItem) cmbResFuncion.getSelectedItem();
            String fechaStr = txtResFecha.getText().trim();
            String tipoEntrada = (String) cmbResTipo.getSelectedItem();

            if (cliItem == null || funItem == null || fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            Date fecha = Date.valueOf(fechaStr);
            double basePrice = funItem.getFuncion().getPrecio();

            // Usar Factory para calcular precio final automáticamente con IVA
            Reserva r = ReservaFactory.crearReserva(tipoEntrada, cliItem.getCliente().getIdCliente(), funItem.getFuncion().getIdFuncion(), fecha, basePrice);
            
            daoReserva.crearReserva(r);
            JOptionPane.showMessageDialog(this, "Reserva creada con éxito (Precio calculado con IVA: $" + r.getPrecioFinal() + " CLP).");
            cargarTablaReservas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage() + ". Asegúrese de que la fecha tiene el formato YYYY-MM-DD.");
        }
    }

    private void modificarReserva() {
        if (selectedResId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una reserva."); return; }
        try {
            view.CajeroFrame.ClienteComboItem cliItem = (view.CajeroFrame.ClienteComboItem) cmbResCliente.getSelectedItem();
            view.CajeroFrame.FuncionComboItem funItem = (view.CajeroFrame.FuncionComboItem) cmbResFuncion.getSelectedItem();
            String fechaStr = txtResFecha.getText().trim();
            String tipoEntrada = (String) cmbResTipo.getSelectedItem();
            String precStr = txtResPrecio.getText().trim();

            Date fecha = Date.valueOf(fechaStr);
            int precio = Integer.parseInt(precStr);

            // Re-instanciar usando Factory pero forzar el ID
            Reserva r = ReservaFactory.crearReserva(tipoEntrada, cliItem.getCliente().getIdCliente(), funItem.getFuncion().getIdFuncion(), fecha, funItem.getFuncion().getPrecio());
            r.setIdReserva(selectedResId);
            r.setPrecioFinal(precio); // Permitir forzar el precio en modificación directa

            daoReserva.actualizarReserva(r);
            JOptionPane.showMessageDialog(this, "Reserva modificada.");
            cargarTablaReservas();
            cargarCombosFK();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarReserva() {
        if (selectedResId == -1) { JOptionPane.showMessageDialog(this, "Seleccione una reserva."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Reserva r = ReservaFactory.crearReservaDesdeDB("General");
            r.setIdReserva(selectedResId);
            daoReserva.eliminarReserva(r);
            JOptionPane.showMessageDialog(this, "Reserva eliminada.");
            cargarTablaReservas();
            cargarCombosFK();
        }
    }

    private void limpiarReservas() {
        selectedResId = -1;
        txtResFecha.setText("");
        txtResPrecio.setText("");
        tblReservas.clearSelection();
    }

    // RESERVAS ASIENTOS
    private void crearResAsiento() {
        try {
            ReservaComboItem resItem = (ReservaComboItem) cmbRaReserva.getSelectedItem();
            AsientoComboItem asiItem = (AsientoComboItem) cmbRaAsiento.getSelectedItem();

            if (resItem == null || asiItem == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            ReservaAsiento ra = new ReservaAsiento(0, resItem.getReserva().getIdReserva(), asiItem.getAsiento().getIdAsiento());
            daoReservaAsiento.crearResAsiento(ra);
            JOptionPane.showMessageDialog(this, "Asiento asignado a la reserva.");
            cargarTablaResAsientos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarResAsiento() {
        if (selectedRaId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un registro."); return; }
        try {
            ReservaComboItem resItem = (ReservaComboItem) cmbRaReserva.getSelectedItem();
            AsientoComboItem asiItem = (AsientoComboItem) cmbRaAsiento.getSelectedItem();

            ReservaAsiento ra = new ReservaAsiento(selectedRaId, resItem.getReserva().getIdReserva(), asiItem.getAsiento().getIdAsiento());
            daoReservaAsiento.actualizarResAsiento(ra);
            JOptionPane.showMessageDialog(this, "Asiento reservado modificado.");
            cargarTablaResAsientos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarResAsiento() {
        if (selectedRaId == -1) { JOptionPane.showMessageDialog(this, "Seleccione un registro."); return; }
        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar este asiento de la reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            ReservaAsiento ra = new ReservaAsiento();
            ra.setIdReservaAsiento(selectedRaId);
            daoReservaAsiento.eliminarResAsiento(ra);
            JOptionPane.showMessageDialog(this, "Registro eliminado.");
            cargarTablaResAsientos();
        }
    }

    private void limpiarResAsientos() {
        selectedRaId = -1;
        tblResAsientos.clearSelection();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        lblRightSpacer = new javax.swing.JLabel();
        lblBottomSpacer = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlPeliculas = new javax.swing.JPanel();
        scrollPeliculas = new javax.swing.JScrollPane();
        tblPeliculas = new javax.swing.JTable();
        lblPeliTitulo = new javax.swing.JLabel();
        txtPeliTitulo = new javax.swing.JTextField();
        lblPeliClasificacion = new javax.swing.JLabel();
        txtPeliClasificacion = new javax.swing.JTextField();
        lblPeliDuracion = new javax.swing.JLabel();
        txtPeliDuracion = new javax.swing.JTextField();
        btnPeliCrear = new javax.swing.JButton();
        btnPeliModificar = new javax.swing.JButton();
        btnPeliEliminar = new javax.swing.JButton();
        btnPeliLimpiar = new javax.swing.JButton();
        pnlSalas = new javax.swing.JPanel();
        scrollSalas = new javax.swing.JScrollPane();
        tblSalas = new javax.swing.JTable();
        lblSalaNombre = new javax.swing.JLabel();
        txtSalaNombre = new javax.swing.JTextField();
        lblSalaCapacidad = new javax.swing.JLabel();
        txtSalaCapacidad = new javax.swing.JTextField();
        btnSalaCrear = new javax.swing.JButton();
        btnSalaModificar = new javax.swing.JButton();
        btnSalaEliminar = new javax.swing.JButton();
        btnSalaLimpiar = new javax.swing.JButton();
        pnlFunciones = new javax.swing.JPanel();
        scrollFunciones = new javax.swing.JScrollPane();
        tblFunciones = new javax.swing.JTable();
        lblFunPelicula = new javax.swing.JLabel();
        cmbFunPelicula = new javax.swing.JComboBox<>();
        lblFunSala = new javax.swing.JLabel();
        cmbFunSala = new javax.swing.JComboBox<>();
        lblFunHorario = new javax.swing.JLabel();
        txtFunHorario = new javax.swing.JTextField();
        lblFunPrecio = new javax.swing.JLabel();
        txtFunPrecio = new javax.swing.JTextField();
        btnFunCrear = new javax.swing.JButton();
        btnFunModificar = new javax.swing.JButton();
        btnFunEliminar = new javax.swing.JButton();
        btnFunLimpiar = new javax.swing.JButton();
        pnlAsientos = new javax.swing.JPanel();
        scrollAsientos = new javax.swing.JScrollPane();
        tblAsientos = new javax.swing.JTable();
        lblAsiFila = new javax.swing.JLabel();
        txtAsiFila = new javax.swing.JTextField();
        lblAsiNumero = new javax.swing.JLabel();
        txtAsiNumero = new javax.swing.JTextField();
        lblAsiSala = new javax.swing.JLabel();
        cmbAsiSala = new javax.swing.JComboBox<>();
        btnAsiCrear = new javax.swing.JButton();
        btnAsiModificar = new javax.swing.JButton();
        btnAsiEliminar = new javax.swing.JButton();
        btnAsiLimpiar = new javax.swing.JButton();
        pnlClientes = new javax.swing.JPanel();
        scrollClientes = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        lblCliNombre = new javax.swing.JLabel();
        txtCliNombre = new javax.swing.JTextField();
        lblCliCorreo = new javax.swing.JLabel();
        txtCliCorreo = new javax.swing.JTextField();
        lblCliRut = new javax.swing.JLabel();
        txtCliRut = new javax.swing.JTextField();
        btnCliCrear = new javax.swing.JButton();
        btnCliModificar = new javax.swing.JButton();
        btnCliEliminar = new javax.swing.JButton();
        btnCliLimpiar = new javax.swing.JButton();
        pnlReservas = new javax.swing.JPanel();
        scrollReservas = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        lblResCliente = new javax.swing.JLabel();
        cmbResCliente = new javax.swing.JComboBox<>();
        lblResFuncion = new javax.swing.JLabel();
        cmbResFuncion = new javax.swing.JComboBox<>();
        lblResFecha = new javax.swing.JLabel();
        txtResFecha = new javax.swing.JTextField();
        lblResTipo = new javax.swing.JLabel();
        cmbResTipo = new javax.swing.JComboBox<>();
        lblResPrecio = new javax.swing.JLabel();
        txtResPrecio = new javax.swing.JTextField();
        btnResCrear = new javax.swing.JButton();
        btnResModificar = new javax.swing.JButton();
        btnResEliminar = new javax.swing.JButton();
        btnResLimpiar = new javax.swing.JButton();
        pnlResAsientos = new javax.swing.JPanel();
        scrollResAsientos = new javax.swing.JScrollPane();
        tblResAsientos = new javax.swing.JTable();
        lblRaReserva = new javax.swing.JLabel();
        cmbRaReserva = new javax.swing.JComboBox<>();
        lblRaAsiento = new javax.swing.JLabel();
        cmbRaAsiento = new javax.swing.JComboBox<>();
        btnRaCrear = new javax.swing.JButton();
        btnRaModificar = new javax.swing.JButton();
        btnRaEliminar = new javax.swing.JButton();
        btnRaLimpiar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CineVerse MultiPlex: Panel de Administración");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHeader.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(110, 230, 240));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("PANEL DE ADMINISTRACIÓN Y GESTIÓN DE ENTIDADES");
        getContentPane().add(lblHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 850, 30));

        pnlPeliculas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPeliculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollPeliculas.setViewportView(tblPeliculas);

        pnlPeliculas.add(scrollPeliculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblPeliTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblPeliTitulo.setText("Título:");
        pnlPeliculas.add(lblPeliTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));
        pnlPeliculas.add(txtPeliTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblPeliClasificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblPeliClasificacion.setText("Clasificación:");
        pnlPeliculas.add(lblPeliClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));
        pnlPeliculas.add(txtPeliClasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        lblPeliDuracion.setForeground(new java.awt.Color(255, 255, 255));
        lblPeliDuracion.setText("Duración (HH:MM:SS):");
        pnlPeliculas.add(lblPeliDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 100, 20));
        pnlPeliculas.add(txtPeliDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 180, 25));

        btnPeliCrear.setText("Crear");
        pnlPeliculas.add(btnPeliCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnPeliModificar.setText("Modificar");
        pnlPeliculas.add(btnPeliModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnPeliEliminar.setText("Eliminar");
        pnlPeliculas.add(btnPeliEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnPeliLimpiar.setText("Limpiar");
        pnlPeliculas.add(btnPeliLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Películas", pnlPeliculas);

        pnlSalas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSalas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollSalas.setViewportView(tblSalas);

        pnlSalas.add(scrollSalas, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblSalaNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblSalaNombre.setText("Nombre Sala:");
        pnlSalas.add(lblSalaNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));
        pnlSalas.add(txtSalaNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblSalaCapacidad.setForeground(new java.awt.Color(255, 255, 255));
        lblSalaCapacidad.setText("Capacidad:");
        pnlSalas.add(lblSalaCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));
        pnlSalas.add(txtSalaCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        btnSalaCrear.setText("Crear");
        pnlSalas.add(btnSalaCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnSalaModificar.setText("Modificar");
        pnlSalas.add(btnSalaModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnSalaEliminar.setText("Eliminar");
        pnlSalas.add(btnSalaEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnSalaLimpiar.setText("Limpiar");
        pnlSalas.add(btnSalaLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Salas", pnlSalas);

        pnlFunciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblFunciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollFunciones.setViewportView(tblFunciones);

        pnlFunciones.add(scrollFunciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblFunPelicula.setForeground(new java.awt.Color(255, 255, 255));
        lblFunPelicula.setText("Película:");
        pnlFunciones.add(lblFunPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));

        pnlFunciones.add(cmbFunPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblFunSala.setForeground(new java.awt.Color(255, 255, 255));
        lblFunSala.setText("Sala:");
        pnlFunciones.add(lblFunSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));

        pnlFunciones.add(cmbFunSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        lblFunHorario.setForeground(new java.awt.Color(255, 255, 255));
        lblFunHorario.setText("Horario (HH:MM):");
        pnlFunciones.add(lblFunHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 100, 20));
        pnlFunciones.add(txtFunHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 180, 25));

        lblFunPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblFunPrecio.setText("Precio Base:");
        pnlFunciones.add(lblFunPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 100, 20));
        pnlFunciones.add(txtFunPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 180, 25));

        btnFunCrear.setText("Crear");
        pnlFunciones.add(btnFunCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnFunModificar.setText("Modificar");
        pnlFunciones.add(btnFunModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnFunEliminar.setText("Eliminar");
        pnlFunciones.add(btnFunEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnFunLimpiar.setText("Limpiar");
        pnlFunciones.add(btnFunLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Funciones", pnlFunciones);

        pnlAsientos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblAsientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollAsientos.setViewportView(tblAsientos);

        pnlAsientos.add(scrollAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblAsiFila.setForeground(new java.awt.Color(255, 255, 255));
        lblAsiFila.setText("Fila (A-Z):");
        pnlAsientos.add(lblAsiFila, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));
        pnlAsientos.add(txtAsiFila, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblAsiNumero.setForeground(new java.awt.Color(255, 255, 255));
        lblAsiNumero.setText("Número Asiento:");
        pnlAsientos.add(lblAsiNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));
        pnlAsientos.add(txtAsiNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        lblAsiSala.setForeground(new java.awt.Color(255, 255, 255));
        lblAsiSala.setText("Sala:");
        pnlAsientos.add(lblAsiSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 100, 20));

        pnlAsientos.add(cmbAsiSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 180, 25));

        btnAsiCrear.setText("Crear");
        pnlAsientos.add(btnAsiCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnAsiModificar.setText("Modificar");
        pnlAsientos.add(btnAsiModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnAsiEliminar.setText("Eliminar");
        pnlAsientos.add(btnAsiEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnAsiLimpiar.setText("Limpiar");
        pnlAsientos.add(btnAsiLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Asientos", pnlAsientos);

        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollClientes.setViewportView(tblClientes);

        pnlClientes.add(scrollClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblCliNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblCliNombre.setText("Nombre:");
        pnlClientes.add(lblCliNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));
        pnlClientes.add(txtCliNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblCliCorreo.setForeground(new java.awt.Color(255, 255, 255));
        lblCliCorreo.setText("Correo:");
        pnlClientes.add(lblCliCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));
        pnlClientes.add(txtCliCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        lblCliRut.setForeground(new java.awt.Color(255, 255, 255));
        lblCliRut.setText("RUT:");
        pnlClientes.add(lblCliRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 100, 20));
        pnlClientes.add(txtCliRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 180, 25));

        btnCliCrear.setText("Crear");
        pnlClientes.add(btnCliCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnCliModificar.setText("Modificar");
        pnlClientes.add(btnCliModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnCliEliminar.setText("Eliminar");
        pnlClientes.add(btnCliEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnCliLimpiar.setText("Limpiar");
        pnlClientes.add(btnCliLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Clientes", pnlClientes);

        pnlReservas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollReservas.setViewportView(tblReservas);

        pnlReservas.add(scrollReservas, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblResCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblResCliente.setText("Cliente:");
        pnlReservas.add(lblResCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));

        pnlReservas.add(cmbResCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblResFuncion.setForeground(new java.awt.Color(255, 255, 255));
        lblResFuncion.setText("Función:");
        pnlReservas.add(lblResFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));

        pnlReservas.add(cmbResFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        lblResFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblResFecha.setText("Fecha (YYYY-MM-DD):");
        pnlReservas.add(lblResFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 110, 20));
        pnlReservas.add(txtResFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, 170, 25));

        lblResTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblResTipo.setText("Tipo Entrada:");
        pnlReservas.add(lblResTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 100, 20));

        cmbResTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "VIP" }));
        pnlReservas.add(cmbResTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 180, 25));

        lblResPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblResPrecio.setText("Precio Final (IVA inc):");
        pnlReservas.add(lblResPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 110, 20));
        pnlReservas.add(txtResPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, 170, 25));

        btnResCrear.setText("Crear");
        pnlReservas.add(btnResCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnResModificar.setText("Modificar");
        pnlReservas.add(btnResModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnResEliminar.setText("Eliminar");
        pnlReservas.add(btnResEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnResLimpiar.setText("Limpiar");
        pnlReservas.add(btnResLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Reservas", pnlReservas);

        pnlResAsientos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblResAsientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        scrollResAsientos.setViewportView(tblResAsientos);

        pnlResAsientos.add(scrollResAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 460, 450));

        lblRaReserva.setForeground(new java.awt.Color(255, 255, 255));
        lblRaReserva.setText("Reserva (ID):");
        pnlResAsientos.add(lblRaReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 100, 20));

        pnlResAsientos.add(cmbRaReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 180, 25));

        lblRaAsiento.setForeground(new java.awt.Color(255, 255, 255));
        lblRaAsiento.setText("Asiento:");
        pnlResAsientos.add(lblRaAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 100, 20));

        pnlResAsientos.add(cmbRaAsiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 180, 25));

        btnRaCrear.setText("Crear");
        pnlResAsientos.add(btnRaCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 130, 35));

        btnRaModificar.setText("Modificar");
        pnlResAsientos.add(btnRaModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 130, 35));

        btnRaEliminar.setText("Eliminar");
        pnlResAsientos.add(btnRaEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 130, 35));

        btnRaLimpiar.setText("Limpiar");
        pnlResAsientos.add(btnRaLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 130, 35));

        tabbedPane.addTab("Asientos Reservados", pnlResAsientos);

        getContentPane().add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 810, 520));

        btnVolver.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnVolver.setText("Volver");
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 810, 30));
        getContentPane().add(lblRightSpacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 20, 1));
        getContentPane().add(lblBottomSpacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 850, 20));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsiCrear;
    private javax.swing.JButton btnAsiEliminar;
    private javax.swing.JButton btnAsiLimpiar;
    private javax.swing.JButton btnAsiModificar;
    private javax.swing.JButton btnCliCrear;
    private javax.swing.JButton btnCliEliminar;
    private javax.swing.JButton btnCliLimpiar;
    private javax.swing.JButton btnCliModificar;
    private javax.swing.JButton btnFunCrear;
    private javax.swing.JButton btnFunEliminar;
    private javax.swing.JButton btnFunLimpiar;
    private javax.swing.JButton btnFunModificar;
    private javax.swing.JButton btnPeliCrear;
    private javax.swing.JButton btnPeliEliminar;
    private javax.swing.JButton btnPeliLimpiar;
    private javax.swing.JButton btnPeliModificar;
    private javax.swing.JButton btnRaCrear;
    private javax.swing.JButton btnRaEliminar;
    private javax.swing.JButton btnRaLimpiar;
    private javax.swing.JButton btnRaModificar;
    private javax.swing.JButton btnResCrear;
    private javax.swing.JButton btnResEliminar;
    private javax.swing.JButton btnResLimpiar;
    private javax.swing.JButton btnResModificar;
    private javax.swing.JButton btnSalaCrear;
    private javax.swing.JButton btnSalaEliminar;
    private javax.swing.JButton btnSalaLimpiar;
    private javax.swing.JButton btnSalaModificar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<AsientoComboItem> cmbRaAsiento;
    private javax.swing.JComboBox<ReservaComboItem> cmbRaReserva;
    private javax.swing.JComboBox<view.CajeroFrame.FuncionComboItem> cmbResFuncion;
    private javax.swing.JComboBox<view.CajeroFrame.ClienteComboItem> cmbResCliente;
    private javax.swing.JComboBox<String> cmbResTipo;
    private javax.swing.JComboBox<SalaComboItem> cmbAsiSala;
    private javax.swing.JComboBox<SalaComboItem> cmbFunSala;
    private javax.swing.JComboBox<view.CajeroFrame.PeliculaComboItem> cmbFunPelicula;
    private javax.swing.JLabel lblAsiFila;
    private javax.swing.JLabel lblAsiNumero;
    private javax.swing.JLabel lblAsiSala;
    private javax.swing.JLabel lblCliCorreo;
    private javax.swing.JLabel lblCliNombre;
    private javax.swing.JLabel lblCliRut;
    private javax.swing.JLabel lblBottomSpacer;
    private javax.swing.JLabel lblFunHorario;
    private javax.swing.JLabel lblFunPelicula;
    private javax.swing.JLabel lblFunPrecio;
    private javax.swing.JLabel lblFunSala;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblPeliClasificacion;
    private javax.swing.JLabel lblPeliDuracion;
    private javax.swing.JLabel lblPeliTitulo;
    private javax.swing.JLabel lblRaAsiento;
    private javax.swing.JLabel lblRaReserva;
    private javax.swing.JLabel lblRightSpacer;
    private javax.swing.JLabel lblResCliente;
    private javax.swing.JLabel lblResFecha;
    private javax.swing.JLabel lblResFuncion;
    private javax.swing.JLabel lblResPrecio;
    private javax.swing.JLabel lblResTipo;
    private javax.swing.JLabel lblSalaCapacidad;
    private javax.swing.JLabel lblSalaNombre;
    private javax.swing.JPanel pnlAsientos;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlFunciones;
    private javax.swing.JPanel pnlPeliculas;
    private javax.swing.JPanel pnlResAsientos;
    private javax.swing.JPanel pnlReservas;
    private javax.swing.JPanel pnlSalas;
    private javax.swing.JScrollPane scrollAsientos;
    private javax.swing.JScrollPane scrollClientes;
    private javax.swing.JScrollPane scrollFunciones;
    private javax.swing.JScrollPane scrollPeliculas;
    private javax.swing.JScrollPane scrollResAsientos;
    private javax.swing.JScrollPane scrollReservas;
    private javax.swing.JScrollPane scrollSalas;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tblAsientos;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblFunciones;
    private javax.swing.JTable tblPeliculas;
    private javax.swing.JTable tblResAsientos;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTable tblSalas;
    private javax.swing.JTextField txtAsiFila;
    private javax.swing.JTextField txtAsiNumero;
    private javax.swing.JTextField txtCliCorreo;
    private javax.swing.JTextField txtCliNombre;
    private javax.swing.JTextField txtCliRut;
    private javax.swing.JTextField txtFunHorario;
    private javax.swing.JTextField txtFunPrecio;
    private javax.swing.JTextField txtPeliClasificacion;
    private javax.swing.JTextField txtPeliDuracion;
    private javax.swing.JTextField txtPeliTitulo;
    private javax.swing.JTextField txtResFecha;
    private javax.swing.JTextField txtResPrecio;
    private javax.swing.JTextField txtSalaCapacidad;
    private javax.swing.JTextField txtSalaNombre;
    // End of variables declaration//GEN-END:variables

    // Combo Items definition
    public static class SalaComboItem {
        private final Sala sala;
        public SalaComboItem(Sala s) { this.sala = s; }
        public Sala getSala() { return sala; }
        @Override public String toString() { return sala.getNombreSala() + " (ID: " + sala.getIdSala() + ")"; }
    }

    public static class ReservaComboItem {
        private final Reserva reserva;
        public ReservaComboItem(Reserva r) { this.reserva = r; }
        public Reserva getReserva() { return reserva; }
        @Override public String toString() { return "ID: " + reserva.getIdReserva() + " | Cliente ID: " + reserva.getIdCliente(); }
    }

    public static class AsientoComboItem {
        private final Asiento asiento;
        public AsientoComboItem(Asiento a) { this.asiento = a; }
        public Asiento getAsiento() { return asiento; }
        @Override public String toString() { return "ID: " + asiento.getIdAsiento() + " | Sala: " + asiento.getIdSala() + " | Fila " + asiento.getFila() + " - N° " + asiento.getNumero(); }
    }
}
