package programacion3.trabajo_practico.src.entidades;

public class CuentaCorriente extends Cuenta {
  // * Atributos
  private double limiteGiro;

  // * Constructores
  public CuentaCorriente(Moneda moneda, String alias, String cbu, double limiteGiro) {
    super(moneda, alias, cbu);
    this.limiteGiro = limiteGiro;
  }

  public CuentaCorriente(Moneda moneda, String alias, String cbu, double limiteGiro, int id, double saldo) {
    super(moneda, alias, cbu, id, saldo);
    this.limiteGiro = limiteGiro;
  }

  // * Métodos
  @Override
  public double extraer(double cantidad) {
    if (this.saldo + this.limiteGiro < cantidad)
      throw new RuntimeException("Saldo insuficiente");

    this.saldo -= cantidad;
    return this.saldo;
  }

  // * Getters & Setters
  public double getLimiteGiro() {
    return this.limiteGiro;
  }

  public void setLimiteGiro(double limiteGiro) {
    this.limiteGiro = limiteGiro;
  }
}
