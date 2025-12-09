package programacion3.trabajo_practico.src.service;

import java.util.List;

public interface IService<T, K> {
  public void insertar(T elemento) throws ServiceException;

  public void modificar(T elemento) throws ServiceException;

  public void eliminar(K id) throws ServiceException;

  public T consultar(K id) throws ServiceException;

  public List<T> consultarTodos() throws ServiceException;
}
