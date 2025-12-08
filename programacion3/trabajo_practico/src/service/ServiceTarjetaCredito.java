package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;

import java.time.DateTimeException;
import java.time.LocalDate;

import programacion3.trabajo_practico.src.dao.DAOTarjetaCredito;
import programacion3.trabajo_practico.src.dao.DAOConsumo;
import programacion3.trabajo_practico.src.dao.DAOMoneda;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceTarjetaCredito extends ServiceBase<TarjetaCredito, Integer> {
  private DAOTarjetaCredito dao;
  private DAOConsumo daoConsumo;
  private DAOMoneda daoMoneda;

  public ServiceTarjetaCredito() throws ServiceException {
    try {
      dao = new DAOTarjetaCredito();
      daoConsumo = new DAOConsumo();
      daoMoneda = new DAOMoneda();
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
      return null;
    });
  }

  @Override
  public TarjetaCredito consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<TarjetaCredito>().execute(() -> {
      return dao.consultar(id);
    });
  }

  public TarjetaCredito consultarConConsumo(Integer id) throws ServiceException {
    return new ServiceTemplate<TarjetaCredito>().execute(() -> {
      TarjetaCredito resultado = dao.consultar(id);
      List<Consumo> consumos = daoConsumo.consultarTodos(resultado);
      for (Consumo consumo : consumos) {
        resultado.agregarConsumo(consumo);
      }
      return resultado;
    });
  }

  @Override
  public List<TarjetaCredito> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      return dao.consultarTodos();
    });
  }

  // todo: esta logica tendria que hacerse en el ServiceUsuarioCliente
  public List<TarjetaCredito> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      return dao.consultarTodos(usuario);
    });
  }

  // todo: same here
  public List<TarjetaCredito> consultarTodosConConsumo(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<TarjetaCredito>>().execute(() -> {
      List<TarjetaCredito> tarjetas = dao.consultarTodos(usuario);
      for (TarjetaCredito tarjeta : tarjetas) {
        List<Consumo> consumos = daoConsumo.consultarTodos(tarjeta);
        for (Consumo consumo : consumos) {
          tarjeta.agregarConsumo(consumo);
        }
      }
      return tarjetas;
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
  public void modificar(TarjetaCredito elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      return null;
    });
  }

  public void modificarConConsumo(TarjetaCredito elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      for (Consumo consumo : elemento.getConsumos()) {
        if (daoConsumo.consultar(consumo.getId()) == null) {
          daoConsumo.insertar(elemento, consumo);
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

  // Reporte
  public List<String> generarReporteConsumos(TarjetaCredito tarjeta) {
    List<String> reporte = new ArrayList<>();
    reporte.add("id,fecha,cantidad,moneda,referencia\n");
    for (Consumo consumo : tarjeta.getConsumos()) {
      reporte.add(consumo.toCsv() + "\n");
    }
    return reporte;
  }
}
