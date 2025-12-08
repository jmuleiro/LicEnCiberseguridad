package programacion3.trabajo_practico.src.service;

import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOBase;

public abstract class ServiceBase<T, K> implements IService<T, K> {
  protected DAOBase<T, K> dao;
  protected Map<String, String> contexto;

  public ServiceBase(Map<String, String> contexto) {
    this.dao = null;
    this.contexto = contexto;
  }
}
