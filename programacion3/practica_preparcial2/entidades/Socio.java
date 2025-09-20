package programacion3.practica_preparcial2.entidades;

import java.util.ArrayList;
import java.util.List;

public class Socio {
  //* Atributos
  private int numeroSocio;
  private String nombre;
  private float cuotaMensual = 50000;
  // Utilizo la colección de tipo List para poder agregar y quitar actividades
  private List<Actividad> actividades;

  //* Constructor
  public Socio(int numero, String nombre) {
    this.numeroSocio = numero;
    this.nombre = nombre;
    this.actividades = new ArrayList<>();
  }

  //* Métodos
  public void inscribirActividad(Actividad actividad) {
    this.actividades.add(actividad);
  }

  public float valorAbonoMensual() {
    float resultado = this.cuotaMensual;
    for (Actividad actividad : this.actividades) {
      // Gracias al polimorfismo, no importa el tipo de actividad, ya sea ActividadGeneral, Pileta o EntrenadorPersonal
      // Como las tres clases son subclases de Actividad, que incluye valorActividad, se puede llamar libremente al método
      // Y es por el principio de encapsulamiento que podemos llamar a este método, sabiendo solamente que va a devolver
      // un valor float, pero no nos importa la lógica que está adentro, eso es responsabilidad del objeto.
      resultado += actividad.valorActividad();
    }
    return resultado;
  }

  // Getters & Setters
  public void setCuotaMensual(float cuota) {
    this.cuotaMensual = cuota;
  }

  public int getNumeroSocio() {
    return this.numeroSocio;
  }
}
