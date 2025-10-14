package programacion3.trabajo_practico.src.dao;

import java.sql.SQLException;

// Similar a un Callback, esta clase permite reutilizar el bloque try/catch
public class DAOTemplate {
  public static void execute(String nombreElemento, DAOAction action) throws DAOException {
    try {
      // Ejecuta la accion, cualquiera sea
      action.execute();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      throw new DAOException("Fallo de SQL en el DAO de: " + nombreElemento);
    }
  }
}
