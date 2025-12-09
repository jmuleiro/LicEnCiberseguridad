package programacion3.trabajo_practico.src.entidades;

public enum TipoObjeto {
  USUARIO("USR"),
  CUENTA("ACC"),
  CAJA_AHORRO("SAV"),
  CUENTA_CORRIENTE("COR"),
  TARJETA_CREDITO("CAR"),
  TRANSFERENCIA("TRF"),
  CONSUMO("CON"),
  EVENTO("EVT");

  private final String codigo;

  TipoObjeto(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

  public static TipoObjeto fromCodigo(String codigo) {
    for (TipoObjeto tipo : TipoObjeto.values()) {
      if (tipo.getCodigo().equals(codigo)) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("No enum constant for code: " + codigo);
  }
}
