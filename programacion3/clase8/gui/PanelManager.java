package programacion3.clase8.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelManager {
  // Definir todos los paneles aca, cada panel es una clase
  JFrame jFrame;

  public PanelManager(int tipo) {
    jFrame = new JFrame();
    // Cerrar todas las ventanas al clickear en el boton de cerrar
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void mostrar(JPanel panel) {
    jFrame.getContentPane().removeAll();
    jFrame.getContentPane().add(BorderLayout.SOUTH, panel);
    jFrame.getContentPane().validate();
    jFrame.getContentPane().repaint();
    // jFrame.show() esta deprecado
    jFrame.setVisible(true);
    jFrame.pack();
  }

  public void mostrar(int codigoPantalla) {
    switch (codigoPantalla) {
      case 1:
      case 2:
    }
  }
}
