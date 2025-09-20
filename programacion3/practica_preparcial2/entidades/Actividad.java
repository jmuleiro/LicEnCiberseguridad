package programacion3.practica_preparcial2.entidades;

// Actividad es la clase "padre" o superclase en la cual se basan ActividadGeneral, Pileta, y EntrenadorPersonal
public abstract class Actividad {
  //* Atributos
  private String codigo;
  private String nombre;

  //* Constructor
  public Actividad(String codigo, String nombre) {
    this.codigo = codigo;
    this.nombre = nombre;
  }

  //* Métodos
  // Polimorfismo: Se utiliza un método y una clase abstractos para poder
  // definir el comportamiento del método en las clases hijas de la clase Actividad
  public abstract float valorActividad();
}
