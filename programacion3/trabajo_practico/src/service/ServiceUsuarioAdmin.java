package programacion3.trabajo_practico.src.service;

import java.util.List;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioAdmin;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class ServiceUsuarioAdmin extends ServiceBaseS<UsuarioAdmin, Integer> {
  private DAOUsuarioAdmin dao;

  public ServiceUsuarioAdmin() throws ServiceException {
    try {
      dao = new DAOUsuarioAdmin();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  public void insertar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(usuarioAdmin);
      return null;
    });
  }

  public UsuarioAdmin consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      return dao.consultar(id);
    });
  }

  public UsuarioAdmin consultar(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      return dao.consultar(username);
    });
  }

  public List<UsuarioAdmin> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<UsuarioAdmin>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      return null;
    });
  }

  public void modificar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(usuarioAdmin);
      return null;
    });
  }
}
