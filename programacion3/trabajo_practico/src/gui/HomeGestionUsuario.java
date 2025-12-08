package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.time.LocalDate;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.service.ServiceCajaAhorro;
import programacion3.trabajo_practico.src.service.ServiceCuentaCorriente;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceMoneda;
import programacion3.trabajo_practico.src.service.ServiceUsuarioCliente;
import programacion3.trabajo_practico.src.entidades.Transferencia;

public class HomeGestionUsuario extends JPanelBase {
  // * Atributos
  ServiceMoneda serviceMoneda;
  ServiceUsuarioCliente serviceUsuarioCliente;
  ServiceCuentaCorriente serviceCuentaCorriente;
  ServiceCajaAhorro serviceCajaAhorro;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JLabel jLabelNombre;
  JLabel jLabelUsuario;
  JLabel jLabelId;
  JButton jButtonTransferir;
  JButton jButtonCuentas;
  JButton jButtonTarjetas;
  JButton jButtonVolver;

  // * Constructor
  public HomeGestionUsuario(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jPanelLabels = new JPanel();
    String usuarioString = contexto.get("usuario");
    Integer usuarioId = Integer.valueOf(contexto.get("id_usuario"));
    panel.jFrame.setTitle("Usuario: " + usuarioString);

    jPanelLabels.setLayout(new GridLayout(1, 5));

    jLabelNombre = new JLabel("Nombre: " + contexto.get("nombre_usuario") + " " + contexto.get("apellido_usuario"));
    jLabelUsuario = new JLabel("Usuario: " + usuarioString);
    jLabelId = new JLabel("ID: " + usuarioId);

    jPanelLabels.add(jLabelNombre);
    jPanelLabels.add(new JLabel());
    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JLabel());
    jPanelLabels.add(jLabelId);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 7));

    jButtonTransferir = new JButton("Transferir");
    jButtonCuentas = new JButton("Cuentas");
    jButtonTarjetas = new JButton("Tarjetas");
    jButtonVolver = new JButton("Volver");

    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JLabel());
    jPanelBotones.add(jButtonTransferir);
    jPanelBotones.add(new JLabel());
    jPanelBotones.add(jButtonCuentas);
    jPanelBotones.add(new JLabel());
    jPanelBotones.add(jButtonTarjetas);
    actualPanel.add(jPanelBotones, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "2");
      panel.mostrar(prev, contexto);
    });

    jButtonTransferir.addActionListener(e -> {
      transferir(usuarioId);
    });

    jButtonCuentas.addActionListener(e -> {
      contexto.put("prev", "4");
      panel.mostrar(5, contexto);
    });

    jButtonTarjetas.addActionListener(e -> {
      contexto.put("prev", "4");
      panel.mostrar(6, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  private void transferir(int usuarioId) {
    JDialog jDialogTransferir = new JDialog();
    jDialogTransferir.setTitle("Transferencia");
    jDialogTransferir.setSize(250, 300);
    jDialogTransferir.setLayout(new GridLayout(7, 2));

    try {
      jDialogTransferir.add(new JLabel("Usuario: "));
      JLabel jLabelUsuario = new JLabel();
      jDialogTransferir.add(jLabelUsuario);

      jDialogTransferir.add(new JLabel("Moneda: "));
      JComboBox<String> jComboBoxMoneda = new JComboBox<>();
      jComboBoxMoneda.addItem("");
      jDialogTransferir.add(jComboBoxMoneda);

      jDialogTransferir.add(new JLabel("Cuenta: "));
      JComboBox<String> jComboBoxCuenta = new JComboBox<>();
      jComboBoxCuenta.addItem("");
      jComboBoxCuenta.setEnabled(false);
      jDialogTransferir.add(jComboBoxCuenta);

      jDialogTransferir.add(new JLabel("Destino: "));
      JTextField jTextFieldDestino = new JTextField();
      jTextFieldDestino.setEnabled(false);
      jDialogTransferir.add(jTextFieldDestino);

      jDialogTransferir.add(new JLabel("Disponible: "));
      JLabel jLabelDisponible = new JLabel();
      jDialogTransferir.add(jLabelDisponible);

      jDialogTransferir.add(new JLabel("Monto: "));
      JTextField jTextFieldMonto = new JTextField();
      jTextFieldMonto.setEnabled(false);
      jDialogTransferir.add(jTextFieldMonto);

      JButton jButtonTransferir = new JButton("Transferir");
      jButtonTransferir.setEnabled(false);
      JButton jButtonCancelar = new JButton("Cancelar");

      jDialogTransferir.add(jButtonTransferir);
      jDialogTransferir.add(jButtonCancelar);

      serviceUsuarioCliente = new ServiceUsuarioCliente(contexto);
      UsuarioCliente usuario = serviceUsuarioCliente.consultar(usuarioId);
      if (usuario == null) {
        JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      jLabelUsuario.setText(usuario.getUsuario());

      serviceMoneda = new ServiceMoneda(contexto);
      List<Moneda> monedas = serviceMoneda.consultarTodos();
      for (Moneda m : monedas) {
        jComboBoxMoneda.addItem(m.getCodigo());
      }

      List<Cuenta> cuentasUsuario = new ArrayList<>();
      serviceCajaAhorro = new ServiceCajaAhorro(contexto);
      serviceCuentaCorriente = new ServiceCuentaCorriente(contexto);

      cuentasUsuario.addAll(serviceCajaAhorro.consultarTodos(usuario));
      cuentasUsuario.addAll(serviceCuentaCorriente.consultarTodos(usuario));

      jButtonCancelar.addActionListener(e -> {
        jDialogTransferir.dispose();
      });

      jComboBoxMoneda.addActionListener(e -> {
        String monedaSeleccionada = jComboBoxMoneda.getSelectedItem().toString();
        if (monedaSeleccionada.isEmpty())
          return;

        jComboBoxCuenta.removeAllItems();
        jComboBoxCuenta.addItem("");
        for (Cuenta c : cuentasUsuario) {
          String identificadorTipoCuenta = "CA";
          if (c instanceof CuentaCorriente)
            identificadorTipoCuenta = "CC";
          String codigoMoneda = c.getMoneda().getCodigo();
          if (codigoMoneda.equals(monedaSeleccionada))
            jComboBoxCuenta.addItem(identificadorTipoCuenta + " " + codigoMoneda + ": " + String.valueOf(c.getId()));
        }

        if (jComboBoxCuenta.getItemCount() == 1) {
          JOptionPane.showMessageDialog(null, "El usuario no tiene cuentas disponibles para la moneda seleccionada",
              "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        jComboBoxCuenta.addActionListener(e2 -> {
          String cuentaSeleccionada = jComboBoxCuenta.getSelectedItem().toString();
          if (cuentaSeleccionada.isEmpty())
            return;
          try {
            String[] partes = cuentaSeleccionada.split(" ");
            Cuenta cuenta;
            if (partes[0].equals("CA")) {
              serviceCajaAhorro = new ServiceCajaAhorro(contexto);
              cuenta = serviceCajaAhorro.consultar(Integer.valueOf(partes[2].strip()));
            } else {
              serviceCuentaCorriente = new ServiceCuentaCorriente(contexto);
              cuenta = serviceCuentaCorriente.consultar(Integer.valueOf(partes[2].strip()));
            }
            jLabelDisponible.setText(String.valueOf(cuenta.getSaldo()));
            jTextFieldMonto.setEnabled(true);
            jTextFieldDestino.setEnabled(true);
            jButtonTransferir.setEnabled(true);
          } catch (ServiceException exc) {
            JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
          }
        });
        jComboBoxCuenta.setEnabled(true);
      });

      jButtonTransferir.addActionListener(e -> {
        String cuentaSeleccionada = jComboBoxCuenta.getSelectedItem().toString();
        if (cuentaSeleccionada.isEmpty())
          return;

        String monto = jTextFieldMonto.getText();
        String destino = jTextFieldDestino.getText();

        if (monto.isEmpty() ||
            destino.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        try {
          serviceMoneda = new ServiceMoneda(contexto);
          String[] partes = cuentaSeleccionada.split(" ");
          Moneda moneda = serviceMoneda.consultar(partes[1].replace(":", ""));
          if (moneda == null) {
            JOptionPane.showMessageDialog(null, "Moneda no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }

          List<Cuenta> cuentas = new ArrayList<>();
          Cuenta cuentaOrigen;
          serviceCajaAhorro = new ServiceCajaAhorro(contexto);
          serviceCuentaCorriente = new ServiceCuentaCorriente(contexto);
          cuentas.addAll(serviceCajaAhorro.consultarTodos());
          cuentas.addAll(serviceCuentaCorriente.consultarTodos());

          for (Cuenta cuentaDestino : cuentas) {
            // Seguir hasta encontrar la cuenta
            if (!(Integer.toString(cuentaDestino.getId()).equals(destino)
                || cuentaDestino.getCbu().equals(destino)
                || cuentaDestino.getAlias().equals(destino)))
              continue;

            if (!(cuentaDestino.getMoneda().getCodigo().equals(moneda.getCodigo()))) {
              JOptionPane.showMessageDialog(null, "Las cuentas no son de la misma moneda", "Error",
                  JOptionPane.ERROR_MESSAGE);
              return;
            }

            Double montoD = Double.valueOf(monto);
            // Encontrar cuenta origen
            if (partes[0].equals("CA"))
              cuentaOrigen = serviceCajaAhorro.consultar(Integer.valueOf(partes[2]));
            else
              cuentaOrigen = serviceCuentaCorriente.consultar(Integer.valueOf(partes[2]));
            cuentaOrigen.extraer(montoD);
            cuentaDestino.depositar(montoD);

            Transferencia transferencia = new Transferencia(LocalDate.now(), montoD,
                moneda, "Transferencia", cuentaDestino, false);
            cuentaOrigen.agregarTransferencia(transferencia);

            // Actualizar cuenta origen en DB y agregar transferencia
            if (cuentaOrigen instanceof CuentaCorriente)
              serviceCuentaCorriente.modificarConTransferencia(((CuentaCorriente) cuentaOrigen));
            else
              serviceCajaAhorro.modificarConTransferencia(((CajaAhorro) cuentaOrigen));

            // Actualizar cuenta destino en DB
            if (cuentaDestino instanceof CuentaCorriente)
              serviceCuentaCorriente.modificar(((CuentaCorriente) cuentaDestino));
            else
              serviceCajaAhorro.modificar(((CajaAhorro) cuentaDestino));

            JOptionPane.showMessageDialog(null, "Transferencia realizada con éxito", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            jDialogTransferir.dispose();

            return;
          }
          JOptionPane.showMessageDialog(null, "Cuenta destino no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException exc) {
          JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
      });
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }

    jDialogTransferir.setLocationRelativeTo(null);
    jDialogTransferir.setVisible(true);
  }
}
