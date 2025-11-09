package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;
import java.util.Vector;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import programacion3.trabajo_practico.src.service.ServiceUsuarioCliente;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class AbmUsuarios extends JPanelBase {
  //* Atributos
  ServiceUsuarioCliente serviceUsuarioCliente;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonAgregar;
  JButton jButtonAdministrar;
  JButton jButtonModificar;
  JButton jButtonEliminar;
  JTable jTableUsuarios;

  //* Constructor
  public AbmUsuarios(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jButtonVolver = new JButton("Volver");
    jButtonAgregar = new JButton("Agregar");
    jButtonAdministrar = new JButton("Administrar");
    jButtonModificar = new JButton("Modificar");
    jButtonEliminar = new JButton("Eliminar");
    
    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "3");
      panel.mostrar(prev, contexto);
    });

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
    jPanelTabla.add(new JScrollPane(jTableUsuarios));

    actualPanel.add(jPanelTabla);

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  public DefaultTableModel construirTablaUsuarios() {
    List<UsuarioCliente> usuarios;
    Vector<String> columnas = new Vector<String>(4);
    columnas.addElement("ID");
    columnas.addElement("Nombre");
    columnas.addElement("Apellido");
    columnas.addElement("Usuario");
    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);
    try {
      serviceUsuarioCliente = new ServiceUsuarioCliente();
      usuarios = serviceUsuarioCliente.consultarTodos();
      
      if (usuarios == null)
        return resultado;

      for (UsuarioCliente u : usuarios) {
        Object [] fila = new Object[4];
        fila[0] = u.getId();
        fila[1] = u.getNombre();
        fila[2] = u.getApellido();
        fila[3] = u.getUsuario();
        resultado.addRow(fila);
      }
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e);
    }
    return resultado;
  }
}
