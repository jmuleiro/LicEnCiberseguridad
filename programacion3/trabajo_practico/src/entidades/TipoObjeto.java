package programacion3.trabajo_practico.src.entidades;

public enum TipoObjeto {
  USUARIO("USR"),
  CUENTA("ACC"),
  CAJA_AHORRO("SAV"),
  CUENTA_CORRIENTE("COR"),
  TARJETA_CREDITO("CAR"),
  TRANSFERENCIA("TRF"),
  CONSUMO("CON");

  private final String codigo;

  TipoObjeto(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }
}
