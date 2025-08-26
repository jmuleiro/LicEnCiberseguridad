package programacion3.practica2;

import programacion3.practica2.entidades.CajaAhorro;
import programacion3.practica2.entidades.CuentaCorriente;
import programacion3.practica2.entidades.exceptions.SaldoException;

public class Main {
  private static int contador = 1;
  public static void main(String[] args) {
    CajaAhorro cajaAhorro = new CajaAhorro("José Gómez", 5.00);
    CuentaCorriente cuentaCorriente = new CuentaCorriente("Gonzalo González", 10000.00);
    
    logearEstado(cajaAhorro, cuentaCorriente);
    
    cajaAhorro.depositar(10251.53);
    cuentaCorriente.depositar(128972.91);

    logearEstado(cajaAhorro, cuentaCorriente);

    try {
      cajaAhorro.extraer(11000.00);
    } catch (SaldoException e) {
      System.err.println(e);
    }

    try {
      cuentaCorriente.extraer(131000.00);
    } catch (SaldoException e) {
      System.err.println(e);
    }

    logearEstado(cajaAhorro, cuentaCorriente);
  }

  public static void logearEstado(CajaAhorro ca, CuentaCorriente cc) {
    System.out.println(contador + ". Caja Ahorro: " + ca);
    System.out.println(contador + ". Cuenta Corriente: " + cc);
    System.out.println("--------------------");
    contador++;
  }
}
