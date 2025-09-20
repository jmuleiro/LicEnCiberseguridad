package programacion3.practica_preparcial2.entidades;

import java.util.ArrayList;
import java.util.List;

public class Gimnasio {
  //* Atributos
  private String nombre;
  // Utilizo la colección de tipo List para poder agregar y quitar socios
  private List<Socio> socios;

  //* Constructor
  public Gimnasio(String nombre) {
    this.nombre = nombre;
    this.socios = new ArrayList<>();
  }

  //* Métodos
  public void agregarSocio(int nro, String nombre, double cuota, Actividad actividad) {
    Socio nuevo = new Socio(nro, nombre);
    nuevo.setCuotaMensual((float) cuota);
    nuevo.inscribirActividad(actividad);
    this.socios.add(nuevo);
  }

  public void inscribirSocioActividad(int nro, Actividad actividad) {
    this.consultarSocio(nro).inscribirActividad(actividad);
  }

  public Socio consultarSocio(int numero) {
    for (Socio s : this.socios) {
      if (s.getNumeroSocio() == numero)
        return s;
    }
    throw new RuntimeException("No se encontró al socio");
  }

  public double importeSocio(Socio socio) {
    return socio.valorAbonoMensual();
  }

  public double ingresoTotalPorPagoCuotas() {
    double total = 0;
    for (Socio s : this.socios) {
      total += s.valorAbonoMensual();
    }
    return total;
  }
}
