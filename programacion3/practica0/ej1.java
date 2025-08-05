package programacion3.practica0;

import java.util.Scanner;

public class ej1 {
  public static void validarUnidad(String unidad) {
    if (!unidad.matches("fahrenheit|celsius|f|c")) {
      System.out.println("Valores validos: Fahrenheit, Celsius");
      System.exit(1);
    }
  }
  public static void convertirFahrenheit(float celsius) {
    float resultado = (float) ((celsius * 9 / 5) + 32);
    System.out.println(celsius + " grados Celsius son " + resultado + " grados Fahrenheit");
  }

  public static void convertirCelsius(float fahrenheit) {
    float resultado = (float) ((fahrenheit - 32) * 5 / 9);
    System.out.println(fahrenheit + " grados Fahrenheit son " + resultado + "grados Celsius");
  }

  public static void main(String[] args) {
    System.out.println("Ingrese la medida a la que convertir: ");
    Scanner lector = new Scanner(System.in);
    String unidad = lector.nextLine().toLowerCase();
    validarUnidad(unidad);

    if (unidad.matches("fahrenheit|f")) {
      System.out.println("Ingrese la medida en Celsius: ");
      float medida = lector.nextFloat();
      convertirFahrenheit(medida);
    }

    if (unidad.matches("celsius|c")) {
      System.out.println("Ingrese la medida en Fahrenheit: ");
      float medida = lector.nextFloat();
      convertirCelsius(medida);
    }
    lector.close();
  }
}