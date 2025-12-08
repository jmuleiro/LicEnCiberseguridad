package programacion3.trabajo_practico.src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.entidades.Moneda;

public class DAOCajaAhorro extends DAOBase<CajaAhorro, Integer> {
  public DAOCajaAhorro() throws DAOException {
    super();
  }

  @Override
  public void insertar(CajaAhorro elemento) throws UnsupportedOperationException {
    // Se deja este método implementado para compatibilidad
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(CajaAhorro elemento, UsuarioCliente usuario) throws DAOException {
    insertar(elemento, usuario, new Moneda("ARS", "Peso Argentino"));
  }

  public void insertar(CajaAhorro elemento, UsuarioCliente usuario, Moneda moneda) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Cuenta (cod_tipo_cuenta, usuario_id, cod_moneda, alias, cbu, limite_giro, porcentaje_interes, saldo)"
              +
              "VALUES (\"SAV\", ?, ?, ?, ?, 0, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, usuario.getId());
      preparedStatement.setString(2, moneda.getCodigo());
      preparedStatement.setString(3, elemento.getAlias());
      preparedStatement.setString(4, elemento.getCbu());
      preparedStatement.setDouble(5, elemento.getPorcentajeInteres());
      preparedStatement.setDouble(6, elemento.getSaldo());
      preparedStatement.executeUpdate();
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        elemento.setId(generatedKeys.getInt(1));
      }
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  @Override
  public CajaAhorro consultar(Integer id) throws DAOException {
    return new DAOTemplate<CajaAhorro>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT M.cod_moneda, M.nombre_moneda, C.alias, C.cbu, C.porcentaje_interes, C.saldo " +
              "FROM Cuenta AS C " +
              "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
              "WHERE C.cuenta_id = ?");
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new CajaAhorro(
            new Moneda(
                rs.getString("M.cod_moneda"),
                rs.getString("M.nombre_moneda")),
            rs.getString("C.alias"),
            rs.getString("C.cbu"),
            rs.getDouble("C.porcentaje_interes"),
            id,
            rs.getDouble("C.saldo"));
      }
      return null; // No hubo resultados
    });
  }

  @Override
  public List<CajaAhorro> consultarTodos() throws DAOException {
    return new DAOTemplate<List<CajaAhorro>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT M.cod_moneda, M.nombre_moneda, C.alias, C.cbu, C.porcentaje_interes, C.saldo, C.cuenta_id " +
              "FROM Cuenta AS C " +
              "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
              "WHERE C.cod_tipo_cuenta = \"SAV\"");
      ResultSet rs = preparedStatement.executeQuery();
      List<CajaAhorro> cajasAhorro = new ArrayList<>();
      while (rs.next()) {
        cajasAhorro.add(new CajaAhorro(
            new Moneda(
                rs.getString("M.cod_moneda"),
                rs.getString("M.nombre_moneda")),
            rs.getString("C.alias"),
            rs.getString("C.cbu"),
            rs.getDouble("C.porcentaje_interes"),
            rs.getInt("C.cuenta_id"),
            rs.getDouble("C.saldo")));
      }
      return cajasAhorro;
    });
  }

  public List<CajaAhorro> consultarTodos(UsuarioCliente usuario) throws DAOException {
    return new DAOTemplate<List<CajaAhorro>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT M.cod_moneda, M.nombre_moneda, C.alias, C.cbu, C.porcentaje_interes, C.saldo, C.cuenta_id " +
              "FROM Cuenta AS C " +
              "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
              "WHERE C.usuario_id = ? AND C.cod_tipo_cuenta = \"SAV\"");
      preparedStatement.setInt(1, usuario.getId());
      ResultSet rs = preparedStatement.executeQuery();
      List<CajaAhorro> cajasAhorro = new ArrayList<>();
      while (rs.next()) {
        cajasAhorro.add(new CajaAhorro(
            new Moneda(
                rs.getString("M.cod_moneda"),
                rs.getString("M.nombre_moneda")),
            rs.getString("C.alias"),
            rs.getString("C.cbu"),
            rs.getDouble("C.porcentaje_interes"),
            rs.getInt("C.cuenta_id"),
            rs.getDouble("C.saldo")));
      }
      return cajasAhorro;
    });
  }

  @Override
  public void modificar(CajaAhorro elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "UPDATE Cuenta " +
              "SET alias = ?, porcentaje_interes = ?, saldo = ? " +
              "WHERE cuenta_id = ?");
      preparedStatement.setString(1, elemento.getAlias());
      preparedStatement.setDouble(2, elemento.getPorcentajeInteres());
      preparedStatement.setDouble(3, elemento.getSaldo());
      preparedStatement.setInt(4, elemento.getId());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  @Override
  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "DELETE FROM Cuenta WHERE cuenta_id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return null;
    });
  }
}
