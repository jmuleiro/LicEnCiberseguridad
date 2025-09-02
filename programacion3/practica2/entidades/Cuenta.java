package programacion3.practica2.entidades;

import java.util.Objects;

import programacion3.practica2.entidades.exceptions.SaldoException;

public abstract class Cuenta implements Comparable<Cuenta> {
  //* Atributos
  private String titular;
  private int idCuenta;
  private static int ultimoIdCuenta = 1000;
  private Double saldo = 0.00;

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

  public Double getSaldo () {
    return saldo;
  }

  protected void setSaldo(double cantidad) {
    this.saldo = cantidad; 
  }

  @Override
  public int compareTo(Cuenta o) {
    return this.saldo.compareTo(o.getSaldo());
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCuenta, titular);
  }
}
