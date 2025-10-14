package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioAdmin;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class ServiceUsuarioAdmin {
  private DAOUsuarioAdmin daoUsuarioAdmin;

  public ServiceUsuarioAdmin() throws ServiceException {
    try {
      daoUsuarioAdmin = new DAOUsuarioAdmin();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO");
    }
  }

  public void insertar(UsuarioAdmin usuarioAdmin) throws ServiceException {
    try {
      daoUsuarioAdmin.insertar(usuarioAdmin);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al insertar, DAO: " + daoUsuarioAdmin.getClass().getName());
    }
  }
}
