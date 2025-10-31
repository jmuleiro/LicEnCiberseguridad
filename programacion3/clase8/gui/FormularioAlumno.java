package programacion3.clase8.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import programacion3.clase8.entidades.Alumno;
import programacion3.clase8.service.ServiceAlumno;

public class FormularioAlumno extends JPanel {
  // El JPanel interactua con el Service (intermediario), mientras que el Service interactua con el DAO ("la base de datos")
  ServiceAlumno serviceAlumno;

  // esta instancia de PanelManager tiene que ser la misma que la del PanelManager que llama a esta clase
  // ej: FormularioAlumno formularioAlumno = new FormularioAlumno(this);
  PanelManager panel;

  // Se necesita un atributo de este tipo para poder interactuar con el
  // Agregando lo que se quiere mostrar
  JPanel formularioAlumno;
  
  // Componentes visuales
  JLabel jLabelNombre;
  JLabel jLabelApellido;
  JButton jButtonReporte;
  JButton jButtonGuardar;

  public FormularioAlumno(PanelManager panel) {
    this.panel = panel;
    armarFormulario();
  }

  public void armarFormulario() {
    serviceAlumno = new ServiceAlumno();
    formularioAlumno = new JPanel();
    jButtonGuardar = new JButton("Guardar");
    jButtonReporte = new JButton("Reporte");
    formularioAlumno.setLayout(new GridLayout(2, 3));
    formularioAlumno.add(jButtonGuardar);
    formularioAlumno.add(jButtonReporte);
    formularioAlumno.add(jLabelNombre);
    formularioAlumno.add(jLabelApellido);

    jButtonGuardar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Alumno alumno = new Alumno();
        alumno.setNombre(jLabelNombre.getText());
        try {
          serviceAlumno.guardar(alumno);
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    });
  }
}
