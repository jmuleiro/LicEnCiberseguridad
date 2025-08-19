package programacion3.clase3.entidades;
import programacion3.clase2.entidades.Fecha;
import programacion3.clase2.entidades.Empleado;

public class EmpleadoEfectivo extends Empleado {
  //* Atributos
  private double sueldoBasico;
  private double descuento;
  private double porcentajeBonoAntiguedad = 0.05;

  //* Constructor(es)
  public EmpleadoEfectivo(String nombre, Fecha fechaIngreso, double sueldoBasico, double porcentajeDescuento) {
    super(nombre, fechaIngreso);
    this.sueldoBasico = sueldoBasico;
    this.descuento = porcentajeDescuento;
  }

  //* Métodos
  public double calcularDescuento() {
    return (this.sueldoBasico / 100) * this.descuento;
  }

  @Override
  public double calcularSueldo() {
    // Fecha placeholder
    Fecha comp = new Fecha(19,8,2025);
    return this.sueldoBasico - this.calcularDescuento() + this.calcularBonoAntiguedad(comp);
  }

  public double calcularBonoAntiguedad(Fecha comp) {
    return this.porcentajeBonoAntiguedad * this.sueldoBasico * antiguedad(comp);
  }

  @Override
  public String toString() {
    return "Empleado: " + super.toString() + "\nSueldo: " + this.calcularSueldo();
  }
}
