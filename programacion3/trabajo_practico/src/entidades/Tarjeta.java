package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Tarjeta {
  // * Atributos
  private int id;
  private List<Consumo> consumos;
  private String numero;
  private LocalDate fechaVencimiento;
  private int cvc;
  private double limite;

  // * Constructores
  public Tarjeta(String numero, LocalDate fechaVencimiento, int cvc, double limite) {
    this.numero = numero;
    this.fechaVencimiento = fechaVencimiento;
    this.cvc = cvc;
    this.limite = limite;
    consumos = new ArrayList<>();
  }

  public Tarjeta(String numero, LocalDate fechaVencimiento, int cvc, double limite, int id) {
    this.numero = numero;
    this.fechaVencimiento = fechaVencimiento;
    this.cvc = cvc;
    this.limite = limite;
    this.id = id;
    consumos = new ArrayList<>();
  }

  // * Métodos
  // Calcular gastos del mes actual
  public Map<String, Double> calcularGastos() {
    LocalDate fechaDesde = LocalDate.now().withDayOfMonth(1);
    LocalDate fechaHasta = LocalDate.now();
    return this.calcularGastos(fechaDesde, fechaHasta);
  }

  // Calcular gastos del período especificado
  // Devuelve un Map donde la key es el código de moneda y el valor es el total de
  // gastos
  public Map<String, Double> calcularGastos(LocalDate fechaDesde, LocalDate fechaHasta) {
    return this.consumos.stream()
        .filter(c -> {
          LocalDate fechaConsumo = c.getFecha();
          // Filtra consumos que están dentro del período, inclusive.
          return !fechaConsumo.isBefore(fechaDesde) && !fechaConsumo.isAfter(fechaHasta);
        })
        .collect(Collectors.groupingBy(
            consumo -> consumo.getMoneda().getCodigo(), // Agrupa por código de moneda
            Collectors.summingDouble(Consumo::getCantidad) // Suma las cantidades para cada grupo
        ));
  }

  // * Getters & Setters
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNumero() {
    return this.numero;
  }

  public LocalDate getFechaVencimiento() {
    return this.fechaVencimiento;
  }

  public int getCvc() {
    return this.cvc;
  }

  public List<Consumo> getConsumos() {
    return this.consumos;
  }

  public double getLimite() {
    return this.limite;
  }

  public void setLimite(double limite) {
    this.limite = limite;
  }
}
