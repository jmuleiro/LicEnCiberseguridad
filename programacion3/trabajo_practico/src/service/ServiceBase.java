package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOBase;
import programacion3.trabajo_practico.src.dao.DAOException;

public abstract class ServiceBase<T, K> implements IService<T, K> {
  protected DAOBase<T, K> dao;

  public ServiceBase() throws ServiceException {
    try {
      dao = new DAOBase<T, K>();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO: " + dao.getClass().getName());
    }
  }
}
