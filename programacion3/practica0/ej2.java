package programacion3.practica0;

import java.util.Scanner;

public class ej2 {
  public static void main(String[] args) {
    Scanner lector = new Scanner(System.in);
    System.out.println("Ingrese un año: ");
    int año = lector.nextInt();
    String textoFinal = "";
    if (año % 4 == 0 && !( año % 100 == 0 || año % 400 == 0)) {
      textoFinal = " es bisiesto.";
    } else {
      textoFinal = " no es bisiesto.";
    }
    System.out.println("El año " + año + textoFinal);
    lector.close();
  }
}
