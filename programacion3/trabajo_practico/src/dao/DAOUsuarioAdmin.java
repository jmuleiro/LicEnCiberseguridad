package programacion3.trabajo_practico.src.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;
import programacion3.trabajo_practico.src.helpers.ConnectionHelper;

public class DAOUsuarioAdmin extends DAOBase implements IDAO<UsuarioAdmin> {
  public DAOUsuarioAdmin() throws DAOException {
    super();
  }

  @Override
  public void insertar(UsuarioAdmin elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "INSERT INTO Usuario (cod_tipo_usuario, nombre, apellido)" +
        "VALUES (\"ADM\", ?, ?)"
        );
        preparedStatement.setString(1, elemento.getNombre());
        preparedStatement.setString(2, elemento.getApellido());
        preparedStatement.executeUpdate();
        return null; // Necesario para que no tire error for el tipo Void
    });
  }

  @Override
  public UsuarioAdmin consultar(int id) throws DAOException {
    return new DAOTemplate<UsuarioAdmin>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT T.nombre_tipo, U.nombre, U.apellido " + 
        "FROM Usuario AS U INNER JOIN Tipo_Usuario AS T ON T.cod_tipo_usuario = U.cod_tipo_usuario " + 
        "WHERE U.usuario_id = ?"
      );
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new UsuarioAdmin(
          rs.getString("U.nombre"),
          rs.getString("U.apellido"),
          rs.getString("T.nombre_tipo"),
          id
        );
      }
      return null; // No hubo resultados
    });
  }
  
  @Override
  public List<UsuarioAdmin> consultarTodos() throws DAOException {
    return new DAOTemplate<List<UsuarioAdmin>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT U.usuario_id, T.nombre_tipo, U.nombre, U.apellido " + 
        "FROM Usuario AS U INNER JOIN Tipo_Usuario AS T ON T.cod_tipo_usuario = U.cod_tipo_usuario"
      );
      ResultSet rs = preparedStatement.executeQuery();
      List<UsuarioAdmin> usuarioAdmins = new ArrayList<>();
      while (rs.next()) {
        usuarioAdmins.add(new UsuarioAdmin(
          rs.getString("U.nombre"),
          rs.getString("U.apellido"),
          rs.getString("T.nombre_tipo"),
          rs.getInt("U.usuario_id")
        ));
      }
      return usuarioAdmins;
    });
  }

  @Override
  public void eliminar(int id) throws DAOException {
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
  public void modificar(UsuarioAdmin elemento) throws DAOException {
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
