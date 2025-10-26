package programacion3.trabajo_practico.src.entidades;

public class Transferencia {
  //* Atributos
  private int id;
  private Cuenta cuentaOrigen;
  private Cuenta cuentaDestino;
  private double monto;
  private Moneda moneda;
  private String concepto;

  //* Constructores
  public Transferencia(Cuenta origen, Cuenta destino, double monto, Moneda moneda, String concepto) {
    this.cuentaOrigen = origen;
    this.cuentaDestino = destino;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
  }

  public Transferencia(Cuenta origen, Cuenta destino, double monto, Moneda moneda, String concepto, int id) {
    this.cuentaOrigen = origen;
    this.cuentaDestino = destino;
    this.monto = monto;
    this.moneda = moneda;
    this.concepto = concepto;
    this.id = id;
  }

  //* Métodos
  public void ejecutar() {
    this.cuentaOrigen.extraer(this.monto);
    this.cuentaDestino.depositar(this.monto);
  }

  //* Getters & Setters
  public int getId() {
    return this.id;
  }

  public Cuenta getCuentaOrigen() {
    return this.cuentaOrigen;
  }

  public Cuenta getCuentaDestino() {
    return this.cuentaDestino;
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
