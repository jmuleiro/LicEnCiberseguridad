package programacion3.trabajo_practico.src.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class DAOUsuarioCliente extends DAOBase implements IDAO<UsuarioCliente, Integer> {
  public DAOUsuarioCliente() throws DAOException {
    super();
  }

  @Override
  public void insertar(UsuarioCliente elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "INSERT INTO Usuario (cod_tipo_usuario, nombre, apellido)" +
        "VALUES (\"CLI\", ?, ?)"
      );
      preparedStatement.setString(1, elemento.getNombre());
      preparedStatement.setString(2, elemento.getApellido());
      preparedStatement.executeUpdate();
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  @Override
  public UsuarioCliente consultar(Integer id) throws DAOException {
    return new DAOTemplate<UsuarioCliente>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT T.nombre_tipo, U.nombre, U.apellido " + 
        "FROM Usuario AS U INNER JOIN Tipo_Usuario AS T ON T.cod_tipo_usuario = U.cod_tipo_usuario " + 
        "WHERE U.usuario_id = ?"
      );
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new UsuarioCliente(
          rs.getString("U.nombre"),
          rs.getString("U.apellido"),
          id
        );
      }
      return null; // No hubo resultados
    });
  }
  
  @Override
  public List<UsuarioCliente> consultarTodos() throws DAOException {
    return new DAOTemplate<List<UsuarioCliente>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT U.usuario_id, T.nombre_tipo, U.nombre, U.apellido " + 
        "FROM Usuario AS U INNER JOIN Tipo_Usuario AS T ON T.cod_tipo_usuario = U.cod_tipo_usuario"
      );
      ResultSet rs = preparedStatement.executeQuery();
      List<UsuarioCliente> usuarioClientes = new ArrayList<>();
      while (rs.next()) {
        usuarioClientes.add(new UsuarioCliente(
          rs.getString("U.nombre"),
          rs.getString("U.apellido"),
          rs.getInt("U.usuario_id")
        ));
      }
      return usuarioClientes;
    });
  }

  @Override
  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "DELETE FROM Usuario WHERE usuario_id = ?"
      );
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return null;
    });
  }

  @Override
  public void modificar(UsuarioCliente elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "UPDATE Usuario " +
        "SET nombre = ?, apellido = ? " +
        "WHERE usuario_id = ?"
      );
      preparedStatement.setString(1, elemento.getNombre());
      preparedStatement.setString(2, elemento.getApellido());
      preparedStatement.setInt(3, elemento.getId());
      preparedStatement.executeUpdate();
      return null;
    });
  }
}
