package programacion3.trabajo_practico.src.service;

import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOBaseS;

public abstract class ServiceBaseS<T, K> implements IService<T, K> {
  protected DAOBaseS<T, K> dao;
  protected Map<String, String> contexto;

  public ServiceBaseS(Map<String, String> contexto) {
    this.dao = null;
    this.contexto = contexto;
  }

  // Método consultar por String (ej. Usuario)
  public abstract T consultar(String id) throws ServiceException;
}
