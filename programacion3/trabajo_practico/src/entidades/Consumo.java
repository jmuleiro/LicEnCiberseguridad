package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

public class Consumo {
  // * Atributos
  private int id;
  private LocalDate fecha;
  private double cantidad;
  private Moneda moneda;
  private String referencia;

  // * Constructores
  public Consumo(double cantidad, String referencia) {
    this.fecha = LocalDate.now();
    this.cantidad = cantidad;
    this.moneda = new Moneda("ARS", "Peso Argentino");
    this.referencia = referencia;
  }

  public Consumo(double cantidad, LocalDate fecha, String referencia) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = new Moneda("ARS", "Peso Argentino");
    this.referencia = referencia;
  }

  public Consumo(double cantidad, Moneda moneda, String referencia) {
    this.fecha = LocalDate.now();
    this.cantidad = cantidad;
    this.moneda = moneda;
    this.referencia = referencia;
  }

  public Consumo(double cantidad, LocalDate fecha, Moneda moneda, String referencia) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = moneda;
    this.referencia = referencia;
  }

  public Consumo(double cantidad, LocalDate fecha, Moneda moneda, int id, String referencia) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = moneda;
    this.id = id;
    this.referencia = referencia;
  }

  // * Getters & Setters
  public int getId() {
    return this.id;
  }

  public LocalDate getFecha() {
    return this.fecha;
  }

  public double getCantidad() {
    return this.cantidad;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public String getReferencia() {
    return this.referencia;
  }
}
