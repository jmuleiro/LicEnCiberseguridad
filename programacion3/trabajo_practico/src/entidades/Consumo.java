package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

public class Consumo {
  //* Atributos
  private int id;
  private LocalDate fecha;
  private double cantidad;
  private Moneda moneda;

  //* Constructores
  public Consumo(double cantidad) {
    this.fecha = LocalDate.now();
    this.cantidad = cantidad;
    this.moneda = new Moneda("ARS", "Peso Argentino");
  }

  public Consumo(double cantidad, LocalDate fecha) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = new Moneda("ARS", "Peso Argentino");
  }

  public Consumo(double cantidad, Moneda moneda) {
    this.fecha = LocalDate.now();
    this.cantidad = cantidad;
    this.moneda = moneda;
  }

  public Consumo(double cantidad, LocalDate fecha, Moneda moneda) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = moneda;
  }

  public Consumo(double cantidad, LocalDate fecha, Moneda moneda, int id) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.moneda = moneda;
    this.id = id;
  }

  //* Getters & Setters
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
}
