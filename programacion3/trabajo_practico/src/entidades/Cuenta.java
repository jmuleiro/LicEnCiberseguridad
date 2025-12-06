package programacion3.trabajo_practico.src.entidades;

public abstract class Cuenta {
  // * Atributos
  private int id;
  private Moneda moneda;
  private String alias;
  private String cbu;
  protected double saldo;

  // * Constructores
  public Cuenta(Moneda moneda, String alias, String cbu) {
    this.moneda = moneda;
    this.setAlias(alias);
    this.cbu = cbu;
    this.saldo = 0;
  }

  public Cuenta(Moneda moneda, String alias, String cbu, int id, double saldo) {
    this.moneda = moneda;
    this.setAlias(alias);
    this.cbu = cbu;
    this.id = id;
    this.saldo = saldo;
  }

  // * Métodos
  public double depositar(double cantidad) {
    this.saldo += cantidad;
    return this.saldo;
  }

  public abstract double extraer(double cantidad);

  // * Getters & Setters
  public int getId() {
    return this.id;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    if (alias == null)
      this.alias = "";
    else
      this.alias = alias;
  }

  public String getCbu() {
    return this.cbu;
  }

  public double getSaldo() {
    return this.saldo;
  }
}
