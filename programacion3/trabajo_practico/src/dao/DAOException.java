package programacion3.trabajo_practico.src.dao;

public class DAOException extends Exception {
  // * Atributos
  private String daoClassName = null;

  // * Constructores
  public DAOException() {
    super();
  }

  public DAOException(String mensaje) {
    super(mensaje);
  }

  public DAOException(String mensaje, String dao) {
    super(mensaje);
    this.daoClassName = dao;
  }

  // * Getters & Setters
  public String getDaoClassName() {
    return this.daoClassName;
  }

  // * Métodos
  public String toString() {
    return "Exception " + this.getClass().getName() + ": " + this.getMessage();
  }
}
