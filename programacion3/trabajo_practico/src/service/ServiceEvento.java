package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOEvento;

public class ServiceEvento extends ServiceBase<Evento, Integer> {
  DAOEvento dao;

  public ServiceEvento(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOEvento();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public Evento consultar(Integer id) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void insertar(Evento entidad) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public List<Evento> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<Evento>>().execute(() -> {
      List<Evento> eventos = dao.consultarTodos();
      dao.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.EVENTO, "-1"),
          contexto);
      return eventos;
    });
  }

  @Override
  public void modificar(Evento entidad) throws ServiceException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  // Reporte
  public List<String> generarReporte() throws ServiceException {
    try {
      List<String> reporte = new ArrayList<String>();
      reporte.add("id,fechaHora,tipo,objeto,idObjeto\n");
      for (Evento evento : dao.consultarTodos()) {
        reporte.add(evento.getId() + "," + evento.getFechaHora() + "," + evento.getTipo() + "," + evento.getObjeto()
            + "," + evento.getIdObjeto() + "\n");
      }
      dao.insertar(new Evento(TipoEvento.REPORTE, TipoObjeto.EVENTO, "-1"),
          contexto);
      return reporte;
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al generar reporte en: " + this.getClass().getName());
    }
  }
}
