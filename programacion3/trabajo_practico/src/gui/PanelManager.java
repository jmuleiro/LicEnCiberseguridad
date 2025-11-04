package programacion3.trabajo_practico.src.gui;

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
    jFrame.setSize(200, 100);
  }

  public void mostrar(int tipo) {
    switch (tipo) {
      case 1:
        mostrar(new FormularioLoginAdmin(this));
        break;
      case 2:
        break;
      default:
        throw new IllegalArgumentException("Tipo de ventana no definido: " + tipo);
    }
  }
}
