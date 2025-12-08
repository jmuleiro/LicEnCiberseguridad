package programacion3.trabajo_practico.src.dao;

import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.Usuario;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class DAOEvento extends DAOBase<Evento, Integer> {
  public DAOEvento() throws DAOException {
    super();
  }

  @Override
  public void insertar(Evento elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Evento (cod_evento, cod_objeto, objeto_id) VALUES (?, ?, ?)");
      preparedStatement.setString(1, elemento.getTipo().name());
      preparedStatement.setString(2, elemento.getObjeto().name());
      preparedStatement.setString(3, elemento.getIdObjeto());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  public void insertar(Usuario usuario, Evento elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Evento (cod_evento, cod_objeto, objeto_id) VALUES (?, ?, ?)");
      preparedStatement.setString(1, elemento.getTipo().name());
      preparedStatement.setString(2, elemento.getObjeto().name());
      preparedStatement.setString(3, elemento.getIdObjeto());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  public List<Evento> consultarTodos() throws DAOException {
    return new DAOTemplate<List<Evento>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT evento_id, usuario_id, fecha_hora, cod_evento, cod_objeto, objeto_id FROM Evento");
      ResultSet resultSet = preparedStatement.executeQuery();
      List<Evento> eventos = new ArrayList<>();
      while (resultSet.next()) {
        Evento evento = new Evento(
            TipoEvento.valueOf(resultSet.getString("cod_evento")),
            TipoObjeto.valueOf(resultSet.getString("cod_objeto")),
            resultSet.getString("objeto_id"),
            resultSet.getTimestamp("fecha_hora").toLocalDateTime(),
            resultSet.getInt("evento_id"));
        eventos.add(evento);
      }
      return eventos;
    });
  }
}
