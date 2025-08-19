package programacion3.clase3;

import programacion3.clase2.entidades.Empleado;
import programacion3.clase2.entidades.Fecha;
import programacion3.clase3.entidades.EmpleadoEfectivo;
import programacion3.clase3.entidades.EmpleadoContratado;

public class Main {
  public static void main(String[] args) {
    Fecha fechaIngreso = new Fecha(21, 12, 2024);
    EmpleadoEfectivo nuevo = new EmpleadoEfectivo("José Pérez", fechaIngreso, 650000.00, 21.00);
    System.out.println(nuevo);

    EmpleadoContratado nuevo2 = new EmpleadoContratado("Juan Gómez", fechaIngreso, 1350.00);
    System.out.println(nuevo2);
    
    Empleado[] empleados = new Empleado[2];
    empleados[0] = new EmpleadoEfectivo("Juan González", fechaIngreso, 750000.00, 18);
    empleados[1] = new EmpleadoContratado("Raquel Fernández", fechaIngreso, 1150.00, 160.00);
    double total = 0;
    for (int i = 0 ; i < empleados.length; i++) {
      total += empleados[i].calcularSueldo();
    }
    System.out.println("Total: " + total);
  }
}
