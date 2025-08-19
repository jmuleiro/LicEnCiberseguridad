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
}
