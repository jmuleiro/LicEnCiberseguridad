package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOCajaAhorro;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;

public class ServiceCajaAhorro extends ServiceBase<CajaAhorro, Integer>{
  private DAOCajaAhorro dao;

  public ServiceCajaAhorro() throws ServiceException {
    try {
      dao = new DAOCajaAhorro();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      return null;
    });
  }

  @Override
  public CajaAhorro consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<CajaAhorro>().execute(() -> {
      return dao.consultar(id);
    });
  }

  @Override
  public List<CajaAhorro> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<CajaAhorro>>().execute(() -> {
      return dao.consultarTodos();
    });
  }
  
  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      return null;
    });
  }

  @Override
  public void modificar(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }
}
