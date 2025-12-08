package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

public class Transferencia {
  // * Atributos
  private int id;
  private double monto;
  private LocalDate fecha;
  private Moneda moneda;
  private String concepto;
  private Cuenta cuentaTercero;
  private boolean entrante = false;

  // * Constructores
  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto, Cuenta cuentaTercero) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.cuentaTercero = cuentaTercero;
  }

  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto, Cuenta cuentaTercero,
      boolean entrante) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.cuentaTercero = cuentaTercero;
    this.entrante = entrante;
  }

  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto, Cuenta cuentaTercero, int id) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.cuentaTercero = cuentaTercero;
    this.id = id;
  }

  public Transferencia(LocalDate fecha, double monto, Moneda moneda, String concepto, Cuenta cuentaTercero,
      boolean entrante, int id) {
    this.fecha = fecha;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.cuentaTercero = cuentaTercero;
    this.entrante = entrante;
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

  public Cuenta getCuentaTercero() {
    return this.cuentaTercero;
  }

  public boolean getEntrante() {
    return this.entrante;
  }
}
