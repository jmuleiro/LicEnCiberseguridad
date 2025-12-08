package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;

import programacion3.trabajo_practico.src.dao.DAOCajaAhorro;
import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceCajaAhorro extends ServiceBase<CajaAhorro, Integer> {
  private DAOCajaAhorro dao;
  private DAOTransferencia daoTransferencia;

  public ServiceCajaAhorro() throws ServiceException {
    try {
      dao = new DAOCajaAhorro();
      daoTransferencia = new DAOTransferencia();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      return null;
    });
  }

  public void insertar(CajaAhorro elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      return null;
    });
  }

  @Override
  public CajaAhorro consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<CajaAhorro>().execute(() -> {
      return dao.consultar(id);
    });
  }

  public CajaAhorro consultarConTransferencia(Integer id) throws ServiceException {
    return new ServiceTemplate<CajaAhorro>().execute(() -> {
      CajaAhorro cajaAhorro = dao.consultar(id);
      List<Transferencia> transferencias = daoTransferencia.consultarTodos(cajaAhorro);
      for (Transferencia transferencia : transferencias) {
        cajaAhorro.agregarTransferencia(transferencia);
      }
      return cajaAhorro;
    });
  }

  @Override
  public List<CajaAhorro> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<CajaAhorro>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  public List<CajaAhorro> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<CajaAhorro>>().execute(() -> {
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
  public void modificar(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }

  public void modificarConTransferencia(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      for (Transferencia transferencia : elemento.getTransferencias()) {
        if (daoTransferencia.consultar(transferencia.getId()) == null) {
          daoTransferencia.insertar(elemento, transferencia);
        }
      }
      return null;
    });
  }

  // Reporte
  public List<String> generarReporteMovimientos(CajaAhorro elemento) {
    List<String> reporte = new ArrayList<String>();
    reporte.add("id,fecha,monto,concepto,moneda,cbu_origen,cbu_destino\n");
    for (Transferencia transferencia : elemento.getTransferencias()) {
      if (transferencia.getEntrante())
        reporte.add(transferencia.getId() + "," + transferencia.getFecha() + "," + transferencia.getMonto() + ","
            + transferencia.getConcepto() + "," + transferencia.getMoneda() + ","
            + transferencia.getCuentaTercero().getCbu() + "," + elemento.getCbu() + "\n");
      else
        reporte.add(transferencia.getId() + "," + transferencia.getFecha() + "," + transferencia.getMonto() + ","
            + transferencia.getConcepto() + "," + transferencia.getMoneda() + "," + elemento.getCbu() + ","
            + transferencia.getCuentaTercero().getCbu() + "\n");
    }
    return reporte;
  }
}
