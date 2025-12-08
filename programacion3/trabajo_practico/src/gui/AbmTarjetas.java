package programacion3.trabajo_practico.src.gui;

import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceTarjetaCredito;
import programacion3.trabajo_practico.src.service.ServiceMoneda;

public class AbmTarjetas extends JPanelBase {
  // * Atributos
  UsuarioCliente usuario;
  ServiceTarjetaCredito serviceTarjetaCredito;
  ServiceMoneda serviceMoneda;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonAgregar;
  JButton jButtonAgregarConsumo;
  JButton jButtonVerConsumos;
  JButton jButtonModificar;
  JButton jButtonEliminar;
  JLabel jLabelUsuario;
  JLabel jLabelId;
  JLabel jLabelPeriodo;
  JTable jTableTarjetas;

  // * Constructor
  public AbmTarjetas(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    String usuarioString = contexto.get("usuario");
    Integer idUsuario = Integer.valueOf(contexto.get("id_usuario"));
    panel.jFrame.setTitle("Tarjetas: " + usuarioString);

    LocalDate fechaActual = LocalDate.now();

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 5));

    jLabelUsuario = new JLabel("Usuario: " + usuarioString);
    jLabelId = new JLabel("ID: " + idUsuario);
    jLabelPeriodo = new JLabel("Periodo: " + fechaActual.getMonthValue() + "/" + fechaActual.getYear());

    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JLabel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelId);
    jPanelLabels.add(new JLabel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelPeriodo);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 7));

    jButtonVolver = new JButton("Volver");
    jButtonAgregar = new JButton("Agregar");
    jButtonAgregarConsumo = new JButton("Agregar Consumo");
    jButtonVerConsumos = new JButton("Ver Consumos");
    jButtonModificar = new JButton("Modificar");
    jButtonEliminar = new JButton("Eliminar");

    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JPanel(), BorderLayout.CENTER);
    jPanelBotones.add(jButtonAgregar);
    jPanelBotones.add(jButtonAgregarConsumo);
    jPanelBotones.add(jButtonVerConsumos);
    jPanelBotones.add(jButtonModificar);
    jPanelBotones.add(jButtonEliminar);
    actualPanel.add(jPanelBotones, BorderLayout.CENTER);

    usuario = new UsuarioCliente(
        contexto.get("nombre_usuario"),
        contexto.get("apellido_usuario"),
        usuarioString,
        idUsuario);

    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1, 1));

    jTableTarjetas = new JTable(construirTablaTarjetas());
    JScrollPane jScrollPane = new JScrollPane(jTableTarjetas);
    jPanelTabla.add(jScrollPane);
    actualPanel.add(jPanelTabla, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "3");
      panel.mostrar(prev, contexto);
    });

    jButtonAgregar.addActionListener(e -> {
      agregarTarjeta();
    });

    jButtonAgregarConsumo.addActionListener(e -> {
      TarjetaCredito tarjeta = getTarjetaSeleccionada();
      if (tarjeta == null)
        return;
      agregarConsumo(tarjeta);
    });

    jButtonVerConsumos.addActionListener(e -> {
      TarjetaCredito tarjeta = getTarjetaSeleccionada();
      if (tarjeta == null)
        return;
      contexto.put("prev", "6");
      contexto.put("usuario", usuarioString);
      contexto.put("id_usuario", String.valueOf(idUsuario));
      contexto.put("id_tarjeta", String.valueOf(tarjeta.getId()));
      contexto.put("nro_tarjeta", String.valueOf(tarjeta.getNumero()));
      panel.mostrar(7, contexto);
    });

    jButtonModificar.addActionListener(e -> {
      TarjetaCredito tarjeta = getTarjetaSeleccionada();
      if (tarjeta == null)
        return;
      modificarTarjeta(tarjeta);
    });

    jButtonEliminar.addActionListener(e -> {
      TarjetaCredito tarjeta = getTarjetaSeleccionada();
      if (tarjeta == null)
        return;
      eliminarTarjeta(tarjeta);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  // todo: posiblemente, agregar un ComboBox que permita seleccionar
  // la moneda para calc. el total
  private DefaultTableModel construirTablaTarjetas() {
    List<TarjetaCredito> tarjetas = new ArrayList<>();
    Vector<String> columnas = new Vector<>(5);
    columnas.addElement("ID");
    columnas.addElement("Numero"); // Últimos 4 dígitos
    columnas.addElement("Vencimiento");
    columnas.addElement("Límite");
    columnas.addElement("Consumos"); // Del mes actual

    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);

    try {
      serviceTarjetaCredito = new ServiceTarjetaCredito();
      tarjetas = serviceTarjetaCredito.consultarTodosConConsumo(usuario);
      if (tarjetas == null)
        return resultado;

      for (TarjetaCredito t : tarjetas) {
        String numeroTarjeta = String.valueOf(t.getNumero());
        resultado.addRow(new Object[] {
            t.getId(),
            "**** " + numeroTarjeta.substring(numeroTarjeta.length() - 4),
            t.getFechaVencimiento(),
            t.getLimite(),
            t.getConsumos().stream().mapToDouble(c -> c.getCantidad()).sum()
        });
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return resultado;
  }

  private TarjetaCredito getTarjetaSeleccionada() {
    int filaSeleccionada = jTableTarjetas.getSelectedRow();
    if (filaSeleccionada == -1) {
      JOptionPane.showMessageDialog(null, "Debe seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    int idTarjeta = Integer.parseInt(jTableTarjetas.getValueAt(filaSeleccionada, 0).toString());
    TarjetaCredito tarjeta = null;

    try {
      serviceTarjetaCredito = new ServiceTarjetaCredito();
      tarjeta = serviceTarjetaCredito.consultar(idTarjeta);
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    if (tarjeta == null) {
      JOptionPane.showMessageDialog(null, "Tarjeta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    return tarjeta;
  }

  private void agregarTarjeta() {
    JDialog jDialogAgregar = new JDialog();
    jDialogAgregar.setTitle("Agregar Tarjeta");
    jDialogAgregar.setSize(400, 200);
    jDialogAgregar.setLayout(new GridLayout(6, 2));

    jDialogAgregar.add(new JLabel("Usuario: "));
    jDialogAgregar.add(new JLabel(usuario.getUsuario()));

    jDialogAgregar.add(new JLabel("Número: "));
    JTextField jTextFieldNumero = new JTextField();
    jTextFieldNumero.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (jTextFieldNumero.getText().length() >= 16)
          e.consume();
      }
    });
    jDialogAgregar.add(jTextFieldNumero);

    jDialogAgregar.add(new JLabel("Límite: "));
    JTextField jTextFieldLimite = new JTextField("100000");
    jDialogAgregar.add(jTextFieldLimite);

    LocalDate vencimientoDate = LocalDate.now().plusMonths(1);
    String vencimiento = vencimientoDate.getDayOfMonth() + "/" + vencimientoDate.getMonthValue() + "/"
        + vencimientoDate.getYear();

    jDialogAgregar.add(new JLabel("Vencimiento: "));
    JTextField jTextFieldVencimiento = new JTextField(vencimiento);
    jTextFieldVencimiento.setEditable(false);
    jDialogAgregar.add(jTextFieldVencimiento);

    jDialogAgregar.add(new JLabel("CVC: "));
    Random random = new Random();
    int cvc = 100 + random.nextInt(900);
    JPasswordField jPasswordFieldCVC = new JPasswordField(String.valueOf(cvc));
    jPasswordFieldCVC.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (jPasswordFieldCVC.getPassword().length >= 3)
          e.consume();
      }
    });
    jDialogAgregar.add(jPasswordFieldCVC);

    JButton jButtonAgregar = new JButton("Agregar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jDialogAgregar.add(jButtonAgregar);
    jDialogAgregar.add(jButtonCancelar);

    jButtonAgregar.addActionListener(e -> {
      String limiteString = jTextFieldLimite.getText();
      String numeroString = jTextFieldNumero.getText();
      String cvcString = new String(jPasswordFieldCVC.getPassword());
      if (limiteString.isEmpty() || numeroString.isEmpty() || cvcString.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        Double.parseDouble(limiteString);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "El límite debe ser un número", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (Integer.valueOf(limiteString) < 1000) {
        JOptionPane.showMessageDialog(null, "El límite debe ser mayor a 1000", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (!numeroString.matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "El número de tarjeta debe ser un número", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (numeroString.length() != 16) {
        JOptionPane.showMessageDialog(null, "El número debe tener exactamente 16 dígitos", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        Integer.parseInt(cvcString);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "El CVC debe ser un número", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (cvcString.length() != 3) {
        JOptionPane.showMessageDialog(null, "El CVC debe tener exactamente 3 dígitos", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        serviceTarjetaCredito = new ServiceTarjetaCredito();
        serviceTarjetaCredito.insertar(
            new TarjetaCredito(
                numeroString,
                vencimientoDate,
                Integer.parseInt(cvcString),
                Double.parseDouble(limiteString)),
            usuario);
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogAgregar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogAgregar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jDialogAgregar.setLocationRelativeTo(null);
    jDialogAgregar.setVisible(true);
  }

  private void agregarConsumo(TarjetaCredito tarjeta) {
    JDialog jDialogAgregarConsumo = new JDialog();
    jDialogAgregarConsumo.setTitle("Agregar Consumo");
    jDialogAgregarConsumo.setSize(400, 200);
    jDialogAgregarConsumo.setLayout(new GridLayout(6, 2));

    jDialogAgregarConsumo.add(new JLabel("Usuario: "));
    jDialogAgregarConsumo.add(new JLabel(usuario.getUsuario()));

    jDialogAgregarConsumo.add(new JLabel("Tarjeta: "));
    jDialogAgregarConsumo.add(new JLabel(
        "**** " + String.valueOf(tarjeta.getNumero()).substring(String.valueOf(tarjeta.getNumero()).length() - 4)));

    jDialogAgregarConsumo.add(new JLabel("Cantidad: "));
    JTextField jTextFieldCantidad = new JTextField();
    jDialogAgregarConsumo.add(jTextFieldCantidad);

    jDialogAgregarConsumo.add(new JLabel("Fecha: "));
    JTextField jTextFieldFecha = new JTextField();
    jTextFieldFecha.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (jTextFieldFecha.getText().length() >= 10)
          e.consume();
      }
    });
    jTextFieldFecha.setText(LocalDate.now().toString());
    jDialogAgregarConsumo.add(jTextFieldFecha);

    jDialogAgregarConsumo.add(new JLabel("Moneda: "));
    JComboBox<String> jComboBoxMoneda = new JComboBox<>();
    jDialogAgregarConsumo.add(jComboBoxMoneda);
    // Cargar monedas a combo box
    try {
      List<Moneda> monedas = new ArrayList<>();
      serviceMoneda = new ServiceMoneda();
      monedas = serviceMoneda.consultarTodos();
      jComboBoxMoneda.addItem("");
      for (Moneda moneda : monedas) {
        jComboBoxMoneda.addItem(moneda.getCodigo());
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    JButton jButtonAgregar = new JButton("Agregar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jDialogAgregarConsumo.add(jButtonAgregar);
    jDialogAgregarConsumo.add(jButtonCancelar);

    jButtonAgregar.addActionListener(e -> {
      try {
        serviceTarjetaCredito = new ServiceTarjetaCredito();
        Consumo consumo = serviceTarjetaCredito.validarConsumo(
            jTextFieldCantidad.getText(),
            jTextFieldFecha.getText(),
            jComboBoxMoneda.getSelectedItem().toString());
        tarjeta.agregarConsumo(consumo);
        serviceTarjetaCredito.modificarConConsumo(tarjeta);
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogAgregarConsumo.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogAgregarConsumo.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jDialogAgregarConsumo.setLocationRelativeTo(null);
    jDialogAgregarConsumo.setVisible(true);
  }

  private void modificarTarjeta(TarjetaCredito tarjeta) {
    JDialog jDialogModificar = new JDialog();
    jDialogModificar.setTitle("Modificar Tarjeta");
    jDialogModificar.setSize(400, 200);
    jDialogModificar.setLayout(new GridLayout(4, 2));

    jDialogModificar.add(new JLabel("Usuario: "));
    jDialogModificar.add(new JLabel(usuario.getUsuario()));

    jDialogModificar.add(new JLabel("Número: "));
    jDialogModificar.add(new JLabel(tarjeta.getNumero()));

    jDialogModificar.add(new JLabel("Límite: "));
    JTextField jTextFieldLimite = new JTextField(String.valueOf(tarjeta.getLimite()));
    jDialogModificar.add(jTextFieldLimite);

    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jDialogModificar.add(jButtonModificar);
    jDialogModificar.add(jButtonCancelar);

    jButtonModificar.addActionListener(e -> {
      String limiteString = jTextFieldLimite.getText();
      if (limiteString.isEmpty()) {
        JOptionPane.showMessageDialog(null, "El límite es obligatorio", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (Double.valueOf(limiteString) == tarjeta.getLimite()) {
        JOptionPane.showMessageDialog(null, "El límite no ha cambiado", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        Double.parseDouble(limiteString);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "El límite debe ser un número", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (Double.valueOf(limiteString) < 1000) {
        JOptionPane.showMessageDialog(null, "El límite debe ser mayor a 1000", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        serviceTarjetaCredito = new ServiceTarjetaCredito();
        tarjeta.setLimite(Double.parseDouble(limiteString));
        serviceTarjetaCredito.modificar(tarjeta);
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogModificar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogModificar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jDialogModificar.setLocationRelativeTo(null);
    jDialogModificar.setVisible(true);
  }

  private void eliminarTarjeta(TarjetaCredito tarjeta) {
    JDialog jDialogEliminar = new JDialog();
    jDialogEliminar.setTitle("Eliminar Tarjeta");
    jDialogEliminar.setSize(400, 200);
    jDialogEliminar.setLayout(new GridLayout(2, 2));

    JPanel jPanelMensaje = new JPanel();
    jPanelMensaje.add(new JLabel("¿Desea eliminar la tarjeta de ID " + tarjeta.getId() + "?"));
    jDialogEliminar.add(jPanelMensaje, BorderLayout.CENTER);

    JPanel jPanelBotonesEliminar = new JPanel();
    JButton jButtonConfirmar = new JButton("Confirmar");
    JButton jButtonCancelar = new JButton("Cancelar");
    jPanelBotonesEliminar.add(jButtonConfirmar);
    jPanelBotonesEliminar.add(jButtonCancelar);
    jDialogEliminar.add(jPanelBotonesEliminar, BorderLayout.SOUTH);

    jButtonConfirmar.addActionListener(e -> {
      try {
        serviceTarjetaCredito = new ServiceTarjetaCredito();
        serviceTarjetaCredito.eliminar(tarjeta.getId());
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogEliminar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogEliminar.dispose();
      jTableTarjetas.setModel(construirTablaTarjetas());
    });

    jDialogEliminar.setLocationRelativeTo(null);
    jDialogEliminar.setVisible(true);
  }

}
