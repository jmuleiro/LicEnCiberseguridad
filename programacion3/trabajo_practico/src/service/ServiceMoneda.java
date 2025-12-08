package programacion3.trabajo_practico.src.service;

import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOMoneda;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.Moneda;

public class ServiceMoneda extends ServiceBase<Moneda, String> {
  private DAOMoneda dao;
  private DAOEvento daoEvento;

  public ServiceMoneda(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOMoneda();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public Moneda consultar(String id) throws ServiceException {
    return new ServiceTemplate<Moneda>().execute(() -> {
      return dao.consultar(id);
    });
  }

  @Override
  public List<Moneda> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<Moneda>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  // * Métodos no implementados
  @Override
  public void insertar(Moneda elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      return null;
    });
  }

  @Override
  public void eliminar(String id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      return null;
    });
  }

  @Override
  public void modificar(Moneda elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }
}
