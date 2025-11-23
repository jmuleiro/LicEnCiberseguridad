package programacion3.trabajo_practico.src.service;

import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Transferencia;

public class ServiceTransferencia extends ServiceBase<Transferencia, Integer> {
  private DAOTransferencia daoTransferencia;

  public ServiceTransferencia() {
    try {
      this.daoTransferencia = new DAOTransferencia();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Transferencia consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<Transferencia>().execute(() -> {
      return daoTransferencia.consultar(id);
    });
  }

  @Override
  public void insertar(Transferencia elemento) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  public void registrarTransferencia(Cuenta origen, Cuenta destino, Transferencia elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      daoTransferencia.insertar(origen, destino, elemento);
      return null;
    });
  }

  @Override
  public void eliminar(Integer id) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public List<Transferencia> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<Transferencia>>().execute(() -> {
      return daoTransferencia.consultarTodos();
    });
  }

  @Override
  public void modificar(Transferencia elemento) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Método no implementado");
  }
}
