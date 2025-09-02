package programacion3.clase3.entidades;
import programacion3.clase2.entidades.Empleado;
import programacion3.clase2.entidades.Fecha;
import programacion3.clase5.entidades.Vacacionable;

public class EmpleadoContratado extends Empleado implements Vacacionable, Comparable<EmpleadoContratado> {
  //* Atributos
  private double valorHora = 1200.00;
  private double cantidadHorasMensuales = 160.00;

  //* Constructor(es)
  public EmpleadoContratado(String nombre, Fecha fechaIngreso) {
    super(nombre, fechaIngreso);
  }

  public EmpleadoContratado(String nombre, Fecha fechaIngreso, double valorHora) {
    super(nombre, fechaIngreso);
    this.valorHora = valorHora;
  }

  public EmpleadoContratado(String nombre, Fecha fechaIngreso, double valorHora, double cantHorasMensuales) {
    super(nombre, fechaIngreso);
    this.valorHora = valorHora;
    this.cantidadHorasMensuales = cantHorasMensuales;
  }

  //* Getters & Setters
  public double getValorHora() {
    return this.valorHora;
  }

  public double getCantidadHoras() {
    return this.cantidadHorasMensuales;
  }
  
  //* Métodos
  @Override
  public double calcularSueldo() {
    return this.valorHora * this.cantidadHorasMensuales;
  }

  public double calcularSueldo(double horas) {
    return this.valorHora * horas;
  }

  @Override
  public String toString() {
    return "Empleado: " + super.toString() + "\nSueldo mensual: " + this.calcularSueldo();
  }

  @Override
  public boolean tieneVacaciones() {
    return true;
  }

  @Override
  public int diasVacaciones() {
    return 15;
  }


  @Override
  public int compareTo(Empleado o) {
    if (o instanceof EmpleadoContratado) {
      if (this.valorHora == ((EmpleadoContratado) o).valorHora)
        return 0;
      if (this.valorHora > ((EmpleadoContratado) o).valorHora)
        return 1;
      return -1;
    }
    return super.compareTo(o);
  }
}
