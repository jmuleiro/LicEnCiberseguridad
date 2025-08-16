package programacion3.practica1.ej8;

public class Motor {
  //* Atributos
  private String fabricante;
  private double hp;
  private int cilindrada;
  private boolean encendido = false;

  //* Constructor
  public Motor(String marca, double horsepower, int cilindrada) {
    this.fabricante = marca;
    this.hp = horsepower;
    this.cilindrada = cilindrada;
  }

  //* Getters & Setters
  public String getFabricante() {
    return this.fabricante;
  }

  public double getHorsepower() {
    return this.hp;
  }

  public int getCilindrada() {
    return this.cilindrada;
  }

  //* Métodos
  public String toString() {
    return "Fabricante: " + this.fabricante + ", HP: " + this.hp + ", cilindrada: " + this.cilindrada;
  }

  public void encender() {
    if (this.encendido)
      throw new Error("El motor ya está encendido");
    this.encendido = true;
  }

  public void apagar() {
    if (!this.encendido)
      throw new Error("El motor ya está apagado");
    this.encendido = false;
  }
}
