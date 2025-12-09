package programacion3.trabajo_practico.src.entidades;

public enum TipoOperacion {
  TRANSFERENCIA("TRF"),
  EXTRACCION("EXT"),
  DEPOSITO("DEP"),
  CREDITO("CRE"),
  DEBITO("DEB");

  private final String codigo;

  TipoOperacion(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public static TipoOperacion fromCodigo(String codigo) {
    for (TipoOperacion tipo : TipoOperacion.values()) {
      if (tipo.getCodigo().equals(codigo)) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("No enum constant for code: " + codigo);
  }
}