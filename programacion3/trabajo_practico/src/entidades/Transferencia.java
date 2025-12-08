package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

public class Transferencia {
  // * Atributos
  private int id;
  private double monto;
  private LocalDate fecha;
  private Moneda moneda;
  private String concepto;

  // * Constructores
  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
  }

  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto, int id) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.id = id;
  }

  // * Getters & Setters
  public int getId() {
    return this.id;
  }

  public double getMonto() {
    return this.monto;
  }

  public LocalDate getFecha() {
    return this.fecha;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public String getConcepto() {
    return this.concepto;
  }
}
