package programacion3.trabajo_practico.src.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import programacion3.trabajo_practico.src.service.ServiceCajaAhorro;
import programacion3.trabajo_practico.src.service.ServiceCuentaCorriente;

public class HomeGestionCuenta extends JPanelBase {
  //* Atributos
  ServiceCajaAhorro serviceCajaAhorro;
  ServiceCuentaCorriente serviceCuentaCorriente;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JLabel jLabelUsuario;
  JLabel jLabelIdCuenta;
  JLabel jLabelTipoCuenta;
  JButton jButtonExtraer;
  JButton jButtonDepositar;
  JButton jButtonVolver;
  
  //* Constructor
  public HomeGestionCuenta(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 3));

    String tipoCuenta = contexto.get("tipo_cuenta");
    Integer idCuenta = Integer.valueOf(contexto.get("id_cuenta"));

    jLabelUsuario = new JLabel("Usuario: " + contexto.get("id_usuario"));
    jLabelIdCuenta = new JLabel("ID Cuenta: " + idCuenta);
    jLabelTipoCuenta = new JLabel("Tipo Cuenta: " + tipoCuenta);

    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(jLabelIdCuenta);
    jPanelLabels.add(jLabelTipoCuenta);
    actualPanel.add(jPanelLabels);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(4, 1));

    jButtonExtraer = new JButton("Extraer");
    jButtonDepositar = new JButton("Depositar");
    jButtonVolver = new JButton("Volver");

    jPanelBotones.add(jButtonExtraer);
    jPanelBotones.add(jButtonDepositar);
    jPanelBotones.add(jButtonVolver);
    actualPanel.add(jPanelBotones);

    jButtonExtraer.addActionListener(e -> {

    });

    jButtonDepositar.addActionListener(e -> {

    });

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "4");
      panel.mostrar(prev, contexto);
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }
}
