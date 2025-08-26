package programacion3.practica2.entidades.exceptions;

public class SaldoException extends Exception {
  //* Constructores
  public SaldoException() {
    super();
  }

  public SaldoException(String message) {
    super(message);
  }

  //* Métodos
  public String toString() {
    return "Exception " + this.getClass().getName() + ": " + this.getMessage();
  }
}
