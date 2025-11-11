package programacion3.trabajo_practico.src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.Transferencia;

public class DAOTransferencia extends DAOBase<Transferencia, Integer> {
  public DAOTransferencia() throws DAOException {
    super();
  }

  @Override
  public Transferencia consultar(Integer id) throws DAOException {
    return new DAOTemplate<Transferencia>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "SELECT T.monto, T.cod_moneda, T.concepto, M.nombre_moneda " + 
        "FROM Transferencia AS T " +
        "INNER JOIN Moneda AS M ON T.cod_moneda = M.cod_moneda " +
        "WHERE transferencia_id = ?"
      );
      preparedStatement.setInt(1, id.intValue());
      preparedStatement.executeQuery();
      ResultSet rs = preparedStatement.getResultSet();
      if (rs.next()) {
        return new Transferencia(
          rs.getDouble("monto"),
          new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
          rs.getString("concepto"),
          id
        );
      }

      return null; // No hubo resultados
    });
  }

  public void insertar(Cuenta cuentaOrigen, Cuenta cuentaDestino, Transferencia elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      // Insertar la transferencia
      PreparedStatement preparedStatement = conn.prepare(
        "INSERT INTO Transferencia (monto, cod_moneda, concepto) " +
        "VALUES (?, ?, ?) "
      );
      preparedStatement.setDouble(1, elemento.getMonto());
      preparedStatement.setString(2, elemento.getMoneda().getCodigo());
      preparedStatement.setString(3, elemento.getConcepto());
      preparedStatement.executeUpdate();

      // Traer ID transferencia
      ResultSet rs = preparedStatement.getGeneratedKeys();
      int id = -1;
      if (rs.next()) {
        id = rs.getInt(1);
      } else {
        throw new RuntimeException("No se pudo obtener el ID de la transferencia");
      }

      String movimientoInsertSql = "INSERT INTO Movimiento (transferencia_id, cuenta_id, entrante) " +
        "VALUES (?, ?, ?)";
      
      // Insertar el movimiento entrante (relación)
      // entrante = true -> Crédito
      preparedStatement = conn.prepare(movimientoInsertSql);
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, cuentaDestino.getId());
      preparedStatement.setBoolean(3, true);
      preparedStatement.executeUpdate();

      // Insertar el movimiento saliente (relación)
      // entrante = false -> Débito
      preparedStatement = conn.prepare(movimientoInsertSql);
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, cuentaOrigen.getId());
      preparedStatement.setBoolean(3, false);
      preparedStatement.executeUpdate();

      return null;
    });
  }
}
