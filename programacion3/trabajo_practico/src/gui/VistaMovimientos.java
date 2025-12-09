package programacion3.trabajo_practico.src.gui;

import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.service.ServiceCuenta;
import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Transferencia;

public class VistaMovimientos extends JPanelBase {
  // * Atributos
  ServiceCuenta serviceCuenta;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JButton jButtonVolver;
  JButton jButtonReporte;
  JFileChooser jFileChooser;
  JLabel jLabelUsuario;
  JLabel jLabelCuenta;
  JLabel jLabelTipoCuenta;
  JLabel jLabelPeriodo;
  JTable jTableMovimientos;

  // * Constructores
  public VistaMovimientos(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    try {
      this.iniciar();
    } catch (GUIException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  // * Metodos
  @Override
  public void iniciar() throws GUIException {
    String usuarioString = contexto.get("usuario");
    String idCuentaString = contexto.get("id_cuenta");
    String tipoCuentaString = contexto.get("tipo_cuenta");
    panel.jFrame.setTitle("Transferencias: " + usuarioString + " [" + idCuentaString + "]");

    jPanelLabels = new JPanel();
    jPanelLabels.setLayout(new GridLayout(1, 7));

    jLabelUsuario = new JLabel("Usuario: " + usuarioString);
    jLabelCuenta = new JLabel("Cuenta: " + idCuentaString);
    jLabelTipoCuenta = new JLabel("Tipo de Cuenta: " + tipoCuentaString);
    jLabelPeriodo = new JLabel("Periodo: " + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());

    jPanelLabels.add(jLabelUsuario);
    jPanelLabels.add(new JPanel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelCuenta);
    jPanelLabels.add(new JPanel(), BorderLayout.CENTER);
    jPanelLabels.add(jLabelTipoCuenta);
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

    Cuenta cuenta = traerCuenta(idCuentaString);
    if (cuenta == null)
      throw new GUIException("No se encontró la cuenta");

    jPanelTabla = new JPanel();
    jPanelTabla.setLayout(new GridLayout(1, 1));

    jTableMovimientos = new JTable(construirTablaMovimientos(cuenta, cuenta.getTransferencias()));
    JScrollPane jScrollPane = new JScrollPane(jTableMovimientos);
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
          List<String> lineasReporte = new ArrayList<>();
          serviceCuenta = new ServiceCuenta(contexto);
          lineasReporte = serviceCuenta.generarReporteMovimientos(cuenta);
          for (String linea : lineasReporte) {
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

  private DefaultTableModel construirTablaMovimientos(Cuenta cuenta, List<Transferencia> transferencias) {
    Vector<String> columnas = new Vector<String>();
    columnas.add("Fecha");
    columnas.add("Concepto");
    columnas.add("Monto");
    columnas.add("Moneda");
    columnas.add("Origen");
    columnas.add("Destino");

    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);

    for (Transferencia transferencia : transferencias) {
      if (transferencia.getEntrante()) {
        resultado.addRow(new Object[] {
            transferencia.getFecha(),
            transferencia.getConcepto(),
            transferencia.getMonto(),
            transferencia.getMoneda().getNombre(),
            transferencia.getCuentaTercero().getCbu(),
            cuenta.getCbu()
        });
      } else {
        resultado.addRow(new Object[] {
            transferencia.getFecha(),
            transferencia.getConcepto(),
            transferencia.getMonto(),
            transferencia.getMoneda().getNombre(),
            cuenta.getCbu(),
            transferencia.getCuentaTercero().getCbu()
        });
      }
    }

    return resultado;
  }

  private Cuenta traerCuenta(String idCuenta) throws GUIException {
    Cuenta cuenta = null;
    try {
      serviceCuenta = new ServiceCuenta(contexto);
      cuenta = serviceCuenta.consultarConTransferencia(Integer.parseInt(idCuenta));
      return cuenta;
    } catch (ServiceException e) {
      throw new GUIException(e.getMessage());
    }
  }
}
