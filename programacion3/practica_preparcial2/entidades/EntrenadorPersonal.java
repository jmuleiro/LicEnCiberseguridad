package programacion3.practica_preparcial2.entidades;

// Se utiliza herencia, extendiendo la clase Actividad, ya que es la superclase que tiene el comportamiento básico
public class EntrenadorPersonal extends Actividad {
  //* Atributos
  private float costoHora;
  private int horas;

  //* Constructor
  public EntrenadorPersonal(String codigo, String nombre, float costo, int horas){
    super(codigo, nombre);
    this.costoHora = costo;
    this.horas = horas;
  }

  //* Métodos
  // Devuelve el costo de actividad con entrenador, mensual, según cantidad de horas por mes
  public float valorActividad() {
    return this.costoHora * this.horas;
  }
}
