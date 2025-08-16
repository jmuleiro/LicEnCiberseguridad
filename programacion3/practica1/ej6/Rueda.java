package programacion3.practica1.ej6;

public class Rueda {
  //* Atributos
  private double radio;
  private String color;
  private String tipoMaterial;
  private int posicion = 0;

  //* Constructor
  public Rueda(double radio, String color, String tipoMaterial) {
    this.radio = radio;
    this.color = color;
    this.tipoMaterial = tipoMaterial;
  }

  //* Getters & Setters
  public double getRadio() {
    return this.radio;
  }

  public String getColor() {
    return this.color;
  }

  public String getTipoMaterial() {
    return this.tipoMaterial;
  }

  public int getPosicion() {
    return this.posicion;
  }

  //* Métodos
  public String toString() {
    return "Radio: " + radio + ", color: " + color + ", tipo: " + tipoMaterial + ", posición: " + posicion;
  }

  public void girar() {
    int tempPos = this.posicion;
    tempPos += 1;
    if (tempPos > 3)
      tempPos = 1;
    this.posicion = tempPos;
  }
}
