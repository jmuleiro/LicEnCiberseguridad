package programacion3.trabajo_practico.src.entidades;

public class CajaAhorro extends Cuenta {
  // * Atributos
  private double porcentajeInteres;

  // * Constructores
  public CajaAhorro(Moneda moneda, String alias, String cbu, double porcentajeInteres) {
    super(moneda, alias, cbu);
    if (porcentajeInteres >= 100)
      throw new RuntimeException("No se admite un porcentaje de interés mayor a 100%");
    if (porcentajeInteres < 0)
      throw new RuntimeException("El porcentaje de interés debe ser igual o mayor a 0%");
    this.porcentajeInteres = porcentajeInteres;
  }

  public CajaAhorro(Moneda moneda, String alias, String cbu, double porcentajeInteres, int id, double saldo) {
    super(moneda, alias, cbu, id, saldo);
    this.porcentajeInteres = porcentajeInteres;
  }

  // * Métodos
  @Override
  public double extraer(double cantidad) {
    if (this.saldo < cantidad)
      throw new RuntimeException("Saldo insuficiente");

    this.saldo -= cantidad;
    return this.saldo;
  }

  public double calcularInteres(double monto) {
    return monto * (this.getPorcentajeInteres() / 100);
  }

  public double aplicarInteres() {
    this.depositar(this.calcularInteres(this.getSaldo()));
    return this.getSaldo();
  }

  // * Getters & Setters
  public double getPorcentajeInteres() {
    return this.porcentajeInteres;
  }

  public void setPorcentajeInteres(double porcentajeInteres) {
    this.porcentajeInteres = porcentajeInteres;
  }
}
