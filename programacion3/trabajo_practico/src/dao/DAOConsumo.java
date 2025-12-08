package programacion3.trabajo_practico.src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;

public class DAOConsumo extends DAOBase<Consumo, Integer> {
  public DAOConsumo() throws DAOException {
    super();
  }

  public void insertar(TarjetaCredito tarjeta, Consumo elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Consumo (tarjeta_id, fecha, cantidad, cod_moneda, referencia) " +
              "VALUES (?, ?, ?, ?, ?)");
      preparedStatement.setInt(1, tarjeta.getId());
      preparedStatement.setDate(2, Date.valueOf(elemento.getFecha()));
      preparedStatement.setDouble(3, elemento.getCantidad());
      preparedStatement.setString(4, elemento.getMoneda().getCodigo());
      preparedStatement.setString(5, elemento.getReferencia());
      preparedStatement.executeUpdate();
      return null;
    });
  }

  @Override
  public Consumo consultar(Integer id) throws DAOException {
    return new DAOTemplate<Consumo>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT C.cantidad, C.fecha, M.cod_moneda, M.nombre_moneda, C.referencia " +
              "FROM Consumo AS C " +
              "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
              "WHERE C.consumo_id = ?");
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Consumo(
            rs.getDouble("C.cantidad"),
            rs.getDate("C.fecha").toLocalDate(),
            new Moneda(rs.getString("M.cod_moneda"), rs.getString("M.nombre_moneda")),
            rs.getString("C.referencia"));
      }
      return null;
    });
  }

  public List<Consumo> consultarTodos(TarjetaCredito tarjeta) throws DAOException {
    return new DAOTemplate<List<Consumo>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT C.consumo_id, C.cantidad, C.fecha, M.cod_moneda, M.nombre_moneda, C.referencia " +
              "FROM Consumo AS C " +
              "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
              "WHERE C.tarjeta_id = ?");
      preparedStatement.setInt(1, tarjeta.getId());
      ResultSet rs = preparedStatement.executeQuery();
      List<Consumo> consumos = new ArrayList<>();
      while (rs.next()) {
        consumos.add(new Consumo(
            rs.getDouble("C.cantidad"),
            rs.getDate("C.fecha").toLocalDate(),
            new Moneda(rs.getString("M.cod_moneda"), rs.getString("M.nombre_moneda")),
            rs.getInt("C.consumo_id"),
            rs.getString("C.referencia")));
      }
      return consumos;
    });
  }

  @Override
  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "DELETE FROM Consumo WHERE consumo_id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return null;
    });
  }

}
