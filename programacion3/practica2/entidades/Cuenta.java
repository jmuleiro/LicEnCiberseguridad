package programacion3.practica2.entidades;

import programacion3.practica2.entidades.exceptions.SaldoException;

public abstract class Cuenta {
  //* Atributos
  private String titular;
  private int idCuenta;
  private static int ultimoIdCuenta = 1000;
  private double saldo = 0.00;

  //* Constructor
  public Cuenta(String titular) {
    this.titular = titular;
    this.idCuenta = ultimoIdCuenta;
    incrementarIdCuenta();
  }

  //* Métodos
  private static void incrementarIdCuenta() {
    ultimoIdCuenta++;
  }

  public void depositar(double cantidad) {
    this.saldo += cantidad;
  }

  public abstract void extraer(double cantidad) throws SaldoException;

  public String toString() {
    return "Titular: " + this.getTitular() + ", saldo: " + this.getSaldo() + ", ID cuenta: " + this.getIdCuenta();
  }

  // Getters & Setters
  public String getTitular() {
    return titular;
  }

  public int getIdCuenta() {
    return idCuenta;
  }

  public double getSaldo () {
    return saldo;
  }

  protected void setSaldo(double cantidad) {
    this.saldo = cantidad; 
  }
}
