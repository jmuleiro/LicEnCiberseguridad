package programacion3.trabajo_practico.src.gui;

import javax.swing.JPanel;
import java.util.HashMap;
import java.util.Map;

public abstract class JPanelBase extends JPanel {
  //* Contexto
  protected JPanel actualPanel;
  protected PanelManager panel;
  protected Map<String, String> contexto;

  //* Constructores
  public JPanelBase(PanelManager panel) {
    super();
    this.panel = panel;
    this.actualPanel = new JPanel();
    this.contexto = new HashMap<>();
  }

  public JPanelBase(PanelManager panel, Map<String, String> contexto) {
    super();
    this.panel = panel;
    this.actualPanel = new JPanel();
    this.contexto = contexto;
  }

  public abstract void iniciar();
}
