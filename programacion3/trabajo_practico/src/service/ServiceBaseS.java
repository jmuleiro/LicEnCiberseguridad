package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOBaseS;
import programacion3.trabajo_practico.src.dao.DAOException;

public abstract class ServiceBaseS<T, K> implements IService<T, K> {
  protected DAOBaseS<T, K> dao;

  public ServiceBaseS() throws ServiceException {
    try {
      dao = new DAOBaseS<T, K>();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO");
    }
  }

  // Método consultar por String (ej. Usuario)
  public abstract T consultar(String id) throws ServiceException;
}
