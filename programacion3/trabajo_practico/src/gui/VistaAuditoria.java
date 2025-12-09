package programacion3.trabajo_practico.src.gui;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.time.LocalDate;

import programacion3.trabajo_practico.src.service.ServiceEvento;
import programacion3.trabajo_practico.src.service.ServiceException;
import programacion3.trabajo_practico.src.entidades.Evento;

public class VistaAuditoria extends JPanelBase {
  // * Atributos
  private ServiceEvento serviceEvento;
  JPanel jPanelLabels;
  JPanel jPanelBotones;
  JPanel jPanelTabla;
  JTable jTableEventos;
  JButton jButtonVolver;
  JButton jButtonReporte;
  JFileChooser jFileChooser;
  JLabel jLabelUsuario;
  JLabel jLabelPeriodo;

  // * Constructores
  public VistaAuditoria(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    try {
      iniciar();
    } catch (GUIException e) {
      System.out.println(e);
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // * Métodos
  @Override
  public void iniciar() throws GUIException {
    try {
      String usuarioString = contexto.get("usuario");
      panel.jFrame.setTitle("Auditoría");

      jPanelLabels = new JPanel();
      jPanelLabels.setLayout(new GridLayout(1, 3));

      jLabelUsuario = new JLabel("Usuario: " + usuarioString);
      jLabelPeriodo = new JLabel("Periodo: " + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());

      jPanelLabels.add(jLabelUsuario);
      jPanelLabels.add(new JLabel());
      jPanelLabels.add(jLabelPeriodo);

      actualPanel.add(jPanelLabels, BorderLayout.NORTH);

      jPanelBotones = new JPanel();
      jPanelBotones.setLayout(new GridLayout(1, 3));

      jButtonVolver = new JButton("Volver");
      jButtonReporte = new JButton("Generar Reporte");

      jPanelBotones.add(jButtonVolver);
      jPanelBotones.add(new JLabel());
      jPanelBotones.add(jButtonReporte);

      actualPanel.add(jPanelBotones, BorderLayout.CENTER);

      List<Evento> eventos = new ArrayList<>();
      serviceEvento = new ServiceEvento(contexto);
      eventos = serviceEvento.consultarTodos();

      jPanelTabla = new JPanel();
      jPanelTabla.setLayout(new GridLayout(1, 1));

      jTableEventos = new JTable(construirTablaEventos(eventos));
      JScrollPane jScrollPane = new JScrollPane(jTableEventos);
      jPanelTabla.add(jScrollPane);
      actualPanel.add(jPanelTabla, BorderLayout.SOUTH);

      jButtonVolver.addActionListener(e -> {
        int prev = Integer.parseInt(contexto.get("prev"));
        contexto.put("prev", "2");
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
            serviceEvento = new ServiceEvento(contexto);
            for (String linea : serviceEvento.generarReporte()) {
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
    } catch (ServiceException e) {
      throw new GUIException(e.getMessage());
    }
  }

  private DefaultTableModel construirTablaEventos(List<Evento> eventos) {
    Vector<String> columnas = new Vector<String>();
    columnas.add("ID");
    columnas.add("Fecha/Hora");
    columnas.add("Evento");
    columnas.add("Objeto");
    columnas.add("ID Objeto");

    DefaultTableModel resultado = new DefaultTableModel(0, columnas.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    resultado.setColumnIdentifiers(columnas);

    for (Evento evento : eventos) {
      resultado.addRow(new Object[] {
          evento.getId(),
          evento.getFechaHora(),
          evento.getTipo().toString(),
          evento.getObjeto().toString(),
          evento.getIdObjeto()
      });
    }

    return resultado;
  }
}
