package programacion3.practica0;

import java.util.Scanner;

public class ej6a {
  public static void main(String[] args) {
    Scanner lector = new Scanner(System.in);
    System.out.println("Ingrese el número: ");
    int num = lector.nextInt();
    if (Integer.toString(num).length() != 4) {
      System.out.println("El número debe ser de exactamente 4 dígitos");
      lector.close();
      System.exit(1);
    }
    
    String[] numeroEncodeado = [];
    int posicion = 0;
    for (int n : Integer.toString(num).toCharArray()) {
      //numeroEncodeado += (n + 7);
      numeroEncodeado[posicion] = Integer.toString(n);
    }
    
    lector.close();
  }
}
