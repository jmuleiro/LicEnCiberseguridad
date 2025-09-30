package programacion3.clase7.dao;

import java.util.List;

public interface IDAO<T> {
  public void insertar(T elemento) throws DAOException;
  public void modificar(T elemento) throws DAOException;
  public void eliminar(int id) throws DAOException;
  public T consultar(int id) throws DAOException;
  public List<T> consultarTodos() throws DAOException;
}
