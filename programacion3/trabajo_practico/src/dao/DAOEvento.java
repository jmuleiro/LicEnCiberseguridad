package programacion3.trabajo_practico.src.dao;

import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DAOEvento extends DAOBase<Evento, Integer> {
  public DAOEvento() throws DAOException {
    super();
  }

  @Override
  public void insertar(Evento elemento) throws DAOException {
    insertar(elemento, new HashMap<>());
  }

  public void insertar(Evento elemento, Map<String, String> contexto) throws DAOException {
    if (contexto.get("id_usuario") != null) {
      insertar(elemento, Integer.parseInt(contexto.get("id_usuario")));
      return;
    }

    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Evento (cod_evento, cod_objeto, objeto_id, exitoso) VALUES (?, ?, ?, ?)");
      preparedStatement.setString(1, elemento.getTipo().getCodigo());
      preparedStatement.setString(2, elemento.getObjeto().getCodigo());
      preparedStatement.setString(3, elemento.getIdObjeto());
      preparedStatement.setBoolean(4, elemento.isExitoso());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  public void insertar(Evento elemento, int usuarioId) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Evento (cod_evento, cod_objeto, objeto_id, exitoso) VALUES (?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, elemento.getTipo().getCodigo());
      preparedStatement.setString(2, elemento.getObjeto().getCodigo());
      preparedStatement.setString(3, elemento.getIdObjeto());
      preparedStatement.setBoolean(4, elemento.isExitoso());
      preparedStatement.executeUpdate();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        int eventoId = rs.getInt(1);
        PreparedStatement preparedStatement2 = conn
            .prepare("INSERT INTO Evento_Usuario (usuario_id, evento_id) VALUES (?, ?)");
        preparedStatement2.setInt(1, usuarioId);
        preparedStatement2.setInt(2, eventoId);
        preparedStatement2.executeUpdate();
      }
      return null;
    });
  }

  public List<Evento> consultarTodos() throws DAOException {
    return new DAOTemplate<List<Evento>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT evento_id, fecha_hora, cod_evento, cod_objeto, objeto_id, exitoso FROM Evento");
      ResultSet resultSet = preparedStatement.executeQuery();
      List<Evento> eventos = new ArrayList<>();
      while (resultSet.next()) {
        Evento evento = new Evento(
            TipoEvento.fromCodigo(resultSet.getString("cod_evento")),
            TipoObjeto.fromCodigo(resultSet.getString("cod_objeto")),
            resultSet.getString("objeto_id"),
            resultSet.getTimestamp("fecha_hora").toLocalDateTime(),
            resultSet.getInt("evento_id"),
            resultSet.getBoolean("exitoso"));
        eventos.add(evento);
      }
      return eventos;
    });
  }
}
