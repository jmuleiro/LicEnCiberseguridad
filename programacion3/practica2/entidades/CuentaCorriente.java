package programacion3.practica2.entidades;

import programacion3.practica2.entidades.exceptions.SaldoException;

public class CuentaCorriente extends Cuenta {
  //* Atributos
  private double limiteGiro = 1000.00;

  //* Constructores
  public CuentaCorriente(String titular) {
    super(titular);
  }

  public CuentaCorriente(String titular, double limiteGiro) {
    super(titular);
    this.limiteGiro = limiteGiro;
  }

  //* Métodos
  public void extraer(double cantidad) throws SaldoException {
    double _saldoMasLimite = this.getSaldo() + this.getLimiteGiro();
    if (_saldoMasLimite - cantidad <= 0.00)
      throw new SaldoException("La cantidad a extraer excede el límite de giro en descubierto de la Cuenta Corriente. Saldo: " + this.getSaldo());
    this.setSaldo(this.getSaldo() - cantidad);
  }

  // Getters & Setters
  public double getLimiteGiro() {
    return this.limiteGiro;
  }
}
