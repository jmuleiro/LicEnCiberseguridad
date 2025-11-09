package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOBaseS;

public abstract class ServiceBaseS<T, K> implements IService<T, K> {
  protected DAOBaseS<T, K> dao;

  // Método consultar por String (ej. Usuario)
  public abstract T consultar(String id) throws ServiceException;
}
