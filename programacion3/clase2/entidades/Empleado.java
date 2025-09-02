package programacion3.clase2.entidades;

import java.util.Objects;

public class Empleado implements Comparable<Empleado> {
  private String nombre;
  private Integer legajo;
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

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    
    if (!(obj instanceof Empleado))
      return false;
    
    return this.legajo == ((Empleado) obj).legajo;
  }
  
  public double antiguedad(Fecha comp) {
    return comp.restarAnio(fechaIngreso);
  }

  public double calcularSueldo() {
    return 0.00;
  }

  @Override
  public int compareTo(Empleado o) {
    return this.legajo.compareTo(o.legajo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre, legajo, fechaIngreso);
  }
}
