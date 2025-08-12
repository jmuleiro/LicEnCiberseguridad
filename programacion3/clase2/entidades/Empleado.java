package programacion3.clase2.entidades;

public class Empleado {
  private String nombre;
  private int legajo;
  private Fecha fechaIngreso;
  private static Integer proxLegajo = 1000;

  public Empleado(String nombre, Fecha fechaIngreso) {
    this.nombre = nombre;
    this.legajo = proxLegajo;
    this.fechaIngreso = fechaIngreso;
    proxLegajo++;
  }

  public String toString() {
    return "Nombre: " + this.nombre + "\n" + "Legajo: " + this.legajo + "\n" + "Fecha: " + this.fechaIngreso;
  }
}
