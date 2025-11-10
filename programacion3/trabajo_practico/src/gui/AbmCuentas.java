package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.service.ServiceCajaAhorro;
import programacion3.trabajo_practico.src.service.ServiceCuentaCorriente;
import programacion3.trabajo_practico.src.service.ServiceException;

public class AbmCuentas extends JPanelBase {
  //* Atributos
  ServiceCuentaCorriente serviceCuentaCorriente;
  ServiceCajaAhorro serviceCajaAhorro;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonAgregar;
  JButton jButtonAdministrar;
  JButton jButtonModificar;
  JButton jButtonEliminar;
  JLabel jLabelUsuario;
  JLabel jLabelId;
  JTable jTableCuentas;

  //* Constructor
  public AbmCuentas(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 2));
    jLabelUsuario = new JLabel("Usuario: " + contexto.get("usuario"));
    jLabelId = new JLabel("ID: " + contexto.get("id_usuario"));
    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(jLabelId);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 6));
    jButtonVolver = new JButton("Volver");
    jButtonAgregar = new JButton("Agregar");
    jButtonAdministrar = new JButton("Administrar");
    jButtonModificar = new JButton("Modificar");
    jButtonEliminar = new JButton("Eliminar");
    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JPanel(), BorderLayout.CENTER);
    jPanelBotones.add(jButtonAgregar);
    jPanelBotones.add(jButtonAdministrar);
    jPanelBotones.add(jButtonModificar);
    jPanelBotones.add(jButtonEliminar);
    actualPanel.add(jPanelBotones, BorderLayout.CENTER);

    UsuarioCliente usuario = new UsuarioCliente(
      contexto.get("nombre_usuario"),
      contexto.get("apellido_usuario"),
      contexto.get("usuario"),
      Integer.valueOf(contexto.get("id_usuario"))
    );
    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1,1));
    jTableCuentas = new JTable(construirTablaCuentas(usuario));
    JScrollPane jScrollPane = new JScrollPane(jTableCuentas);
    jPanelTabla.add(jScrollPane);
    actualPanel.add(jPanelTabla, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "3");
      panel.mostrar(prev, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  private DefaultTableModel construirTablaCuentas(UsuarioCliente usuario) {
    List<Cuenta> cuentas = new ArrayList<>();
    Vector<String> columnas = new Vector<String>(4);
    columnas.addElement("ID");
    columnas.addElement("Tipo");
    columnas.addElement("Moneda");
    columnas.addElement("Saldo");

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
        Object [] fila = new Object[4];
        fila[0] = c.getId();
        fila[1] = c.getClass().getSimpleName();
        fila[2] = c.getMoneda().getNombre();
        fila[3] = c.getSaldo();
        resultado.addRow(fila);
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return resultado;
  }
}
