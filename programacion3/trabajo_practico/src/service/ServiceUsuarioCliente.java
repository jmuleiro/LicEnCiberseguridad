package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioCliente;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceUsuarioCliente extends ServiceBaseS<UsuarioCliente, Integer>{
  public ServiceUsuarioCliente() throws ServiceException {
    try {
      dao = new DAOUsuarioCliente();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public UsuarioCliente consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      return dao.consultar(id);
    });
  }

  @Override
  public UsuarioCliente consultar(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      return dao.consultar(username);
    });
  }

  @Override
  public List<UsuarioCliente> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<UsuarioCliente>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  @Override
  public void insertar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      return null;
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
  public void modificar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }
}
