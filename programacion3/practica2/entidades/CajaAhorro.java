package programacion3.practica2.entidades;

import programacion3.practica2.entidades.exceptions.SaldoException;

public class CajaAhorro extends Cuenta {
  //* Atributos
  private double interesMensual = 0.01;
  
  //* Constructores
  public CajaAhorro(String titular) {
    super(titular);
  }

  public CajaAhorro(String titular, double porcentajeInteresMensual) {
    super(titular);
    this.interesMensual = (porcentajeInteresMensual / 100);
  }
  
  //* Métodos
  public void extraer(double cantidad) throws SaldoException {
    double _saldo = this.getSaldo();
    double operacion = _saldo - cantidad;
    if (operacion <= 0.00)
      throw new SaldoException("La Caja de Ahorro no tiene suficiente saldo. Saldo actual: " + _saldo);
    this.setSaldo(operacion);
  }

  public double calcularInteres() {
    return this.getSaldo() * this.interesMensual;
  }

  public void acreditarInteres() {
    this.depositar(this.calcularInteres());
  }
}
