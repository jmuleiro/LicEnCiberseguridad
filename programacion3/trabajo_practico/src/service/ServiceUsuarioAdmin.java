package programacion3.trabajo_practico.src.service;

import java.util.List;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioAdmin;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class ServiceUsuarioAdmin implements IService<UsuarioAdmin, Integer>{
  private DAOUsuarioAdmin dao;

  public ServiceUsuarioAdmin() throws ServiceException {
    try {
      dao = new DAOUsuarioAdmin();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO");
    }
  }

  public void insertar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    try {
      dao.insertar(usuarioAdmin);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al insertar, DAO: " + dao.getClass().getName());
    }
  }

  public UsuarioAdmin consultar(Integer id) throws ServiceException {
    try {
      return dao.consultar(id);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al consultar, DAO: " + dao.getClass().getName());
    }
  }

  public UsuarioAdmin consultar(String username) throws ServiceException {
    try {
      return dao.consultar(username);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al consultar, DAO: " + dao.getClass().getName());
    }
  }

  public List<UsuarioAdmin> consultarTodos() throws ServiceException {
    try {
      return dao.consultarTodos();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al consultar todos, DAO: " + dao.getClass().getName());
    }
  }

  public void eliminar(Integer id) throws ServiceException {
    try {
      dao.eliminar(id);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al eliminar, DAO: " + dao.getClass().getName());
    }
  }

  public void modificar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    try {
      dao.modificar(usuarioAdmin);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al modificar, DAO: " + dao.getClass().getName());
    }
  }
}
