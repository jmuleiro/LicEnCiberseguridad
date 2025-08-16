package programacion3.practica1.ej9;
import programacion3.practica1.ej6.Rueda;
import programacion3.practica1.ej7.CajaVelocidades;
import programacion3.practica1.ej8.Motor;

public class Auto {
  //* Atributos
  private Rueda[] ruedas = new Rueda[4];
  private CajaVelocidades caja;
  private Motor motor;
  private String fabricante;
  private String modelo;
  private int cantPuertas = 5;
  private boolean tieneAireAcondicionado = true;

  //* Constructores
  public Auto(String fabricante, String modelo, CajaVelocidades caja, Motor motor) {
    this.fabricante = fabricante;
    this.modelo = modelo;
    this.caja = caja;
    this.motor = motor;
    for (int i = 0; i < ruedas.length; i++)
      this.ruedas[i] = new Rueda(55.0, "Negro", "Acero");
  }

  public Auto(String fabricante, String modelo, CajaVelocidades caja, Motor motor, double rodado) {
    this.fabricante = fabricante;
    this.modelo = modelo;
    this.caja = caja;
    this.motor = motor;
    for (int i = 0; i < ruedas.length; i++)
      this.ruedas[i] = new Rueda(rodado, "Negro", "Acero");
  }

  public Auto(String fabricante, String modelo, CajaVelocidades caja, Motor motor, double rodado, int cantPuertas, boolean aireAcondicionado) {
    this.fabricante = fabricante;
    this.modelo = modelo;
    this.caja = caja;
    this.motor = motor;
    for (int i = 0; i < ruedas.length; i++)
      this.ruedas[i] = new Rueda(rodado, "Negro", "Acero");
    this.cantPuertas = cantPuertas;
    this.tieneAireAcondicionado = aireAcondicionado;
  }

  //* Getters & Setters
  public String getFabricante() {
    return this.fabricante;
  }

  public String getModelo() {
    return this.modelo;
  }

  public boolean getAireAcondicionado () {
    return this.tieneAireAcondicionado;
  }

  //* Métodos
  public String toString() {
    return "Fabricante: " + this.fabricante + ", modelo: " + this.modelo + ", cant. puertas: " + this.cantPuertas + ", cilindrada: " + this.motor.getCilindrada() + ", HP: " + this.motor.getHorsepower() + ", cant. marchas: " + this.caja.getCantidadMarchas();
  }
}
