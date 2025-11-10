package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOCuentaCorriente;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceCuentaCorriente extends ServiceBase<CuentaCorriente, Integer>{
  private DAOCuentaCorriente dao;

  public ServiceCuentaCorriente() throws ServiceException {
    try {
      dao = new DAOCuentaCorriente();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(CuentaCorriente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      return null;
    });
  }

  public void insertar(CuentaCorriente elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      return null;
    });
  }

  @Override
  public CuentaCorriente consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<CuentaCorriente>().execute(() -> {
      return dao.consultar(id);
    });
  }

  @Override
  public List<CuentaCorriente> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<CuentaCorriente>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  public List<CuentaCorriente> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<CuentaCorriente>>().execute(() -> {
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
  public void modificar(CuentaCorriente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }
}
