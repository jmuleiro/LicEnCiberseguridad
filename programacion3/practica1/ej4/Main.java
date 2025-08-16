package programacion3.practica1.ej4;

import programacion3.practica1.ej2.Empleado;

public class Main {
  public static void main(String[] args) {
    Empleado nuevo = new Empleado("José", "Gómez");
    System.out.println("Empleado 1 - Nombre: " + nuevo.getNombre() + " " + nuevo.getApellido() + ", legajo: " + nuevo.getLegajo());

    Empleado nuevo2 = new Empleado("Gabriel", "González");
    System.out.println("Empleado 2 - Nombre: " + nuevo2.getNombre() + " " + nuevo2.getApellido() + ", legajo: " + nuevo2.getLegajo());
  }
}
