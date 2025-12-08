package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOCuentaCorriente;
import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceCuentaCorriente extends ServiceBase<CuentaCorriente, Integer> {
  private DAOCuentaCorriente dao;
  private DAOTransferencia daoTransferencia;
  private DAOEvento daoEvento;

  public ServiceCuentaCorriente(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOCuentaCorriente();
      daoTransferencia = new DAOTransferencia();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(CuentaCorriente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      daoEvento.insertar(
          new Evento(TipoEvento.CREACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  public void insertar(CuentaCorriente elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      daoEvento.insertar(
          new Evento(TipoEvento.CREACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  @Override
  public CuentaCorriente consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<CuentaCorriente>().execute(() -> {
      CuentaCorriente cuentaCorriente = dao.consultar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, id.toString()),
          contexto);
      return cuentaCorriente;
    });
  }

  public CuentaCorriente consultarConTransferencia(Integer id) throws ServiceException {
    return new ServiceTemplate<CuentaCorriente>().execute(() -> {
      CuentaCorriente cuentaCorriente = dao.consultar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, id.toString()),
          contexto);
      List<Transferencia> transferencias = daoTransferencia.consultarTodos(cuentaCorriente);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.TRANSFERENCIA, id.toString()),
          contexto);
      for (Transferencia transferencia : transferencias) {
        cuentaCorriente.agregarTransferencia(transferencia);
      }
      return cuentaCorriente;
    });
  }

  @Override
  public List<CuentaCorriente> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<CuentaCorriente>>().execute(() -> {
      List<CuentaCorriente> cuentasCorrientes = dao.consultarTodos();
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, "-1"),
          contexto);
      return cuentasCorrientes;
    });
  }

  public List<CuentaCorriente> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<CuentaCorriente>>().execute(() -> {
      List<CuentaCorriente> cuentasCorrientes = dao.consultarTodos(usuario);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, "-1"),
          contexto);
      return cuentasCorrientes;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.ELIMINACION, TipoObjeto.CUENTA_CORRIENTE, id.toString()),
          contexto);
      return null;
    });
  }

  @Override
  public void modificar(CuentaCorriente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(
          new Evento(TipoEvento.MODIFICACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  public void modificarConTransferencia(CuentaCorriente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(
          new Evento(TipoEvento.MODIFICACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      for (Transferencia transferencia : elemento.getTransferencias()) {
        if (daoTransferencia.consultar(transferencia.getId()) == null) {
          daoTransferencia.insertar(elemento, transferencia);
          daoEvento.insertar(
              new Evento(TipoEvento.CREACION, TipoObjeto.TRANSFERENCIA, Integer.toString(transferencia.getId())),
              contexto);
        }
      }
      return null;
    });
  }

  // Reporte
  public List<String> generarReporteMovimientos(CuentaCorriente elemento) throws ServiceException {
    try {
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
      daoEvento.insertar(
          new Evento(TipoEvento.REPORTE, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      return reporte;
    } catch (DAOException e) {
      throw new ServiceException("Fallo al generar reporte de movimientos en: " + this.getClass().getName());
    }
  }
}
