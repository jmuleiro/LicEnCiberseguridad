package programacion3.trabajo_practico.src.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.Map;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceUsuarioAdmin;

public class HomeAdmin extends JPanelBase {
  // * Atributos
  ServiceUsuarioAdmin serviceUsuarioAdmin;
  JLabel jLabelBienvenido;
  JLabel jLabelUsuario;
  JButton jButtonAbmUsuarios;
  JButton jButtonGestion;
  JButton jButtonSalir;
  JPanel jPanelLabels;
  JPanel jPanelBotones;

  // * Constructor
  public HomeAdmin(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    actualPanel.setLayout(new GridLayout(2, 2));
    panel.jFrame.setTitle("Home");

    jLabelBienvenido = new JLabel("Bienvenido, " + contexto.get("nombre") + " " + contexto.get("apellido"));
    jLabelUsuario = new JLabel("Usuario: " + contexto.get("usuario"));

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 2));

    jPanelLabels.add(jLabelBienvenido);
    jPanelLabels.add(jLabelUsuario);

    actualPanel.add(jPanelLabels);

    jButtonAbmUsuarios = new JButton("Gestión de Usuarios");
    jButtonGestion = new JButton("Gestión General");
    jButtonSalir = new JButton("Salir");

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 3));

    jPanelBotones.add(jButtonAbmUsuarios);
    jPanelBotones.add(jButtonGestion);
    jPanelBotones.add(jButtonSalir);

    actualPanel.add(jPanelBotones);

    jButtonAbmUsuarios.addActionListener(e -> {
      contexto.put("prev", "2");
      panel.mostrar(3, contexto);
    });

    jButtonGestion.addActionListener(e -> {
      contexto.put("prev", "2");
      panel.mostrar(9, contexto);
    });

    jButtonSalir.addActionListener(e -> {
      try {
        serviceUsuarioAdmin = new ServiceUsuarioAdmin(contexto);
        serviceUsuarioAdmin.logout(contexto.get("usuario"));
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
      panel.jFrame.dispose();
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }
}
