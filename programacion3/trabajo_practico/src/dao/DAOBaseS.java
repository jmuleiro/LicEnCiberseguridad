package programacion3.trabajo_practico.src.dao;

public class DAOBaseS<T, K> extends DAOBase<T, K> {
  //* Constructor
  public DAOBaseS() throws DAOException {
    super();
  }

  // Consultar por un ID String (ej. Usuario)
  public T consultar(String id) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }
}
