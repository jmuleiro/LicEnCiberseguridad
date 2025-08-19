package programacion3.clase2.entidades;

import java.time.DateTimeException;

public class Fecha {
  private int dia;
  private int mes;
  private int anio;

  // Constructor por defecto
  public Fecha() {
    // palabra clave "this" para referirse a esta instancia de la clase
    this.dia = 1;
    this.mes = 1;
    this.anio = 2000;
  }
  
  // Constructor con parametros
  public Fecha(int d, int m, int a) {
    this.dia = d;
    this.mes = m;
    this.anio = a;
  }

  // Metodo toString
  public String toString() {
    return dia + "/" + mes + "/" + anio;
  }

  // Getters & Setters
  public void setDia(int d) {
    if (d < 1 || d > 31)
      throw new DateTimeException("El dia no es valido, debe estar entre 1 y 31");
    dia = d;
  }

  public int getDia() {
    return dia;
  }

  public void setMes(int m) { 
    mes = m;
  }

  public int getMes() {
    return mes;
  }

  public void setAnio(int a) {
    anio = a;
  }

  public int getAnio() {
    return anio;
  }

  // Metodos
  public int consultarDia(){
    return getDia();
  }

  public void sumarDias(int dias) {
    dia += dias;
    mes += dia / 31;
    dia = dia % 31;
    anio += mes / 12;
    mes = mes % 12;
  }

  public int restarAnio(Fecha f) {
    return anio -= f.getAnio();
  }
}
