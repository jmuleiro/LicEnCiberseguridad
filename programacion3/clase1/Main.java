package programacion3.clase1;

import java.util.Scanner;

public class Main {
  public static int suma(int a, int b) {
    return a + b;
  }

  public static void main(String[] args) {
    int num = 100;
    int num1 = 3;
    double result;
    System.out.println("Valor: " + num);
    System.out.println(num + num1);
    result = (double) num / num1;
    System.out.println("Resultado: " + result);
    System.out.println("Resultado suma: " + suma(num, num1));
    
    Scanner lector = new Scanner(System.in);
    System.out.print("Ingrese un numero: ");
    int num2 = lector.nextInt();
    System.out.println("Ingrese otro numero: ");
    int num3 = lector.nextInt();
    System.out.println("Resultado suma: " + suma(num2, num3));
    lector.close();
  }
}
