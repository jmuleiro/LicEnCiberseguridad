package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceUsuarioAdmin;

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

  //* Constructor
  public FormularioLoginAdmin(PanelManager panel) {
    this.panel = panel;
    iniciar();
  }

  public void iniciar() {
    formularioLoginAdmin = new JPanel();
    jLabelUsuario = new JLabel("Usuario");
    jLabelPassword = new JLabel("Password");
    jTextFieldUsuario = new JTextField();
    jTextFieldPassword = new JTextField();
    jButtonLogin = new JButton("Login");
    formularioLoginAdmin.setLayout(new GridLayout(3, 2));
    formularioLoginAdmin.add(jLabelUsuario);
    formularioLoginAdmin.add(jTextFieldUsuario);
    formularioLoginAdmin.add(jLabelPassword);
    formularioLoginAdmin.add(jTextFieldPassword);
    formularioLoginAdmin.add(jButtonLogin);

    jButtonLogin.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          serviceUsuarioAdmin = new ServiceUsuarioAdmin();
          //todo: Aca tendria que consultar por "username", 
          //todo: agregar al esquema, clase, DAO, y Service
          UsuarioAdmin usuarioAdmin = serviceUsuarioAdmin.consultar(Integer.valueOf(jTextFieldUsuario.getText()));
          if (usuarioAdmin == null)
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        } catch (ServiceException exc) {
          JOptionPane.showMessageDialog(null, exc);
        }
      }
    });

    setLayout(new BorderLayout());
    add(formularioLoginAdmin, BorderLayout.CENTER);
  }
}
