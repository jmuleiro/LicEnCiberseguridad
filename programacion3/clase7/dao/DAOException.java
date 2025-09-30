package programacion3.clase7.dao;

public class DAOException extends Exception {
  //* Constructores
  public DAOException() {
    super();
  }

  public DAOException(String mensaje) {
    super(mensaje);
  }

  //* Métodos
  public String toString() {
    return "Exception " + this.getClass().getName() + ": " + this.getMessage();
  }
}
