package programacion3.practica1.ej1;

public class Main {
  public static void main(String[] args) {
    Hora h1 = new Hora();
    System.out.println("h1 actual: " + h1);
    h1.incrementarMinutos(30);
    System.out.println("h1 incrementada: " + h1);

    Hora h2 = new Hora(14, 55, 38);
    System.out.println("h2 actual: " + h2);
    h2.incrementarMinutos(30);
    System.out.println("h2 incrementada: " + h2);
  }
}
