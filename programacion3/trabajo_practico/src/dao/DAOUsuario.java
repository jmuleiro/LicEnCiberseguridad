package programacion3.trabajo_practico.src.dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import programacion3.trabajo_practico.src.entidades.Usuario;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class DAOUsuario extends DAOBaseS<Usuario, Integer> {
  public DAOUsuario() throws DAOException {
    super();
  }

  @Override
  public Usuario consultar(Integer id) throws DAOException {
    return new DAOTemplate<Usuario>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT nombre, apellido, usuario, cod_tipo_usuario " +
              "FROM Usuario " +
              "WHERE usuario_id = ?");
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        if (rs.getString("cod_tipo_usuario").equals("ADM")) {
          return new UsuarioAdmin(
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("usuario"),
            "Administrador",
            rs.getInt("usuario_id")
          );
        } else {
          return new UsuarioCliente(
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("usuario"),
            id);
        }
      }
      return null; // No hubo resultados
    });
  }

  public Usuario consultar(String usuario) throws DAOException {
    return new DAOTemplate<Usuario>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT nombre, apellido, usuario_id, cod_tipo_usuario " +
              "FROM Usuario " +
              "WHERE usuario = ?");
      preparedStatement.setString(1, usuario);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        if (rs.getString("cod_tipo_usuario").equals("ADM")) {
          return new UsuarioAdmin(
              rs.getString("nombre"),
              rs.getString("apellido"),
              usuario,
              "Administrador",
              rs.getInt("usuario_id")
          );
        } else {
          return new UsuarioCliente(
              rs.getString("nombre"),
              rs.getString("apellido"),
              usuario,
              rs.getInt("usuario_id"));
        }
      }
      return null; // No hubo resultados
    });
  }

  public List<Usuario> consultarTodos() throws DAOException {
    return new DAOTemplate<List<Usuario>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT usuario_id, nombre, apellido, usuario, cod_tipo_usuario " +
              "FROM Usuario");
      ResultSet rs = preparedStatement.executeQuery();
      List<Usuario> usuarios = new ArrayList<>();
      while (rs.next()) {
        if (rs.getString("cod_tipo_usuario").equals("ADM")) {
          usuarios.add(new UsuarioAdmin(
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("usuario"),
            "Administrador",
            rs.getInt("usuario_id")));
        } else {
          usuarios.add(new UsuarioCliente(
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("usuario"),
            rs.getInt("usuario_id")));
        }
      }
      return usuarios;
    });
  }

  public void insertar(Usuario elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Usuario (cod_tipo_usuario, nombre, apellido, usuario)" +
              "VALUES (?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);

      if (elemento instanceof UsuarioAdmin) {
        preparedStatement.setString(1, "ADM");
      } else {
        preparedStatement.setString(1, "CLI");
      }

      preparedStatement.setString(2, elemento.getNombre());
      preparedStatement.setString(3, elemento.getApellido());
      preparedStatement.setString(4, elemento.getUsuario());
      preparedStatement.executeUpdate();
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        elemento.setId(generatedKeys.getInt(1));
      }
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "DELETE FROM Usuario WHERE usuario_id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return null;
    });
  }

  public void modificar(Usuario elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "UPDATE Usuario " +
              "SET nombre = ?, apellido = ?, usuario = ? " +
              "WHERE usuario_id = ?");
      preparedStatement.setString(1, elemento.getNombre());
      preparedStatement.setString(2, elemento.getApellido());
      preparedStatement.setString(3, elemento.getUsuario());
      preparedStatement.setInt(4, elemento.getId());
      preparedStatement.executeUpdate();
      return null;
    });
  }
}
