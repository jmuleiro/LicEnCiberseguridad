package programacion3.trabajo_practico.src.gui;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.util.Map;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class HomeAdmin extends JPanelBase {
  //* Atributos
  JLabel jLabelBienvenido;
  JLabel jLabelUsuario;
  JButton jButtonAbmUsuarios;

  //* Constructor
  public HomeAdmin(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jLabelBienvenido = new JLabel("Bienvenido, " + contexto.get("nombre") + " " + contexto.get("apellido"));
    jLabelUsuario = new JLabel("Usuario: " + contexto.get("usuario"));
    actualPanel.setLayout(new GridLayout(2, 2));
    actualPanel.add(jLabelBienvenido);
    actualPanel.add(jLabelUsuario);
    jButtonAbmUsuarios = new JButton("ABM Usuarios");
    actualPanel.add(jButtonAbmUsuarios);

    jButtonAbmUsuarios.addActionListener(e -> {
      contexto.put("prev", "2");
      panel.mostrar(3, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }
}
