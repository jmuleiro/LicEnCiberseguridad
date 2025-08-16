package programacion3.practica1.ej2;

public class Empleado {
  private String nombre;
  private String apellido;
  private int legajo;
  private static Integer proxLegajo = 1000;

  //* Constructor
  public Empleado(String nombre, String apellido) {
    this.nombre = nombre.strip();
    this.apellido = apellido.strip();
    this.legajo = proxLegajo;
    proxLegajo++;
  }

  // Método toString
  public String toString() {
    return "Nombre: " + this.nombre + " " + this.apellido + ", legajo: " + this.legajo;
  }

  //* Getters & Setters
  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public int getLegajo() {
    return this.legajo;
  }
}
