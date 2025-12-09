package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import java.time.DateTimeException;
import java.time.LocalDate;

import programacion3.trabajo_practico.src.dao.DAOTarjetaCredito;
import programacion3.trabajo_practico.src.dao.DAOConsumo;
import programacion3.trabajo_practico.src.dao.DAOMoneda;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceTarjetaCredito extends ServiceBase<TarjetaCredito, Integer> {
  private DAOTarjetaCredito dao;
  private DAOConsumo daoConsumo;
  private DAOMoneda daoMoneda;
  private DAOEvento daoEvento;

  public ServiceTarjetaCredito(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOTarjetaCredito();
      daoConsumo = new DAOConsumo();
      daoMoneda = new DAOMoneda();
      daoEvento = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(TarjetaCredito elemento) throws ServiceException {
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(TarjetaCredito elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      daoEvento.insertar(
          new Evento(TipoEvento.CREACION, TipoObjeto.TARJETA_CREDITO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  @Override
  public TarjetaCredito consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<TarjetaCredito>().execute(() -> {
      TarjetaCredito resultado = dao.consultar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, Integer.toString(id)),
          contexto);
      return resultado;
    });
  }

  public TarjetaCredito consultarConConsumo(Integer id) throws ServiceException {
    return new ServiceTemplate<TarjetaCredito>().execute(() -> {
      TarjetaCredito resultado = dao.consultar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, Integer.toString(id)),
          contexto);
      List<Consumo> consumos = daoConsumo.consultarTodos(resultado);
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.CONSUMO, Integer.toString(id)),
          contexto);
      for (Consumo consumo : consumos) {
        resultado.agregarConsumo(consumo);
      }
      return resultado;
    });
  }

  @Override
  public List<TarjetaCredito> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      List<TarjetaCredito> resultado = dao.consultarTodos();
      daoEvento.insertar(
          new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, "-1"),
          contexto);
      return resultado;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(
          new Evento(TipoEvento.ELIMINACION, TipoObjeto.TARJETA_CREDITO, Integer.toString(id)),
          contexto);
      return null;
    });
  }

  @Override
  public void modificar(TarjetaCredito elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(
          new Evento(TipoEvento.MODIFICACION, TipoObjeto.TARJETA_CREDITO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  public void modificarConConsumo(TarjetaCredito elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(
          new Evento(TipoEvento.MODIFICACION, TipoObjeto.TARJETA_CREDITO, Integer.toString(elemento.getId())),
          contexto);
      for (Consumo consumo : elemento.getConsumos()) {
        if (daoConsumo.consultar(consumo.getId()) == null) {
          daoConsumo.insertar(elemento, consumo);
          daoEvento.insertar(
              new Evento(TipoEvento.CREACION, TipoObjeto.CONSUMO, Integer.toString(consumo.getId())),
              contexto);
        }
      }
      return null;
    });
  }

  // Validaciones
  public Consumo validarConsumo(String cantidad, String fecha, String monedaString, String referencia)
      throws ServiceException {
    if (cantidad.isEmpty() || fecha.isEmpty() || monedaString.isEmpty() || referencia.isEmpty()) {
      throw new ServiceException("Todos los campos son obligatorios");
    }

    try {
      Double.parseDouble(cantidad);
    } catch (NumberFormatException e) {
      throw new ServiceException("La cantidad debe ser un número");
    }

    try {
      LocalDate.parse(fecha);
    } catch (DateTimeException e) {
      throw new ServiceException("La fecha debe ser una fecha válida");
    }

    Moneda moneda = null;
    try {
      moneda = daoMoneda.consultar(monedaString);
    } catch (DAOException e) {
      throw new ServiceException("La moneda seleccionada no es válida");
    }

    if (moneda == null) {
      throw new ServiceException("La moneda seleccionada no es válida");
    }

    return new Consumo(Double.parseDouble(cantidad), LocalDate.parse(fecha), moneda, referencia);
  }

  // Funcionalidades
  public void agregarConsumo(TarjetaCredito tarjeta, String cantidad, String fecha, String monedaString,
      String referencia)
      throws ServiceException {
    Consumo consumo = validarConsumo(cantidad, fecha, monedaString, referencia);
    tarjeta.agregarConsumo(consumo);
    this.modificarConConsumo(tarjeta);
  }

  public void modificarTarjeta(TarjetaCredito tarjeta, String limiteString) throws ServiceException {
    if (limiteString.isEmpty()) {
      JOptionPane.showMessageDialog(null, "El límite es obligatorio", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (Double.valueOf(limiteString) == tarjeta.getLimite()) {
      JOptionPane.showMessageDialog(null, "El límite no ha cambiado", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      Double.parseDouble(limiteString);
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(null, "El límite debe ser un número", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (Double.valueOf(limiteString) < 1000) {
      JOptionPane.showMessageDialog(null, "El límite debe ser mayor a 1000", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    tarjeta.setLimite(Double.parseDouble(limiteString));
    this.modificar(tarjeta);
  }

  // Reporte
  public List<String> generarReporteConsumos(TarjetaCredito tarjeta) throws ServiceException {
    List<String> reporte = new ArrayList<>();
    reporte.add("id,fecha,cantidad,moneda,referencia\n");
    for (Consumo consumo : tarjeta.getConsumos()) {
      reporte.add(consumo.toCsv() + "\n");
    }
    try {
      daoEvento.insertar(
          new Evento(TipoEvento.REPORTE, TipoObjeto.TARJETA_CREDITO, Integer.toString(tarjeta.getId())),
          contexto);
    } catch (DAOException e) {
      throw new ServiceException("Fallo al generar reporte de movimientos en: " + this.getClass().getName());
    }
    return reporte;
  }
}
