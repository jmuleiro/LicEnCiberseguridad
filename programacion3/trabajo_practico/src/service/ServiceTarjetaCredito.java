package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOTarjetaCredito;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceTarjetaCredito extends ServiceBase<TarjetaCredito, Integer> {
  private DAOTarjetaCredito dao;

  public ServiceTarjetaCredito() throws ServiceException {
    try {
      dao = new DAOTarjetaCredito();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(TarjetaCredito elemento) throws ServiceException {
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(TarjetaCredito elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      return null;
    });
  }

  @Override
  public TarjetaCredito consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<TarjetaCredito>().execute(() -> {
      return dao.consultar(id);
    });
  }

  @Override
  public List<TarjetaCredito> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  public List<TarjetaCredito> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      return dao.consultarTodos(usuario);
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
  public void modificar(TarjetaCredito elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }
}
