package programacion3.trabajo_practico.src.gui;

import java.util.Map;

public class AbmTarjetas extends JPanelBase {
  // * Atributos

  // * Constructor
  public AbmTarjetas(PanelManager panel, Map<String, String> contexto) {
    super(panel, contexto);
    iniciar();
  }

  @Override
  public void iniciar() {
    String usuarioString = contexto.get("usuario");
    Integer idUsuario = Integer.valueOf(contexto.get("id_usuario"));
    panel.jFrame.setTitle("Tarjetas: " + usuarioString);
  }
}
