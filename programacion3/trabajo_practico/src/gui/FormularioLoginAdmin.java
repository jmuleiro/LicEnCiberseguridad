package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceUsuarioAdmin;

import java.util.Map;
import java.util.HashMap;

public class FormularioLoginAdmin extends JPanelBase {
  // * Atributos
  ServiceUsuarioAdmin serviceUsuarioAdmin;

  // GUI
  JLabel jLabelUsuario;
  JLabel jLabelPassword;
  JTextField jTextFieldUsuario;
  JPasswordField jPasswordFieldPassword;
  JButton jButtonLogin;
  JButton jButtonSalir;

  // * Constructor
  public FormularioLoginAdmin(PanelManager panel) {
    super(panel);
    iniciar();
  }

  public void iniciar() {
    actualPanel.setLayout(new GridLayout(3, 2));
    panel.jFrame.setTitle("Login");

    jLabelUsuario = new JLabel("Usuario");
    jLabelPassword = new JLabel("Password");

    jTextFieldUsuario = new JTextField();
    jPasswordFieldPassword = new JPasswordField();

    jButtonLogin = new JButton("Login");
    jButtonSalir = new JButton("Salir");

    actualPanel.add(jLabelUsuario);
    actualPanel.add(jTextFieldUsuario);
    actualPanel.add(jLabelPassword);
    actualPanel.add(jPasswordFieldPassword);
    actualPanel.add(jButtonLogin);
    actualPanel.add(jButtonSalir);

    jButtonLogin.addActionListener(e -> {
      try {
        serviceUsuarioAdmin = new ServiceUsuarioAdmin();
        UsuarioAdmin usuarioAdmin = serviceUsuarioAdmin.login(jTextFieldUsuario.getText(),
            new String(jPasswordFieldPassword.getPassword()));

        Map<String, String> contexto = new HashMap<>();
        contexto.put("usuario", usuarioAdmin.getUsuario());
        contexto.put("nombre", usuarioAdmin.getNombre());
        contexto.put("apellido", usuarioAdmin.getApellido());
        panel.mostrar(2, contexto);
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    jButtonSalir.addActionListener(e -> {
      panel.jFrame.dispose();
    });

    jPasswordFieldPassword.addActionListener(e -> {
      jButtonLogin.doClick();
    });

    jTextFieldUsuario.addActionListener(e -> {
      jButtonLogin.doClick();
    });

    setLayout(new BorderLayout());
    add(actualPanel, BorderLayout.CENTER);
  }
}
