package programacion3.clase2;

import programacion3.clase2.entidades.Empleado;
import programacion3.clase2.entidades.Fecha;

public class Main {
  public static void main(String[] args) {
    Fecha f1 = new Fecha();
    f1.setDia(30);
    f1.setMes(11);
    f1.setAnio(2025);
    System.out.println("Fecha: " + f1);

    Empleado nuevo = new Empleado("Juan", new Fecha(20, 4, 2024));
    System.out.println(nuevo);
    Empleado nuevo2 = new Empleado("Pedro", new Fecha(20, 4, 2024));
    System.out.println(nuevo2);
  }
}
