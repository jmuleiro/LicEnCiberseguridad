package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOCajaAhorro;
import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceCajaAhorro extends ServiceBase<CajaAhorro, Integer> {
  private DAOCajaAhorro dao;
  private DAOTransferencia daoTransferencia;
  private DAOEvento daoEvento;

  public ServiceCajaAhorro(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOCajaAhorro();
      daoTransferencia = new DAOTransferencia();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(CajaAhorro elemento) throws UnsupportedOperationException {
    // todo
    // daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.CAJA_AHORRO,
    // Integer.toString(elemento.getId())),
    // contexto);
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(CajaAhorro elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  @Override
  public CajaAhorro consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<CajaAhorro>().execute(() -> {
      CajaAhorro cajaAhorro = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, Integer.toString(id)),
          contexto);
      return cajaAhorro;
    });

  }

  public CajaAhorro consultarConTransferencia(Integer id) throws ServiceException {
    return new ServiceTemplate<CajaAhorro>().execute(() -> {
      CajaAhorro cajaAhorro = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, Integer.toString(id)),
          contexto);
      List<Transferencia> transferencias = daoTransferencia.consultarTodos(cajaAhorro);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TRANSFERENCIA, Integer.toString(id)),
          contexto);
      for (Transferencia transferencia : transferencias) {
        cajaAhorro.agregarTransferencia(transferencia);
      }
      return cajaAhorro;
    });

  }

  @Override
  public List<CajaAhorro> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<CajaAhorro>>().execute(() -> {
      List<CajaAhorro> cajasAhorro = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, "-1"),
          contexto);
      return cajasAhorro;
    });
  }

  public List<CajaAhorro> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<CajaAhorro>>().execute(() -> {
      List<CajaAhorro> cajasAhorro = dao.consultarTodos(usuario);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, "-1"),
          contexto);
      return cajasAhorro;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(new Evento(TipoEvento.ELIMINACION, TipoObjeto.CAJA_AHORRO, Integer.toString(id)),
          contexto);
      return null;
    });
  }

  @Override
  public void modificar(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento
          .insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
              contexto);
      return null;
    });
  }

  public void modificarConTransferencia(CajaAhorro elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento
          .insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
              contexto);
      for (Transferencia transferencia : elemento.getTransferencias()) {
        if (daoTransferencia.consultar(transferencia.getId()) == null) {
          daoTransferencia.insertar(elemento, transferencia);
          daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.TRANSFERENCIA,
              Integer.toString(transferencia.getId())), contexto);
        }
      }
      return null;
    });
  }

  // Movimientos
  public double extraer(CajaAhorro elemento, String monto) throws ServiceException {
    try {
      if (monto == null || monto.isEmpty()) {
        throw new ServiceException("El monto no puede ser nulo o vacio");
      }

      Double.parseDouble(monto);

      return extraer(elemento, Double.parseDouble(monto));
    } catch (NumberFormatException e) {
      throw new ServiceException("El monto debe ser un numero");
    }
  }

  public double extraer(CajaAhorro elemento, double monto) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      elemento.extraer(monto);
      dao.modificar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.DEBITO, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
    return monto;
  }

  public double depositar(CajaAhorro elemento, String monto) throws ServiceException {
    try {
      if (monto == null || monto.isEmpty()) {
        throw new ServiceException("El monto no puede ser nulo o vacio");
      }

      Double.parseDouble(monto);

      return depositar(elemento, Double.parseDouble(monto));
    } catch (NumberFormatException e) {
      throw new ServiceException("El monto debe ser un numero");
    }
  }

  public double depositar(CajaAhorro elemento, double monto) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      elemento.depositar(monto);
      dao.modificar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.CREDITO, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
    return monto;
  }

  // Reporte
  public List<String> generarReporteMovimientos(CajaAhorro elemento) throws ServiceException {
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
      daoEvento.insertar(new Evento(TipoEvento.REPORTE, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return reporte;
    } catch (DAOException e) {
      throw new ServiceException("Fallo al generar reporte de movimientos en: " + this.getClass().getName());
    }
  }

  // Intereses
  public void aplicarIntereses() throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      List<CajaAhorro> cajasAhorro = dao.consultarTodos();
      for (CajaAhorro cajaAhorro : cajasAhorro) {
        cajaAhorro.aplicarInteres();
        dao.modificar(cajaAhorro);
        daoEvento.insertar(new Evento(TipoEvento.CREDITO, TipoObjeto.CAJA_AHORRO, Integer.toString(cajaAhorro.getId())),
            contexto);
      }
      return null;
    });
  }
}
