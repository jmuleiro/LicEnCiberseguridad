package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceUsuarioCliente extends ServiceBaseS<UsuarioCliente, Integer>{
  public ServiceUsuarioCliente() throws ServiceException {
    super();
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
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void insertar(UsuarioCliente elemento) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void modificar(UsuarioCliente elemento) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

}
