package programacion3.trabajo_practico.src.dao;

import java.util.List;

public interface IDAO<T, K> {
  public void insertar(T elemento) throws DAOException;
  public void modificar(T elemento) throws DAOException;
  public void eliminar(K id) throws DAOException;
  public T consultar(K id) throws DAOException;
  public List<T> consultarTodos() throws DAOException;
}
