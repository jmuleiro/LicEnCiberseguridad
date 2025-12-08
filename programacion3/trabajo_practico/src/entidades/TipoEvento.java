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
}
