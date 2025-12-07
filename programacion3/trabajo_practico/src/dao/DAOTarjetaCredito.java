package programacion3.trabajo_practico.src.dao;

import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class DAOTarjetaCredito extends DAOBase<TarjetaCredito, Integer> {
  public DAOTarjetaCredito() throws DAOException {
    super();
  }

  @Override
  public void insertar(TarjetaCredito elemento) throws UnsupportedOperationException {
    // Se deja este método implementado para compatibilidad
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(TarjetaCredito elemento, UsuarioCliente usuario) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Tarjeta (usuario_id, cod_tipo_tarjeta, limite, numero, fecha_vencimiento, cvc)" +
              "VALUES (?, \"CRE\", ?, ?, ?, ?)");
      preparedStatement.setInt(1, usuario.getId());
      preparedStatement.setDouble(2, elemento.getLimite());
      preparedStatement.setString(3, elemento.getNumero());
      preparedStatement.setDate(4, Date.valueOf(elemento.getFechaVencimiento()));
      preparedStatement.setInt(5, elemento.getCvc());
      preparedStatement.executeUpdate();
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  @Override
  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "DELETE FROM Tarjeta WHERE tarjeta_id = ?");
      preparedStatement.setInt(1, id.intValue());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  @Override
  public void modificar(TarjetaCredito elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      // El único campo que tiene sentido que sea actualizable es el límite
      PreparedStatement preparedStatement = conn.prepare(
          "UPDATE Tarjeta " +
              "SET limite = ? " +
              "WHERE tarjeta_id = ?");
      preparedStatement.setDouble(1, elemento.getLimite());
      preparedStatement.setInt(2, elemento.getId());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  @Override
  public TarjetaCredito consultar(Integer id) throws DAOException {
    return new DAOTemplate<TarjetaCredito>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT numero, fecha_vencimiento, cvc, limite, tarjeta_id " +
              "FROM Tarjeta " +
              "WHERE tarjeta_id = ?");
      preparedStatement.setInt(1, id.intValue());
      ResultSet rs = preparedStatement.executeQuery();
      if (!(rs.next()))
        return null; // No hubo resultados

      return new TarjetaCredito(
          rs.getString("numero"),
          rs.getDate("fecha_vencimiento").toLocalDate(),
          rs.getInt("cvc"),
          rs.getDouble("limite"),
          rs.getInt("tarjeta_id"));
    });
  }

  @Override
  public List<TarjetaCredito> consultarTodos() throws DAOException {
    return new DAOTemplate<List<TarjetaCredito>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT numero, fecha_vencimiento, cvc, limite, tarjeta_id " +
              "FROM Tarjeta");
      ResultSet rs = preparedStatement.executeQuery();
      List<TarjetaCredito> tarjetas = new ArrayList<>();
      while (rs.next()) {
        tarjetas.add(new TarjetaCredito(
            rs.getString("numero"),
            rs.getDate("fecha_vencimiento").toLocalDate(),
            rs.getInt("cvc"),
            rs.getDouble("limite"),
            rs.getInt("tarjeta_id")));
      }
      return tarjetas;
    });
  }

  public List<TarjetaCredito> consultarTodos(UsuarioCliente usuario) throws DAOException {
    return new DAOTemplate<List<TarjetaCredito>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT numero, fecha_vencimiento, cvc, limite, tarjeta_id " +
              "FROM Tarjeta " +
              "WHERE usuario_id = ?");
      preparedStatement.setInt(1, usuario.getId());
      ResultSet rs = preparedStatement.executeQuery();
      List<TarjetaCredito> tarjetas = new ArrayList<>();
      while (rs.next()) {
        tarjetas.add(new TarjetaCredito(
            rs.getString("numero"),
            rs.getDate("fecha_vencimiento").toLocalDate(),
            rs.getInt("cvc"),
            rs.getDouble("limite"),
            rs.getInt("tarjeta_id")));
      }
      return tarjetas;
    });
  }
}
