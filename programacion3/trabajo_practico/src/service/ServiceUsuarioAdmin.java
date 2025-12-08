package programacion3.trabajo_practico.src.service;

import java.util.List;
import java.util.Map;

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

  public ServiceUsuarioAdmin(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOUsuarioAdmin();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(usuarioAdmin);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.USUARIO, usuarioAdmin.getUsuario()), contexto);
      return null;
    });
  }

  public UsuarioAdmin consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      UsuarioAdmin usuarioAdmin = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, id.toString()), contexto);
      return usuarioAdmin;
    });
  }

  public UsuarioAdmin consultar(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      UsuarioAdmin usuarioAdmin = dao.consultar(username);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, username), contexto);
      return usuarioAdmin;
    });
  }

  public List<UsuarioAdmin> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<UsuarioAdmin>>().execute(() -> {
      List<UsuarioAdmin> usuarioAdmins = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);
      return usuarioAdmins;
    });
  }

  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(new Evento(TipoEvento.ELIMINACION, TipoObjeto.USUARIO, id.toString()), contexto);
      return null;
    });
  }

  public void modificar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(usuarioAdmin);
      daoEvento.insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.USUARIO, usuarioAdmin.getUsuario()),
          contexto);
      return null;
    });
  }

  public UsuarioAdmin login(String username, String password) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      UsuarioAdmin usuarioAdmin = dao.consultar(username);
      if (usuarioAdmin == null) {
        daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username, false));
        return null;
      }

      daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username));
      return usuarioAdmin;
    });
  }

  public void logout(String username) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      daoEvento.insertar(new Evento(TipoEvento.LOGOUT, TipoObjeto.USUARIO, username), contexto);
      return null;
    });
  }
}
