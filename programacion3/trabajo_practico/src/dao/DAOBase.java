package programacion3.trabajo_practico.src.dao;

import java.sql.SQLException;
import java.util.List;

import programacion3.trabajo_practico.src.helpers.ConnectionHelper;

public class DAOBase<T, K> implements IDAO<T, K> {
  // * Atributos
  protected ConnectionHelper conn;
  protected String entityName;

  // * Constructor
  public DAOBase() throws DAOException {
    try {
      this.conn = new ConnectionHelper();
    } catch (SQLException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo al iniciar DAO: " + this.getClass().getName());
    } catch (ClassNotFoundException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo de drivers en: " + this.getClass().getName());
    }
    this.entityName = this.getClass().getSimpleName().substring(3);
  }

  @Override
  public T consultar(K id) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public List<T> consultarTodos() throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void insertar(T elemento) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void eliminar(K id) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void modificar(T elemento) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }
}
