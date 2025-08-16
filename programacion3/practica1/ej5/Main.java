package programacion3.practica1.ej5;

public class Main {
  public static void main(String[] args) {
    CajaAhorro ca = new CajaAhorro("Fulanito Gómez", 100000.00, 1003451);
    System.out.println("Saldo inicial: " + ca.getSaldo());
    ca.depositar(12250.00);
    System.out.println("Nuevo saldo: " + ca.getSaldo());
  }
}
