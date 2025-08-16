package programacion3.practica1.ej7;

public class Main {
  public static void main(String[] args) throws Exception {
    CajaVelocidades c1 = new CajaVelocidades("Peugeot", 5, 'M');
    System.out.println("c1: " + c1);
    CajaVelocidades c2 = new CajaVelocidades("Toyota", 6, 'L');
    System.out.println("c2: " + c2);
    CajaVelocidades c3 = new CajaVelocidades("Kia", 4, 'C');
    System.out.println("c3: " + c3);
  }
}
