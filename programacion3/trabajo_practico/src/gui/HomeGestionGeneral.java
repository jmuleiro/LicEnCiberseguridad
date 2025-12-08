package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import programacion3.trabajo_practico.src.service.ServiceCajaAhorro;
import programacion3.trabajo_practico.src.service.ServiceException;

public class HomeGestionGeneral extends JPanelBase {
  // * Atributos
  ServiceCajaAhorro serviceCajaAhorro;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JLabel jLabelUsuario;
  JButton jButtonIntereses;
  JButton jButtonAuditoria;
  JButton jButtonVolver;

  // * Constructor
  public HomeGestionGeneral(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    panel.jFrame.setTitle("Gestión General");

    jLabelUsuario = new JLabel("Usuario: " + contexto.get("usuario"));

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 3));

    jPanelLabels.add(new JLabel());
    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JLabel());

    actualPanel.add(jPanelLabels, BorderLayout.CENTER);

    jButtonIntereses = new JButton("Aplicar Intereses");
    jButtonAuditoria = new JButton("Auditoría");
    jButtonVolver = new JButton("Volver");

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 5));

    jPanelBotones.add(jButtonIntereses);
    jPanelBotones.add(new JLabel());
    jPanelBotones.add(jButtonAuditoria);
    jPanelBotones.add(new JLabel());
    jPanelBotones.add(jButtonVolver);

    actualPanel.add(jPanelBotones, BorderLayout.SOUTH);

    jButtonIntereses.addActionListener(e -> {
      try {
        serviceCajaAhorro = new ServiceCajaAhorro(contexto);
        serviceCajaAhorro.aplicarIntereses();
        JOptionPane.showMessageDialog(null, "Intereses aplicados");
      } catch (ServiceException exc) {
        JOptionPane.showMessageDialog(null, exc, "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    jButtonAuditoria.addActionListener(e -> {

    });

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "1");
      panel.mostrar(prev, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel, BorderLayout.CENTER);
  }
}
