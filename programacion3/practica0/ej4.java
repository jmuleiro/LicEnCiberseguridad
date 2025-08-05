package programacion3.practica0;

public class ej4 {
  public static int suma3(int a, int b, int c) {
    return a + b + c;
  }

  public static long producto(int a, int b) {
    return (long) a * b;
  }

  public static long ecuacion(int a, int b) {
    return producto(((int) producto(a, b) + (int) producto(b, 5) + 2), suma3(a, b, 1));
  }

  public static void main(String[] args) {
    System.out.println("Resultado: " + ecuacion(2,3));
    System.out.println("Resultado2: " + ecuacion(10,38));
  }
}
