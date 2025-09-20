package programacion3.practica_preparcial2.entidades;

// Se utiliza herencia, extendiendo la clase Actividad, ya que es la superclase que tiene el comportamiento básico
public class ActividadGeneral extends Actividad {
  //* Atributos
  private int cantidadDias;
  private float costoDia;

  //* Constructor
  public ActividadGeneral(String codigo, String nombre, int dias, float costo) {
    super(codigo, nombre);
    this.setCantidadDias(dias);
    this.costoDia = costo;
  }
  
  //* Métodos
  // Devuelve costo mensual de la actividad general
  // Se asume que el costo será cantidadDias * costoDia * 4, habiendo aprox.
  // cuatro semanas por mes.
  public float valorActividad() {
    return cantidadDias * costoDia * 4;
  }

  // Getters & Setters
  public void setCantidadDias(int dias) {
    if (dias < 1)
      throw new RuntimeException("La actividad debe darse al menos 1 vez por semana");
    if (dias > 5)
      throw new RuntimeException("El gimnasio solo abre 5 días a la semana");
    this.cantidadDias = dias;
  }
}
