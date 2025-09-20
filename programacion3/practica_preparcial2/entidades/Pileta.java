package programacion3.practica_preparcial2.entidades;

// Se utiliza herencia, extendiendo la clase Actividad, ya que es la superclase que tiene el comportamiento básico
public class Pileta extends Actividad {
  //* Atributos
  private float costoAcceso;

  //* Constructor
  public Pileta(String codigo, String nombre, float costo) {
    super(codigo, nombre);
    this.costoAcceso = costo;
  }

  //* Métodos
  // Único costo mensual
  public float valorActividad() {
    return this.costoAcceso;
  }
}
