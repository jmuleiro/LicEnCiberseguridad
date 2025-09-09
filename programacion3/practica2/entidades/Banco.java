package programacion3.practica2.entidades;

import java.util.ArrayList;
import java.util.List;

public class Banco {
  //* Atributos
  String nombre;
  private List<Cuenta> cuentas;

  //* Constructor(es)
  public Banco(String nombre) {
    this.nombre = nombre;
    this.cuentas = new ArrayList<>();
  }

  //* Métodos
  public void agregarCuenta(Cuenta c) {
    this.cuentas.add(c);
  }

  public void eliminarCuenta(Cuenta c) {
    this.cuentas.remove(c);
  }

  public Cuenta eliminarCuenta(int index) {
    return this.cuentas.remove(index);
  }

  @Override
  public String toString() {
    return "Banco {" +
    "nombre: " + this.nombre + ", " +
    "cant. cuentas: " + this.cuentas.size() +
    "}";
  }

  public double saldoPositivo() {
    double total = 0;
    for (Cuenta aux: this.cuentas) {
      double saldo = aux.getSaldo();
      if (saldo >= 0)
        total += saldo;
    }
    return total;
  }

  public double saldoNegativo() {
    double total = 0;
    for (Cuenta aux: this.cuentas) {
      double saldo = aux.getSaldo();
      if (saldo < 0)
        total += saldo;
    }
    return total;
  }
}
