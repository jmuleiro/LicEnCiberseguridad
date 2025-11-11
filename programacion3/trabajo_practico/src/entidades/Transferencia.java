package programacion3.trabajo_practico.src.entidades;

public class Transferencia {
  //* Atributos
  private int id;
  private double monto;
  private Moneda moneda;
  private String concepto;

  //* Constructores
  public Transferencia(double monto, Moneda moneda, String concepto) {
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
  }

  public Transferencia(double monto, Moneda moneda, String concepto, int id) {
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.id = id;
  }

  //* Getters & Setters
  public int getId() {
    return this.id;
  }

  public double getMonto() {
    return this.monto;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public String getConcepto() {
    return this.concepto;
  }
}
