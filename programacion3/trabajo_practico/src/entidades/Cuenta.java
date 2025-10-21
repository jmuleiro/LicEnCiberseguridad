package programacion3.trabajo_practico.src.entidades;

public class Cuenta {
  //* Atributos
  private int id;
  private Moneda moneda;
  private String alias;
  private int cbu;
  private double saldo;

  //* Constructores
  public Cuenta(Moneda moneda, String alias, int cbu) {
    this.moneda = moneda;
    this.alias = alias;
    this.cbu = cbu;
    this.saldo = 0;
  }

  public Cuenta(Moneda moneda, String alias, int cbu, int id, double saldo) {
    this.moneda = moneda;
    this.alias = alias;
    this.cbu = cbu;
    this.id = id;
    this.saldo = saldo;
  }

  //* Métodos
  public double depositar(double cantidad) {
    this.saldo += cantidad;
    return this.saldo;
  }

  public double extraer(double cantidad) {
    if (this.saldo < cantidad)
      throw new RuntimeException("Saldo insuficiente");
    
    this.saldo -= cantidad;
    return this.saldo;
  }

  // TODO: public void transferir(Transferencia t) {}

  //* Getters & Setters
  public int getId(){
    return this.id;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public int getCbu() {
    return this.cbu;
  }

  public double getSaldo() {
    return this.saldo;
  }
}
