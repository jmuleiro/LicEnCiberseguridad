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
        "INSERT INTO Usuario (cod_tipo_usuario, nombre, apellido, usuario)" +
        "VALUES (\"CLI\", ?, ?, ?)"
      );
      preparedStatement.setString(1, elemento.getNombre());
      preparedStatement.setString(2, elemento.getApellido());
      preparedStatement.setString(3, elemento.getUsuario());
      preparedStatement.executeUpdate();
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  @Override
  public UsuarioCliente consultar(Integer id) throws DAOException {
    return new DAOTemplate<UsuarioCliente>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT nombre, apellido, usuario " + 
        "FROM Usuario " + 
        "WHERE usuario_id = ?"
      );
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new UsuarioCliente(
          rs.getString("nombre"),
          rs.getString("apellido"),
          rs.getString("usuario"),
          id
        );
      }
      return null; // No hubo resultados
    });
  }

  public UsuarioCliente consultar(String usuario) throws DAOException {
    return new DAOTemplate<UsuarioCliente>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT nombre, apellido, usuario_id " + 
        "FROM Usuario " + 
        "WHERE usuario = ?"
      );
      preparedStatement.setString(1, usuario);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new UsuarioCliente(
          rs.getString("nombre"),
          rs.getString("apellido"),
          usuario,
          rs.getInt("usuario_id")
        );
      }
      return null; // No hubo resultados
    });
  }
  
  @Override
  public List<UsuarioCliente> consultarTodos() throws DAOException {
    return new DAOTemplate<List<UsuarioCliente>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT usuario_id, nombre, apellido, usuario " + 
        "FROM Usuario"
      );
      ResultSet rs = preparedStatement.executeQuery();
      List<UsuarioCliente> usuarioClientes = new ArrayList<>();
      while (rs.next()) {
        usuarioClientes.add(new UsuarioCliente(
          rs.getString("nombre"),
          rs.getString("apellido"),
          rs.getString("usuario"),
          rs.getInt("usuario_id")
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
        "SET nombre = ?, apellido = ?, usuario = ? " +
        "WHERE usuario_id = ?"
      );
      preparedStatement.setString(1, elemento.getNombre());
      preparedStatement.setString(2, elemento.getApellido());
      preparedStatement.setString(3, elemento.getUsuario());
      preparedStatement.setInt(4, elemento.getId());
      preparedStatement.executeUpdate();
      return null;
    });
  }
}
