package programacion3.trabajo_practico.src.gui;

import java.util.Map;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelManager {
  JFrame jFrame;

  public PanelManager(int tipo) {
    jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mostrar(tipo);
  }

  public void mostrar(JPanel panel) {
    jFrame.getContentPane().removeAll();
    jFrame.getContentPane().add(BorderLayout.CENTER, panel);
    jFrame.getContentPane().validate();
    jFrame.getContentPane().repaint();
    jFrame.setVisible(true);
    jFrame.pack();
  }

  public void mostrar(int tipo) {
    mostrar(tipo, Map.of());
  }

  public void mostrar(int tipo, Map<String, String> contexto) {
    switch (tipo) {
      case 1:
        mostrar(new FormularioLoginAdmin(this));
        jFrame.setSize(200, 100);
        break;
      case 2:
        mostrar(new HomeAdmin(this, contexto));
        jFrame.setSize(800, 300);
        break;
      case 3:
        mostrar(new AbmUsuarios(this, contexto));
        jFrame.setSize(800, 300);
        break;
      case 4:
        mostrar(new HomeGestionUsuario(this, contexto));
        jFrame.setSize(800, 300);
        break;
      case 5:
        mostrar(new AbmCuentas(this, contexto));
        jFrame.setSize(800, 300);
        break;
      case 6:
        mostrar(new AbmTarjetas(this, contexto));
        jFrame.setSize(800, 300);
        break;
      default:
        throw new IllegalArgumentException("Tipo de ventana no definido: " + tipo);
    }
  }
}
