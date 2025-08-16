package programacion3.practica1.ej1;

import java.time.DateTimeException;

public class Hora {
  private int hora;
  private int minuto;
  private int segundo;

  //* Constructores
  public Hora() {
    this.hora = 10;
    this.minuto = 30;
    this.segundo = 00;
  }

  public Hora(int hora, int minuto, int segundo) {
    this.setHora(hora);
    this.setMinuto(minuto);
    this.setSegundo(segundo);
  }

  // Metodo toString
  public String toString() {
    String _h = "" + this.hora;
    String _m = "" + this.minuto;
    String _s = "" + this.segundo;
    if (this.hora < 10)
      _h = "0" + this.hora;
    if (this.minuto < 10)
      _m = "0" + this.minuto;
    if (this.segundo < 10)
      _s = "0" + this.segundo;
    return _h + ":" + _m + ":" + _s;
  }

  //* Getters & Setters
  // Hora
  public void setHora(int h) {
    if (h < 0 || h > 23)
      throw new DateTimeException("La hora debe estar entre 0 y 23");
    this.hora = h;
  }

  public int getHora() {
    return this.hora;
  }

  // Minuto
  public void setMinuto(int m) {
    if (m < 0 || m > 59)
      throw new DateTimeException("El minuto debe estar entre 0 y 59");
    this.minuto = m;
  }

  public int getMinuto() {
    return this.minuto;
  }

  // Segundo
  public void setSegundo(int s) {
    if (s < 0 || s > 59)
      throw new DateTimeException("El segundo debe estar entre 0 y 59");
    this.segundo = s;
  }

  public int getSegundo() {
    return this.segundo;
  }

  //* Métodos
  public void incrementarMinutos(int min) {
    this.minuto += min;
    this.hora += this.minuto / 60;
    this.minuto = this.minuto % 60;
    this.hora = this.hora % 24;
  }
}
