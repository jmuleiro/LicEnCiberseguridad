package programacion3.clase4.entidades;

public class FechaException extends Exception {
  //* Constructores
  public FechaException() {
    super();
  }

  public FechaException(String mensaje) {
    super(mensaje);
  }

  public String toString() {
    return "Exception " + this.getClass().getName() + ": " + this.getMessage();
  }
}
