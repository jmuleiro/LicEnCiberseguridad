package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceUsuarioAdmin;

import java.util.Map;
import java.util.HashMap;

public class FormularioLoginAdmin extends JPanel {
  //* Atributos
  ServiceUsuarioAdmin serviceUsuarioAdmin;
  PanelManager panel;
  JPanel formularioLoginAdmin;

  // GUI
  JLabel jLabelUsuario;
  JLabel jLabelPassword;
  JTextField jTextFieldUsuario;
  JTextField jTextFieldPassword;
  JButton jButtonLogin;
  JButton jButtonSalir;

  //* Constructor
  public FormularioLoginAdmin(PanelManager panel) {
    this.panel = panel;
    iniciar();
  }

  public void iniciar() {
    formularioLoginAdmin = new JPanel();
    formularioLoginAdmin.setLayout(new GridLayout(3, 2));

    jLabelUsuario = new JLabel("Usuario");
    jLabelPassword = new JLabel("Password");

    jTextFieldUsuario = new JTextField();
    jTextFieldPassword = new JTextField();

    jButtonLogin = new JButton("Login");
    jButtonSalir = new JButton("Salir");

    formularioLoginAdmin.add(jLabelUsuario);
    formularioLoginAdmin.add(jTextFieldUsuario);
    formularioLoginAdmin.add(jLabelPassword);
    formularioLoginAdmin.add(jTextFieldPassword);
    formularioLoginAdmin.add(jButtonLogin);
    formularioLoginAdmin.add(jButtonSalir);

    jButtonLogin.addActionListener(e -> {
      try {
        serviceUsuarioAdmin = new ServiceUsuarioAdmin();
        UsuarioAdmin usuarioAdmin = serviceUsuarioAdmin.consultar(jTextFieldUsuario.getText());
        if (usuarioAdmin == null) {
          JOptionPane.showMessageDialog(null, "Usuario no encontrado");
          return;
        }
        Map<String, String> contexto = new HashMap<>();
        contexto.put("usuario", usuarioAdmin.getUsuario());
        contexto.put("nombre", usuarioAdmin.getNombre());
        contexto.put("apellido", usuarioAdmin.getApellido());
        panel.mostrar(2, contexto);
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, exc);
      }
    });

    jButtonSalir.addActionListener(e -> {
      panel.jFrame.dispose();
    });

    jTextFieldPassword.addActionListener(e -> {
      jButtonLogin.doClick();
    });
    jTextFieldUsuario.addActionListener(e -> {
      jButtonLogin.doClick();
    });


    setLayout(new BorderLayout());
    add(formularioLoginAdmin, BorderLayout.CENTER);
  }
}
