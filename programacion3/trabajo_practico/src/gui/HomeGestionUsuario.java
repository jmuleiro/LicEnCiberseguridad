package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeGestionUsuario extends JPanelBase {
  //* Atributos
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JLabel jLabelNombre;
  JLabel jLabelApellido;
  JLabel jLabelUsuario;
  JLabel jLabelId;
  JButton jButtonCuentas;
  JButton jButtonTarjetas;
  JButton jButtonVolver;
  
  //* Constructor
  public HomeGestionUsuario(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(2, 2));
    jLabelNombre = new JLabel("Nombre: " + contexto.get("nombre_usuario"));
    jLabelApellido = new JLabel("Apellido: " + contexto.get("apellido_usuario"));
    jLabelUsuario = new JLabel("Usuario: " + contexto.get("usuario"));
    jLabelId = new JLabel("ID: " + contexto.get("id_usuario"));
    jPanelLabels.add(jLabelNombre);
    jPanelLabels.add(jLabelApellido);
    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(jLabelId);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(3, 1));
    jButtonCuentas = new JButton("Cuentas");
    jButtonTarjetas = new JButton("Tarjetas");
    jButtonVolver = new JButton("Volver");
    jPanelBotones.add(jButtonCuentas);
    jPanelBotones.add(jButtonTarjetas);
    jPanelBotones.add(jButtonVolver);
    actualPanel.add(jPanelBotones, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "2");
      panel.mostrar(prev, contexto);
    });

    jButtonCuentas.addActionListener(e -> {
      contexto.put("prev", "4");
      panel.mostrar(5, contexto);
    });

    jButtonTarjetas.addActionListener(e -> {
      contexto.put("prev", "4");
      panel.mostrar(6, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }
}
