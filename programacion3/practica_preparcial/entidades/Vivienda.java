package programacion3.practica_preparcial.entidades;

import java.util.List;
import java.util.ArrayList;

public abstract class Vivienda {
  //* Atributos
  private int superficie;
  private boolean tieneAislacion = false;
  private double porcentajeDescuento = 0.0;
  private List<Estufa> estufas;

  //* Constructor(es)
  public Vivienda(int superficie) {
    this.superficie = superficie;
    this.estufas = new ArrayList<>();
  }

  public Vivienda(int superficie, double porcentajeDescuento) {
    this.superficie = superficie;
    if (porcentajeDescuento < 0)
      throw new RuntimeException("Se indicó un porcentaje de descuento negativo");
    this.porcentajeDescuento = porcentajeDescuento;
    this.tieneAislacion = true;
    this.estufas = new ArrayList<>();
  }

  //* Métodos
  public abstract int caloriasNecesarias();

  public int caloriasCubiertas() {
    int resultado = 0;
    for (Estufa estufa : estufas) {
      resultado += estufa.caloriasProvistas();
    }
    return resultado;
  }

  // Getters & Setters
  public int getSuperficie() {
    return this.superficie;
  }

  public boolean getTieneAislacion() {
    return this.tieneAislacion;
  }

  public double getPorcentajeDescuento() {
    return this.porcentajeDescuento;
  }
}
