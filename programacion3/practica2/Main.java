package programacion3.practica2;

import programacion3.practica2.entidades.Banco;
import programacion3.practica2.entidades.CajaAhorro;
import programacion3.practica2.entidades.CuentaCorriente;
import programacion3.practica2.entidades.exceptions.SaldoException;

public class Main {
  private static int contador = 1;
  public static void main(String[] args) {
    CajaAhorro cajaAhorro = new CajaAhorro("José Gómez", 5.00);
    CuentaCorriente cuentaCorriente = new CuentaCorriente("Gonzalo González", 10000.00);
    CuentaCorriente cuentaCorriente2 = new CuentaCorriente("Juan Martínez", 10000.00);
    
    Banco banco = new Banco("Banco Central");
    banco.agregarCuenta(cajaAhorro);
    banco.agregarCuenta(cuentaCorriente);
    banco.agregarCuenta(cuentaCorriente2);

    compararCc(cuentaCorriente, cuentaCorriente2);
    logearEstado(cajaAhorro, cuentaCorriente, cuentaCorriente2, banco);
    
    cajaAhorro.depositar(10251.53);
    cuentaCorriente.depositar(128972.91);

    logearEstado(cajaAhorro, cuentaCorriente, cuentaCorriente2, banco);

    try {
      cajaAhorro.extraer(11000.00);
    } catch (SaldoException e) {
      System.err.println(e);
      System.out.println("--------------------");
    }

    try {
      cuentaCorriente.extraer(131000.00);
    } catch (SaldoException e) {
      System.err.println(e);
      System.out.println("--------------------");
    }

    logearEstado(cajaAhorro, cuentaCorriente, cuentaCorriente2, banco);

    compararCc(cuentaCorriente, cuentaCorriente2);
  }

  public static void compararCc(CuentaCorriente c1, CuentaCorriente c2) {
    int resultadoComp = c1.compareTo(c2);

    if (resultadoComp == 0)
      System.out.println("Ambas cuentas tienen el mismo saldo");
    else if (resultadoComp > 0)
      System.out.println("Cuenta 1 tiene más saldo");
    else
      System.out.println("Cuenta 2 tiene más saldo");
    System.out.println("--------------------");
  }
  
  public static void logearEstado(CajaAhorro ca, CuentaCorriente cc, CuentaCorriente cc2, Banco b) {
    System.out.println(contador + ". Caja Ahorro: " + ca);
    System.out.println(contador + ". Cuenta Corriente 1: " + cc);
    System.out.println(contador + ". Cuenta Corriente 2: " + cc2);
    System.out.println(contador + ". " + b + ", saldo positivo: " + b.saldoPositivo() + ", saldo negativo: " + b.saldoNegativo());
    System.out.println("--------------------");
    contador++;
  }
}
