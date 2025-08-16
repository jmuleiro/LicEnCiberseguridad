package programacion3.practica1.ej6;

public class Main {
  public static void main(String[] args) {
    Rueda r1 = new Rueda(5.0, "negro", "acero");
    Rueda r2 = new Rueda(10.5, "negro", "acero");
    System.out.println("r1 inicial: " + r1);
    System.out.println("r2 inicial: " + r2);
    r1.girar();
    r1.girar();
    r1.girar();
    r2.girar();
    r2.girar();
    System.out.println("r1 final: " + r1);
    System.out.println("r2 final: " + r2);
  }
}
