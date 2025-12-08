package programacion3.trabajo_practico.src.service;

import java.util.List;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioAdmin;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class ServiceUsuarioAdmin extends ServiceBaseS<UsuarioAdmin, Integer> {
  private DAOUsuarioAdmin dao;
  private DAOEvento daoEvento;

  public ServiceUsuarioAdmin() throws ServiceException {
    try {
      dao = new DAOUsuarioAdmin();
      daoEvento = new DAOEvento();
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

  public UsuarioAdmin login(String username, String password) throws ServiceException {
    try {
      UsuarioAdmin usuarioAdmin = this.consultar(username);
      if (usuarioAdmin == null) {
        daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username, false));
        throw new ServiceException("Usuario no encontrado");
      }

      daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username));
      return usuarioAdmin;
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException(
          "Fallo del DAO " + daoEvento.getClass().getName() + " en: " + this.getClass().getName());
    }
  }
}
