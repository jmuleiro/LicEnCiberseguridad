package programacion3.trabajo_practico.src.entidades;

public enum TipoEvento {
  LOGIN("LGI"),
  LOGOUT("LGO"),
  CREACION("CRE"),
  MODIFICACION("MOD"),
  ELIMINACION("DEL"),
  CONSULTA("QRY"),
  CREDITO("CRD"),
  DEBITO("DBT"),
  EXTRACCION("EXT"),
  DEPOSITO("DEP"),
  REPORTE("REP");

  private final String codigo;

  TipoEvento(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

  public static TipoEvento fromCodigo(String codigo) {
    for (TipoEvento tipo : TipoEvento.values()) {
      if (tipo.getCodigo().equals(codigo)) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("No enum constant for code: " + codigo);
  }
}
