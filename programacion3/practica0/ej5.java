package programacion3.practica0;

import java.util.Scanner;

public class ej5 {
  public static String iniciales(String nombreCompleto) {
    String[] nombreArr = nombreCompleto.split(" ");
    String iniciales = "";
    for (String s : nombreArr) {
      iniciales += s.substring(0, 1);
    }
    return iniciales;
  }
  public static void main(String[] args) {
    Scanner lector = new Scanner(System.in);
    System.out.println("Ingrese nombre: ");
    String nombre = lector.nextLine().strip();
    System.out.println("Ingrese apellido: ");
    String apellido = lector.nextLine().strip();
    String nombreCompleto = nombre + " " + apellido;
    System.out.println("Nombre completo: " + nombreCompleto);
    System.out.println("Iniciales: " + iniciales(nombreCompleto));
    lector.close();
  }
}
