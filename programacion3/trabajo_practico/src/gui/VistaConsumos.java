package programacion3.trabajo_practico.src.gui;

import java.util.Map;
import java.util.Vector;
import java.util.List;

import java.time.LocalDate;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceTarjetaCredito;

public class VistaConsumos extends JPanelBase {
  // * Atributos
  TarjetaCredito tarjeta;
  ServiceTarjetaCredito serviceTarjetaCredito;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonReporte;
  JFileChooser jFileChooser;
  JLabel jLabelUsuario;
  JLabel jLabelTarjeta;
  JLabel jLabelPeriodo;
  JTable jTableConsumos;

  // * Constructor
  public VistaConsumos(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    String usuarioString = contexto.get("usuario");
    String nroTarjetaString = contexto.get("nro_tarjeta");
    String nroTarjetaObfuscadoString = "**** " + nroTarjetaString.substring(12);
    String tarjetaIdString = contexto.get("id_tarjeta");
    panel.jFrame.setTitle("Consumos: " + usuarioString + " [" + nroTarjetaObfuscadoString + "]");

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 5));

    jLabelUsuario = new JLabel("Usuario: " + usuarioString);
    jLabelTarjeta = new JLabel("Tarjeta: " + nroTarjetaObfuscadoString);
    jLabelPeriodo = new JLabel("Periodo: " + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());

    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JPanel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelTarjeta);
    jPanelLabels.add(new JPanel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelPeriodo);
    actualPanel.add(jPanelLabels, BorderLayout.NORTH);

    jPanelBotones = new JPanel();
    jPanelBotones.setLayout(new GridLayout(1, 3));

    jButtonVolver = new JButton("Volver");
    jButtonReporte = new JButton("Generar Reporte");

    jPanelBotones.add(jButtonVolver);
    jPanelBotones.add(new JPanel(), BorderLayout.CENTER);
    jPanelBotones.add(jButtonReporte);
    actualPanel.add(jPanelBotones, BorderLayout.CENTER);

    try {
      serviceTarjetaCredito = new ServiceTarjetaCredito(contexto);
      tarjeta = serviceTarjetaCredito.consultarConConsumo(Integer.parseInt(tarjetaIdString));
    } catch (ServiceException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1, 1));

    jTableConsumos = new JTable(construirTablaConsumos(tarjeta.getConsumos()));
    JScrollPane jScrollPane = new JScrollPane(jTableConsumos);
    jPanelTabla.add(jScrollPane);
    actualPanel.add(jPanelTabla, BorderLayout.SOUTH);

    jButtonVolver.addActionListener(e -> {
      int prev = Integer.parseInt(contexto.get("prev"));
      contexto.put("prev", "4");
      panel.mostrar(prev, contexto);
    });

    jButtonReporte.addActionListener(e -> {
      jFileChooser = new JFileChooser();
      jFileChooser.setDialogTitle("Guardar Reporte");
      jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      jFileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
      int respuesta = jFileChooser.showSaveDialog(null);
      if (respuesta == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".csv")) {
          file = new File(file.getAbsolutePath() + ".csv");
        }

        if (file.exists()) {
          int respuesta2 = JOptionPane.showConfirmDialog(null,
              "El archivo ya existe. ¿Desea sobreescribirlo?", "Confirmar", JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE);
          if (respuesta2 != JOptionPane.YES_OPTION) {
            return;
          }

          file.delete();
        }

        try {
          serviceTarjetaCredito = new ServiceTarjetaCredito(contexto);
          for (String linea : serviceTarjetaCredito.generarReporteConsumos(tarjeta)) {
            Files.writeString(file.toPath(), linea, StandardCharsets.UTF_8, StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
          }
        } catch (ServiceException | IOException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    setLayout(new BorderLayout());
    add(actualPanel);
  }

  private DefaultTableModel construirTablaConsumos(List<Consumo> consumos) {
    Vector<String> columnas = new Vector<String>();
    columnas.add("Fecha");
    columnas.add("Referencia");
    columnas.add("Cantidad");
    columnas.add("Moneda");

    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);

    for (Consumo consumo : consumos) {
      resultado.addRow(new Object[] {
          consumo.getFecha(),
          consumo.getReferencia(),
          consumo.getCantidad(),
          consumo.getMoneda().getNombre()
      });
    }

    return resultado;
  }
}
