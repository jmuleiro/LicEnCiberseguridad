package programacion3.practica0;

import java.util.Scanner;

public class ej3 {
  public static double calcularDiscriminante(double a, double b, double c) {
    return Math.pow(b, 2) - 4 * a * c;
  }

  public static double calcularX1(double a, double b, double discr) {
    return (-b + Math.sqrt(discr)) / (2 * a);
  }

  public static double calcularX2(double a, double b, double discr) {
    return (-b - Math.sqrt(discr)) / (2 * a);
  }

  public static void main(String[] args) {
    Scanner lector = new Scanner(System.in);
    System.out.println("Ingrese el valor a: ");
    double a = lector.nextDouble();
    System.out.println("Ingrese el valor b: ");
    double b = lector.nextDouble();
    System.out.println("Ingrese el valor c: ");
    double c = lector.nextDouble();
    double discriminante = calcularDiscriminante(a, b, c);
    System.out.println("Discriminante: " + discriminante);
    if (discriminante < 0) {
      System.out.println("La discriminante es negativa, no se puede calcular la ecuación de segundo grado");
      System.exit(0);
    }
    double resX1 = calcularX1(a, b, discriminante);
    System.out.println("x1: " + resX1);
    double resX2 = calcularX2(a, b, discriminante);
    System.out.println("x2: " + resX2);
    lector.close();
  }
}
