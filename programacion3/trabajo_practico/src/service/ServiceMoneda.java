package programacion3.trabajo_practico.src.service;

import java.util.List;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOMoneda;
import programacion3.trabajo_practico.src.entidades.Moneda;

public class ServiceMoneda implements IService<Moneda, String>{
  private DAOMoneda dao;

  public ServiceMoneda() throws ServiceException {
    try {
      dao = new DAOMoneda();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO: "  + dao.getClass().getName());
    }
  }

  @Override
  public Moneda consultar(String id) throws ServiceException {
    try {
      return dao.consultar(id);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al consultar, DAO: " + dao.getClass().getName());
    }
  }

  @Override
  public List<Moneda> consultarTodos() throws ServiceException {
    try {
      return dao.consultarTodos();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al consultar todos, DAO: " + dao.getClass().getName());
    }
  }
  
  //* Métodos no implementados
  @Override
  public void insertar(Moneda elemento) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void eliminar(String id) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void modificar(Moneda elemento) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }
}
