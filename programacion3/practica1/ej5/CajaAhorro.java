package programacion3.practica1.ej5;

public class CajaAhorro {
  //* Atributos
  private int nroCuenta;
  private String titular;
  private double saldo;

  //* Constructor
  public CajaAhorro(String nombreTitular, double saldoInicial, int nroCuenta) {
    this.titular = nombreTitular.strip();
    this.saldo = saldoInicial;
    this.nroCuenta = nroCuenta;
  }

  //* Getters & Setters
  public int getNroCuenta() {
    return this.nroCuenta;
  }

  public String getTitular() {
    return this.titular;
  }

  public double getSaldo() {
    return this.saldo;
  }

  //* Métodos
  public double depositar(double saldoDepositado) {
    this.saldo += saldoDepositado;
    return this.saldo;
  }

  public double extraer(double saldoExtraido) throws Exception {
    if (saldoExtraido > this.saldo)
      throw new Exception("El saldo a extaer supera el saldo de la cuenta");
    this.saldo -= saldoExtraido;
    return this.saldo;
  }
}
