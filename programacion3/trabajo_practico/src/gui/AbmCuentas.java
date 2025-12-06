package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.service.ServiceCajaAhorro;
import programacion3.trabajo_practico.src.service.ServiceCuentaCorriente;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceMoneda;

public class AbmCuentas extends JPanelBase {
  // * Atributos
  ServiceCuentaCorriente serviceCuentaCorriente;
  ServiceCajaAhorro serviceCajaAhorro;
  ServiceMoneda serviceMoneda;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonExtraer;
  JButton jButtonDepositar;
  JButton jButtonAgregar;
  JButton jButtonModificar;
  JButton jButtonEliminar;
  JLabel jLabelUsuario;
  JLabel jLabelId;
  JTable jTableCuentas;

  // * Constructor
  public AbmCuentas(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    String usuarioString = contexto.get("usuario");
    Integer idUsuario = Integer.valueOf(contexto.get("id_usuario"));
    panel.jFrame.setTitle("Cuentas: " + usuarioString);

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 3));

    jLabelUsuario = new JLabel("Usuario: " + usuarioString);
    jLabelId = new JLabel("ID: " + idUsuario);
    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JPanel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelId);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 7));
    jButtonVolver = new JButton("Volver");
    jButtonExtraer = new JButton("Extraer");
    jButtonDepositar = new JButton("Depositar");
    jButtonAgregar = new JButton("Agregar");
    jButtonModificar = new JButton("Modificar");
    jButtonEliminar = new JButton("Eliminar");
    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JPanel(), BorderLayout.CENTER);
    jPanelBotones.add(jButtonExtraer);
    jPanelBotones.add(jButtonDepositar);
    jPanelBotones.add(jButtonAgregar);
    jPanelBotones.add(jButtonModificar);
    jPanelBotones.add(jButtonEliminar);
    actualPanel.add(jPanelBotones, BorderLayout.CENTER);

    UsuarioCliente usuario = new UsuarioCliente(
        contexto.get("nombre_usuario"),
        contexto.get("apellido_usuario"),
        usuarioString,
        idUsuario);
    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1, 1));
    jTableCuentas = new JTable(construirTablaCuentas(usuario));
    JScrollPane jScrollPane = new JScrollPane(jTableCuentas);
    jPanelTabla.add(jScrollPane);
    actualPanel.add(jPanelTabla, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "3");
      panel.mostrar(prev, contexto);
    });

    jButtonExtraer.addActionListener(e -> {
      extraer(getCuentaSeleccionada(), usuario);
    });

    jButtonDepositar.addActionListener(e -> {
      Cuenta cuenta = getCuentaSeleccionada();
      if (cuenta == null)
        return;
      depositar(cuenta, usuario);
    });

    jButtonAgregar.addActionListener(e -> {
      agregarCuenta(usuario);
    });

    jButtonModificar.addActionListener(e -> {
      Cuenta cuenta = getCuentaSeleccionada();
      if (cuenta == null)
        return;
      modificarCuenta(cuenta, usuario);
    });

    jButtonEliminar.addActionListener(e -> {
      Cuenta cuenta = getCuentaSeleccionada();
      if (cuenta == null)
        return;
      eliminarCuenta(cuenta, usuario);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  private Cuenta getCuentaSeleccionada() {
    int filaSeleccionada = jTableCuentas.getSelectedRow();
    if (filaSeleccionada == -1) {
      JOptionPane.showMessageDialog(null, "Debe seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    int idCuenta = Integer.valueOf(jTableCuentas.getValueAt(filaSeleccionada, 0).toString());
    String tipoCuenta = jTableCuentas.getValueAt(filaSeleccionada, 1).toString();

    try {
      if (tipoCuenta.equals(CajaAhorro.class.getSimpleName())) {
        serviceCajaAhorro = new ServiceCajaAhorro();
        return serviceCajaAhorro.consultar(idCuenta);
      } else {
        serviceCuentaCorriente = new ServiceCuentaCorriente();
        return serviceCuentaCorriente.consultar(idCuenta);
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
  }

  private void modificarCuenta(Cuenta cuenta, UsuarioCliente usuario) {
    JDialog jDialogModificar = new JDialog();
    jDialogModificar.setTitle("Modificar Cuenta");
    jDialogModificar.setSize(400, 200);
    jDialogModificar.setLayout(new GridLayout(7, 2));

    jDialogModificar.add(new JLabel("Cuenta: "));
    jDialogModificar.add(new JLabel(Integer.toString(cuenta.getId())));

    jDialogModificar.add(new JLabel("Tipo de Cuenta: "));
    if (cuenta instanceof CajaAhorro) {
      jDialogModificar.add(new JLabel("Caja de Ahorro"));
    } else {
      jDialogModificar.add(new JLabel("Cuenta Corriente"));
    }

    jDialogModificar.add(new JLabel("CBU: "));
    jDialogModificar.add(new JLabel(cuenta.getCbu()));

    jDialogModificar.add(new JLabel("Alias: "));
    JTextField jTextFieldAlias = new JTextField(cuenta.getAlias());
    jDialogModificar.add(jTextFieldAlias);

    JTextField jTextFieldInteresOGiro;

    if (cuenta instanceof CajaAhorro) {
      jDialogModificar.add(new JLabel("Interés: "));
      jTextFieldInteresOGiro = new JTextField(Double.toString(((CajaAhorro) cuenta).getPorcentajeInteres()));
      jDialogModificar.add(jTextFieldInteresOGiro);
    } else {
      jDialogModificar.add(new JLabel("Límite de Giro: "));
      jTextFieldInteresOGiro = new JTextField(Double.toString(((CuentaCorriente) cuenta).getLimiteGiro()));
      jDialogModificar.add(jTextFieldInteresOGiro);
    }

    JButton jButtonGuardar = new JButton("Guardar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jDialogModificar.add(jButtonGuardar);
    jDialogModificar.add(jButtonCancelar);

    jButtonGuardar.addActionListener(e -> {
      try {
        if (cuenta instanceof CajaAhorro) {
          CajaAhorro cajaAhorro = (CajaAhorro) cuenta;
          cajaAhorro.setAlias(jTextFieldAlias.getText());
          cajaAhorro.setPorcentajeInteres(Double.parseDouble(jTextFieldInteresOGiro.getText()));
          serviceCajaAhorro = new ServiceCajaAhorro();
          serviceCajaAhorro.modificar(cajaAhorro);
        } else {
          CuentaCorriente cuentaCorriente = (CuentaCorriente) cuenta;
          cuentaCorriente.setAlias(jTextFieldAlias.getText());
          cuentaCorriente.setLimiteGiro(Double.parseDouble(jTextFieldInteresOGiro.getText()));
          serviceCuentaCorriente = new ServiceCuentaCorriente();
          serviceCuentaCorriente.modificar(cuentaCorriente);
        }
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogModificar.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogModificar.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
    });

    jDialogModificar.setLocationRelativeTo(null);
    jDialogModificar.setVisible(true);
  }

  private DefaultTableModel construirTablaCuentas(UsuarioCliente usuario) {
    List<Cuenta> cuentas = new ArrayList<>();
    Vector<String> columnas = new Vector<String>(6);
    columnas.addElement("ID");
    columnas.addElement("Tipo");
    columnas.addElement("Moneda");
    columnas.addElement("Saldo");
    columnas.addElement("CBU");
    columnas.addElement("Alias");

    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);
    try {
      serviceCajaAhorro = new ServiceCajaAhorro();
      serviceCuentaCorriente = new ServiceCuentaCorriente();

      // Alternativa para evitar error de type mismatch por la lista
      cuentas.addAll(serviceCajaAhorro.consultarTodos(usuario));
      cuentas.addAll(serviceCuentaCorriente.consultarTodos(usuario));

      for (Cuenta c : cuentas) {
        Object[] fila = new Object[6];
        fila[0] = c.getId();
        fila[1] = c.getClass().getSimpleName();
        fila[2] = c.getMoneda().getCodigo();
        fila[3] = c.getSaldo();
        fila[4] = c.getCbu();
        if (c.getAlias() == "")
          fila[5] = "N/A";
        else
          fila[5] = c.getAlias();
        resultado.addRow(fila);
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return resultado;
  }

  private void extraer(Cuenta cuenta, UsuarioCliente usuario) {
    JDialog jDialogExtraer = new JDialog();
    jDialogExtraer.setTitle("Extraer");
    jDialogExtraer.setSize(400, 200);
    jDialogExtraer.setLayout(new GridLayout(4, 2));

    jDialogExtraer.add(new JLabel("Cuenta: "));
    jDialogExtraer.add(new JLabel(Integer.toString(cuenta.getId())));

    jDialogExtraer.add(new JLabel("Saldo actual: "));
    jDialogExtraer.add(new JLabel(String.valueOf(cuenta.getSaldo())));

    jDialogExtraer.add(new JLabel("Monto: "));
    JTextField jTextFieldMonto = new JTextField();
    jDialogExtraer.add(jTextFieldMonto);

    JButton jButtonExtraer = new JButton("Extraer");
    JButton jButtonCancelar = new JButton("Cancelar");
    jDialogExtraer.add(jButtonExtraer);
    jDialogExtraer.add(jButtonCancelar);

    jButtonExtraer.addActionListener(e -> {
      try {
        cuenta.extraer(Double.parseDouble(jTextFieldMonto.getText()));
        if (cuenta instanceof CajaAhorro) {
          serviceCajaAhorro = new ServiceCajaAhorro();
          serviceCajaAhorro.modificar((CajaAhorro) cuenta);
        } else {
          serviceCuentaCorriente = new ServiceCuentaCorriente();
          serviceCuentaCorriente.modificar((CuentaCorriente) cuenta);
        }
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogExtraer.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
    });

    jButtonCancelar.addActionListener(e -> jDialogExtraer.dispose());

    jDialogExtraer.setLocationRelativeTo(null);
    jDialogExtraer.setVisible(true);
  }

  private void depositar(Cuenta cuenta, UsuarioCliente usuario) {
    JDialog jDialogDepositar = new JDialog();
    jDialogDepositar.setTitle("Depositar");
    jDialogDepositar.setSize(400, 200);
    jDialogDepositar.setLayout(new GridLayout(4, 2));

    jDialogDepositar.add(new JLabel("Cuenta: "));
    jDialogDepositar.add(new JLabel(Integer.toString(cuenta.getId())));

    jDialogDepositar.add(new JLabel("Saldo actual: "));
    jDialogDepositar.add(new JLabel(String.valueOf(cuenta.getSaldo())));

    jDialogDepositar.add(new JLabel("Monto: "));
    JTextField jTextFieldMonto = new JTextField();
    jDialogDepositar.add(jTextFieldMonto);

    JButton jButtonDepositar = new JButton("Depositar");
    JButton jButtonCancelar = new JButton("Cancelar");
    jDialogDepositar.add(jButtonDepositar);
    jDialogDepositar.add(jButtonCancelar);

    jButtonDepositar.addActionListener(e -> {
      try {
        cuenta.depositar(Double.parseDouble(jTextFieldMonto.getText()));
        if (cuenta instanceof CajaAhorro) {
          serviceCajaAhorro = new ServiceCajaAhorro();
          serviceCajaAhorro.modificar((CajaAhorro) cuenta);
        } else {
          serviceCuentaCorriente = new ServiceCuentaCorriente();
          serviceCuentaCorriente.modificar((CuentaCorriente) cuenta);
        }
      } catch (ServiceException ex) {
        JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogDepositar.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
    });

    jButtonCancelar.addActionListener(e -> jDialogDepositar.dispose());

    jDialogDepositar.setLocationRelativeTo(null);
    jDialogDepositar.setVisible(true);
  }

  private void agregarCuenta(UsuarioCliente usuario) {
    JDialog jDialogFormulario = new JDialog();
    jDialogFormulario.setTitle("Agregar Cuenta");
    jDialogFormulario.setSize(400, 200);
    jDialogFormulario.setLayout(new GridLayout(7, 2));

    jDialogFormulario.add(new JLabel("Usuario: "));
    jDialogFormulario.add(new JLabel(usuario.getUsuario()));

    String tipoCajaAhorro = "Caja de Ahorro";
    String tipoCuentaCorriente = "Cuenta Corriente";
    String[] tipoOpciones = { "", tipoCajaAhorro, tipoCuentaCorriente };
    JComboBox<String> jComboBoxTipo = new JComboBox<>(tipoOpciones);
    jDialogFormulario.add(new JLabel("Tipo: "));
    jDialogFormulario.add(jComboBoxTipo);

    List<Moneda> monedas = new ArrayList<>();
    try {
      serviceMoneda = new ServiceMoneda();
      monedas = serviceMoneda.consultarTodos();
      String[] monedasOpciones = new String[monedas.size()];
      for (int i = 0; i < monedas.size(); i++) {
        monedasOpciones[i] = monedas.get(i).getCodigo();
      }

      JComboBox<String> jComboBoxMoneda = new JComboBox<>(monedasOpciones);
      jComboBoxMoneda.setEnabled(false);
      jDialogFormulario.add(new JLabel("Moneda: "));
      jDialogFormulario.add(jComboBoxMoneda);

      jDialogFormulario.add(new JLabel("Alias: "));
      JTextField jTextFieldAlias = new JTextField();
      jTextFieldAlias.setEnabled(false);
      jDialogFormulario.add(jTextFieldAlias);

      jDialogFormulario.add(new JLabel("CBU: "));
      JTextField jTextFieldCbu = new JTextField();
      jTextFieldCbu.setEnabled(false);
      jDialogFormulario.add(jTextFieldCbu);

      JLabel jLabelLimiteOPorcentaje = new JLabel();
      jDialogFormulario.add(jLabelLimiteOPorcentaje);
      JTextField jTextFieldLimiteOPorcentaje = new JTextField();
      jTextFieldLimiteOPorcentaje.setEnabled(false);
      jTextFieldLimiteOPorcentaje.setText("0");
      jDialogFormulario.add(jTextFieldLimiteOPorcentaje);

      JButton jButtonAgregar = new JButton("Agregar");
      JButton jButtonCancelar = new JButton("Cancelar");

      jButtonAgregar.setEnabled(false);

      jDialogFormulario.add(jButtonAgregar);
      jDialogFormulario.add(jButtonCancelar);

      jComboBoxTipo.addActionListener(e -> {
        String tipoSeleccionado = jComboBoxTipo.getSelectedItem().toString();
        if (tipoSeleccionado.equals(tipoCuentaCorriente))
          jLabelLimiteOPorcentaje.setText("Limite de Giro: ");
        else if (tipoSeleccionado.equals(tipoCajaAhorro))
          jLabelLimiteOPorcentaje.setText("Porcentaje de Interes: ");
        else
          return;

        jComboBoxMoneda.setEnabled(true);
        jTextFieldAlias.setEnabled(true);
        jTextFieldCbu.setEnabled(true);
        jTextFieldLimiteOPorcentaje.setEnabled(true);
        jButtonAgregar.setEnabled(true);
      });

      jButtonAgregar.addActionListener(e -> {
        if (jTextFieldCbu.getText().isEmpty() ||
            jTextFieldLimiteOPorcentaje.getText().isEmpty() ||
            jComboBoxMoneda.getSelectedItem().toString().isEmpty() ||
            jComboBoxTipo.getSelectedItem().toString().isEmpty()) {
          JOptionPane.showMessageDialog(null, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (jTextFieldCbu.getText().length() != 22) {
          JOptionPane.showMessageDialog(null, "CBU debe tener 22 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        try {
          Moneda moneda = serviceMoneda.consultar(jComboBoxMoneda.getSelectedItem().toString());
          if (jComboBoxTipo.getSelectedItem().toString().equals(tipoCajaAhorro)) {
            serviceCajaAhorro = new ServiceCajaAhorro();
            serviceCajaAhorro.insertar(
                new CajaAhorro(
                    moneda,
                    jTextFieldAlias.getText().toString(),
                    jTextFieldCbu.getText().toString(),
                    Double.valueOf(jTextFieldLimiteOPorcentaje.getText().toString()),
                    usuario.getId(),
                    0.0),
                usuario);
          } else {
            serviceCuentaCorriente = new ServiceCuentaCorriente();
            serviceCuentaCorriente.insertar(
                new CuentaCorriente(
                    moneda,
                    jTextFieldAlias.getText().toString(),
                    jTextFieldCbu.getText().toString(),
                    Double.valueOf(jTextFieldLimiteOPorcentaje.getText().toString()),
                    usuario.getId(),
                    0.0),
                usuario);
          }
        } catch (ServiceException exc) {
          JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
        jDialogFormulario.dispose();
        jTableCuentas.setModel(construirTablaCuentas(usuario));
      });

      jButtonCancelar.addActionListener(e ->

      {
        jDialogFormulario.dispose();
        jTableCuentas.setModel(construirTablaCuentas(usuario));
      });
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }

    jDialogFormulario.setLocationRelativeTo(null);
    jDialogFormulario.setVisible(true);
  }

  private void eliminarCuenta(Cuenta cuenta, UsuarioCliente usuario) {
    JDialog jDialogEliminar = new JDialog();
    jDialogEliminar.setTitle("Eliminar Cuenta");
    jDialogEliminar.setSize(400, 200);
    jDialogEliminar.setLayout(new BorderLayout());

    JPanel jPanelMensaje = new JPanel();
    jPanelMensaje.setLayout(new GridLayout(1, 1));
    jPanelMensaje.add(new JLabel("¿Desea eliminar la cuenta " + cuenta.getId() + "?"));
    jDialogEliminar.add(jPanelMensaje, BorderLayout.CENTER);

    JPanel jPanelBotonesEliminar = new JPanel();
    jPanelBotonesEliminar.setLayout(new GridLayout(1, 2));
    JButton jButtonConfirmar = new JButton("Confirmar");
    JButton jButtonCancelar = new JButton("Cancelar");
    jPanelBotonesEliminar.add(jButtonConfirmar);
    jPanelBotonesEliminar.add(jButtonCancelar);
    jDialogEliminar.add(jPanelBotonesEliminar, BorderLayout.SOUTH);

    jButtonConfirmar.addActionListener(e -> {
      if (cuenta.getSaldo() != 0) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar una cuenta con saldo o saldo negativo", "Error",
            JOptionPane.ERROR_MESSAGE);
        jDialogEliminar.dispose();
        jTableCuentas.setModel(construirTablaCuentas(usuario));
        return;
      }

      try {
        if (cuenta instanceof CajaAhorro) {
          serviceCajaAhorro = new ServiceCajaAhorro();
          serviceCajaAhorro.eliminar(cuenta.getId());
        } else {
          serviceCuentaCorriente = new ServiceCuentaCorriente();
          serviceCuentaCorriente.eliminar(cuenta.getId());
        }
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogEliminar.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogEliminar.dispose();
      jTableCuentas.setModel(construirTablaCuentas(usuario));
      return;
    });

    jDialogEliminar.setLocationRelativeTo(null);
    jDialogEliminar.setVisible(true);
  }
}
