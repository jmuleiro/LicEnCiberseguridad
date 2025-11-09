package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOBase;

public abstract class ServiceBase<T, K> implements IService<T, K> {
  protected DAOBase<T, K> dao;
}
