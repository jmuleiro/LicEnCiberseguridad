package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;
import java.util.Vector;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import programacion3.trabajo_practico.src.service.ServiceUsuarioCliente;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class AbmUsuarios extends JPanelBase {
  // * Atributos
  ServiceUsuarioCliente serviceUsuarioCliente;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonAgregar;
  JButton jButtonAdministrar;
  JButton jButtonModificar;
  JButton jButtonEliminar;
  JTable jTableUsuarios;

  // * Constructor
  public AbmUsuarios(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    try {
      iniciar();
    } catch (GUIException e) {
      System.out.println(e);
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void iniciar() throws GUIException {
    panel.jFrame.setTitle("Usuarios");

    jButtonVolver = new JButton("Volver");
    jButtonAgregar = new JButton("Agregar");
    jButtonAdministrar = new JButton("Administrar");
    jButtonModificar = new JButton("Modificar");
    jButtonEliminar = new JButton("Eliminar");

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 6));
    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JPanel(), BorderLayout.CENTER);
    jPanelBotones.add(jButtonAgregar);
    jPanelBotones.add(jButtonAdministrar);
    jPanelBotones.add(jButtonModificar);
    jPanelBotones.add(jButtonEliminar);

    actualPanel.add(jPanelBotones);

    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1, 1));
    jTableUsuarios = new JTable(construirTablaUsuarios());
    JScrollPane jScrollPane = new JScrollPane(jTableUsuarios);
    jPanelTabla.add(jScrollPane);

    actualPanel.add(jPanelTabla);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "3");
      panel.mostrar(prev, contexto);
    });

    jButtonAgregar.addActionListener(e -> {
      agregarUsuario();
    });

    jButtonAdministrar.addActionListener(e -> {
      UsuarioCliente usuario = getUsuarioSeleccionado();
      if (usuario == null)
        return;
      contexto.put("prev", "3");
      contexto.put("usuario", usuario.getUsuario());
      contexto.put("nombre_usuario", usuario.getNombre());
      contexto.put("apellido_usuario", usuario.getApellido());
      contexto.put("id_usuario", Integer.toString(usuario.getId()));
      panel.mostrar(4, contexto);
    });

    jButtonModificar.addActionListener(e -> {
      UsuarioCliente usuario = getUsuarioSeleccionado();
      if (usuario == null)
        return;
      modificarUsuario(usuario);
    });

    jButtonEliminar.addActionListener(e -> {
      UsuarioCliente usuario = getUsuarioSeleccionado();
      if (usuario == null)
        return;
      eliminarUsuario(usuario);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  private UsuarioCliente getUsuarioSeleccionado() {
    int filaSeleccionada = jTableUsuarios.getSelectedRow();
    if (filaSeleccionada == -1) {
      JOptionPane.showMessageDialog(null, "Debe seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }
    return new UsuarioCliente(
        jTableUsuarios.getValueAt(filaSeleccionada, 1).toString(),
        jTableUsuarios.getValueAt(filaSeleccionada, 2).toString(),
        jTableUsuarios.getValueAt(filaSeleccionada, 3).toString(),
        Integer.valueOf(jTableUsuarios.getValueAt(filaSeleccionada, 0).toString()));
  }

  private DefaultTableModel construirTablaUsuarios() {
    List<UsuarioCliente> usuarios;
    Vector<String> columnas = new Vector<String>(6);
    columnas.addElement("ID");
    columnas.addElement("Nombre");
    columnas.addElement("Apellido");
    columnas.addElement("Usuario");
    columnas.addElement("Tarjetas");
    columnas.addElement("Cuentas");
    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);
    try {
      serviceUsuarioCliente = new ServiceUsuarioCliente(contexto);
      usuarios = serviceUsuarioCliente.consultarTodosCompleto();

      if (usuarios == null)
        return resultado;

      for (UsuarioCliente u : usuarios) {
        Object[] fila = new Object[6];
        fila[0] = u.getId();
        fila[1] = u.getNombre();
        fila[2] = u.getApellido();
        fila[3] = u.getUsuario();
        fila[4] = u.getTarjetas().size();
        fila[5] = u.getCuentas().size();
        resultado.addRow(fila);
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return resultado;
  }

  private void agregarUsuario() {
    JDialog jDialogFormulario = new JDialog();
    jDialogFormulario.setTitle("Agregar Usuario");
    jDialogFormulario.setSize(400, 200);
    jDialogFormulario.setLayout(new GridLayout(4, 2));

    JLabel jLabelNombre = new JLabel("Nombre");
    JLabel jLabelApellido = new JLabel("Apellido");
    JLabel jLabelUsuario = new JLabel("Usuario");

    JTextField jTextFieldNombre = new JTextField();
    JTextField jTextFieldApellido = new JTextField();
    JTextField jTextFieldUsuario = new JTextField();

    jDialogFormulario.add(jLabelNombre);
    jDialogFormulario.add(jTextFieldNombre);
    jDialogFormulario.add(jLabelApellido);
    jDialogFormulario.add(jTextFieldApellido);
    jDialogFormulario.add(jLabelUsuario);
    jDialogFormulario.add(jTextFieldUsuario);

    JButton jButtonAceptar = new JButton("Aceptar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jDialogFormulario.add(jButtonAceptar);
    jDialogFormulario.add(jButtonCancelar);

    jButtonAceptar.addActionListener(e -> {
      try {
        serviceUsuarioCliente = new ServiceUsuarioCliente(contexto);
        serviceUsuarioCliente.agregarUsuario(
            jTextFieldNombre.getText(),
            jTextFieldApellido.getText(),
            jTextFieldUsuario.getText());
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, "Error al insertar: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogFormulario.dispose();
      jTableUsuarios.setModel(construirTablaUsuarios());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogFormulario.dispose();
    });

    jDialogFormulario.setLocationRelativeTo(null);
    jDialogFormulario.setVisible(true);
  }

  private void modificarUsuario(UsuarioCliente usuario) {
    JDialog jDialogModificar = new JDialog();
    jDialogModificar.setTitle("Modificar Usuario");
    jDialogModificar.setSize(400, 200);
    jDialogModificar.setLayout(new BorderLayout());

    String nombreOriginal = usuario.getNombre();
    String apellidoOriginal = usuario.getApellido();
    String usuarioOriginal = usuario.getUsuario();

    JPanel jPanelFormulario = new JPanel();
    jPanelFormulario.setLayout(new GridLayout(3, 2));

    JLabel jLabelNombre = new JLabel("Nombre");
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setText(nombreOriginal);
    jPanelFormulario.add(jLabelNombre);
    jPanelFormulario.add(jTextFieldNombre);

    JLabel jLabelApellido = new JLabel("Apellido");
    JTextField jTextFieldApellido = new JTextField();
    jTextFieldApellido.setText(apellidoOriginal);
    jPanelFormulario.add(jLabelApellido);
    jPanelFormulario.add(jTextFieldApellido);

    JLabel jLabelUsuario = new JLabel("Usuario");
    JTextField jTextFieldUsuario = new JTextField();
    jTextFieldUsuario.setText(usuarioOriginal);
    jPanelFormulario.add(jLabelUsuario);
    jPanelFormulario.add(jTextFieldUsuario);

    jDialogModificar.add(jPanelFormulario, BorderLayout.CENTER);

    JPanel jPanelBotonesModificar = new JPanel();
    jPanelBotonesModificar.setLayout(new GridLayout(1, 2));

    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonCancelar = new JButton("Cancelar");

    jPanelBotonesModificar.add(jButtonModificar);
    jPanelBotonesModificar.add(jButtonCancelar);

    jDialogModificar.add(jPanelBotonesModificar, BorderLayout.SOUTH);

    jButtonModificar.addActionListener(e -> {
      try {
        serviceUsuarioCliente = new ServiceUsuarioCliente(contexto);
        serviceUsuarioCliente.modificarUsuario(
            jTextFieldApellido.getText(),
            jTextFieldNombre.getText(),
            jTextFieldUsuario.getText(),
            usuario.getId());
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, "Error al modificar: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogModificar.dispose();
      jTableUsuarios.setModel(construirTablaUsuarios());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogModificar.dispose();
      jTableUsuarios.setModel(construirTablaUsuarios());
    });

    jDialogModificar.setLocationRelativeTo(null);
    jDialogModificar.setVisible(true);
  }

  private void eliminarUsuario(UsuarioCliente usuario) {
    JDialog jDialogEliminar = new JDialog();
    jDialogEliminar.setTitle("Eliminar Usuario");
    jDialogEliminar.setSize(400, 200);
    jDialogEliminar.setLayout(new BorderLayout());

    JPanel jPanelMensaje = new JPanel();
    jPanelMensaje.setLayout(new GridLayout(1, 1));
    JLabel jLabelMensaje = new JLabel(
        "¿Desea eliminar el usuario " + usuario.getNombre() + " " + usuario.getApellido() + "?");
    jPanelMensaje.add(jLabelMensaje);
    jDialogEliminar.add(jPanelMensaje, BorderLayout.CENTER);

    JPanel jPanelBotonesEliminar = new JPanel();
    jPanelBotonesEliminar.setLayout(new GridLayout(1, 2));
    JButton jButtonConfirmar = new JButton("Confirmar");
    JButton jButtonCancelar = new JButton("Cancelar");
    jPanelBotonesEliminar.add(jButtonConfirmar);
    jPanelBotonesEliminar.add(jButtonCancelar);
    jDialogEliminar.add(jPanelBotonesEliminar, BorderLayout.SOUTH);

    jButtonConfirmar.addActionListener(e -> {
      try {
        serviceUsuarioCliente = new ServiceUsuarioCliente(contexto);
        serviceUsuarioCliente.eliminar(usuario.getId());
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, "Error al insertar: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
      jDialogEliminar.dispose();
      jTableUsuarios.setModel(construirTablaUsuarios());
    });

    jButtonCancelar.addActionListener(e -> {
      jDialogEliminar.dispose();
      jTableUsuarios.setModel(construirTablaUsuarios());
    });

    jDialogEliminar.setLocationRelativeTo(null);
    jDialogEliminar.setVisible(true);
  }
}
