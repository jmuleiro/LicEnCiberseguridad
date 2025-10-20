package programacion3.trabajo_practico.src.dao;

import java.sql.SQLException;

// Similar a un Callback, esta clase permite reutilizar el bloque try/catch
public class DAOTemplate<T> {
  public T execute(String nombreElemento, DAOAction<T> action) throws DAOException {
    try {
      // Ejecuta la accion, cualquiera sea
      return action.execute();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      throw new DAOException("Fallo de SQL en el DAO de: " + nombreElemento);
    }
  }
}
