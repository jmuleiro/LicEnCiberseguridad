package programacion3.trabajo_practico.src.service;

import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioCliente;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceUsuarioCliente extends ServiceBaseS<UsuarioCliente, Integer> {
  private DAOEvento daoEvento;

  public ServiceUsuarioCliente(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOUsuarioCliente();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public UsuarioCliente consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, id.toString()), contexto);
      return usuarioCliente;
    });
  }

  @Override
  public UsuarioCliente consultar(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(username);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, username), contexto);
      return usuarioCliente;
    });
  }

  @Override
  public List<UsuarioCliente> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<UsuarioCliente>>().execute(() -> {
      List<UsuarioCliente> usuarioClientes = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);
      return usuarioClientes;
    });
  }

  @Override
  public void insertar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.USUARIO, elemento.getUsuario()), contexto);
      return null;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(new Evento(TipoEvento.ELIMINACION, TipoObjeto.USUARIO, id.toString()), contexto);
      return null;
    });
  }

  @Override
  public void modificar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.USUARIO, elemento.getUsuario()), contexto);
      return null;
    });
  }
}
