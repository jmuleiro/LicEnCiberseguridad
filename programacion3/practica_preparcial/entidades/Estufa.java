package programacion3.practica_preparcial.entidades;

public class Estufa {
  //* Atributos
  private int cantidadQuemadores;
  
  //* Constructor
  public Estufa(int cantidadQuemadores) {
    this.cantidadQuemadores = cantidadQuemadores;
  }

  //* Métodos
  public int caloriasProvistas() {
    return this.cantidadQuemadores * 300;
  }
}
