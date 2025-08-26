package programacion3.clase4;

public class Main {
  public static void main(String[] args) {
    int n1 = 100;
    int n2 = 0;
    int r = 0;
    try {
      r = n1 / n2;
    } catch (ArithmeticException e) {
      System.out.println("Error: " + e.getMessage());
    }
    System.out.println("Resultado: " + r);
  }
}
