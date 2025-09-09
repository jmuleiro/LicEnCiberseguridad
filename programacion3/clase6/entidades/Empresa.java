package programacion3.clase6.entidades;

import java.util.ArrayList;
import java.util.List;

import programacion3.clase2.entidades.Empleado;

public class Empresa {
  //* Atributos
  private List<Empleado> empleados;
  public String nombre;
  
  //* Constructor(es)
  public Empresa(String nombre) {
    this.nombre = nombre;
    empleados = new ArrayList<>();
  }

  //* Métodos
  public void agregarEmpleado(Empleado empleado) {
    empleados.add(empleado);
  }

  public void despedirEmpleado(Empleado empleado) {
    empleados.remove(empleado);
  }

  public void pagarSueldos() {
    String recibos= "";
    for (Empleado emp:empleados) {
      recibos += emp + "\n";
    }
    System.out.println(recibos);
  }

  public double totalSueldos() {
    double total = 0;
    for (Empleado emp:empleados) {
      total += emp.calcularSueldo();
    }
    return total;
  }

  @Override
  public String toString() {
    return "Empresa {" +
    "nombre=" + this.nombre +
    "}";
  }
}
