package programacion3.trabajo_practico.src.entidades;

public class CuentaCorriente extends Cuenta {
  //* Atributos
  private double limiteGiro;
  
  //* Constructores
  public CuentaCorriente(Moneda moneda, String alias, int cbu, double limiteGiro) {
    super(moneda, alias, cbu);
    this.limiteGiro = limiteGiro;
  }

  public CuentaCorriente(Moneda moneda, String alias, int cbu, double limiteGiro, int id, double saldo) {
    super(moneda, alias, cbu, id, saldo);
    this.limiteGiro = limiteGiro;
  }

  //* Métodos
  

  //* Getters & Setters
  public double getLimiteGiro() {
    return this.limiteGiro;
  }
}
