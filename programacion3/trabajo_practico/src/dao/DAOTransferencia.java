package programacion3.trabajo_practico.src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.TipoOperacion;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;

public class DAOTransferencia extends DAOBase<Transferencia, Integer> {
  public DAOTransferencia() throws DAOException {
    super();
  }

  @Override
  public Transferencia consultar(Integer id) throws DAOException {
    return new DAOTemplate<Transferencia>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT T.fecha, T.monto, T.cod_moneda, T.concepto, M.nombre_moneda, MV.cuenta_id, MV.entrante, T.cod_tipo_operacion "
              +
              "FROM Transferencia AS T " +
              "INNER JOIN Moneda AS M ON T.cod_moneda = M.cod_moneda " +
              "INNER JOIN Movimiento AS MV ON T.transferencia_id = MV.transferencia_id " +
              "WHERE T.transferencia_id = ?");
      preparedStatement.setInt(1, id.intValue());
      preparedStatement.executeQuery();
      ResultSet rs = preparedStatement.getResultSet();
      if (rs.next()) {
        PreparedStatement preparedStatement2 = conn.prepare(
            "SELECT C.cod_tipo_cuenta, M.cod_moneda, M.nombre_moneda, C.alias, C.cbu, C.limite_giro, C.porcentaje_interes, C.saldo "
                +
                "FROM Cuenta AS C " +
                "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
                "WHERE C.cuenta_id = ?");
        preparedStatement2.setInt(1, Integer.parseInt(rs.getString("MV.cuenta_id")));
        preparedStatement2.executeQuery();
        ResultSet rs2 = preparedStatement2.getResultSet();
        if (rs2.next()) {
          if (rs.getString("C.cod_tipo_cuenta").equals("SAV")) {
            return new Transferencia(
                rs.getDate("T.fecha").toLocalDate(),
                rs.getDouble("T.monto"),
                new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
                rs.getString("T.concepto"),
                new CajaAhorro(
                    new Moneda(rs.getString("M.cod_moneda"), rs.getString("M.nombre_moneda")),
                    rs.getString("C.alias"),
                    rs.getString("C.cbu"),
                    rs.getDouble("C.porcentaje_interes"),
                    Integer.parseInt(rs.getString("MV.cuenta_id")),
                    rs.getDouble("C.saldo")),
                TipoOperacion.fromCodigo(rs.getString("T.cod_tipo_operacion")),
                rs.getBoolean("MV.entrante"),
                id);
          } else {
            return new Transferencia(
                rs.getDate("T.fecha").toLocalDate(),
                rs.getDouble("T.monto"),
                new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
                rs.getString("T.concepto"),
                new CuentaCorriente(
                    new Moneda(rs.getString("M.cod_moneda"), rs.getString("M.nombre_moneda")),
                    rs.getString("C.alias"),
                    rs.getString("C.cbu"),
                    rs.getDouble("C.limite_giro"),
                    Integer.parseInt(rs.getString("MV.cuenta_id")),
                    rs.getDouble("C.saldo")),
                TipoOperacion.fromCodigo(rs.getString("T.cod_tipo_operacion")),
                rs.getBoolean("MV.entrante"),
                id);
          }
        }
      }

      return null; // No hubo resultados
    });
  }

  public List<Transferencia> consultarTodos(Cuenta cuenta) throws DAOException {
    return new DAOTemplate<List<Transferencia>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT T.fecha, T.monto, T.cod_moneda, T.concepto, M.nombre_moneda, T.transferencia_id, MV.entrante, T.cod_tipo_operacion "
              +
              "FROM Transferencia AS T " +
              "INNER JOIN Moneda AS M ON T.cod_moneda = M.cod_moneda " +
              "INNER JOIN Movimiento AS MV ON T.transferencia_id = MV.transferencia_id " +
              "WHERE MV.cuenta_id = ?");
      preparedStatement.setInt(1, cuenta.getId());
      preparedStatement.executeQuery();
      ResultSet rs = preparedStatement.getResultSet();
      List<Transferencia> transferencias = new ArrayList<>();
      while (rs.next()) {
        TipoOperacion tipoOperacion = TipoOperacion.fromCodigo(rs.getString("T.cod_tipo_operacion"));

        if (tipoOperacion == TipoOperacion.TRANSFERENCIA) {
          // Traer info del movimiento
          PreparedStatement preparedStatement2 = conn.prepare(
              "SELECT MV.cuenta_id " +
                  "FROM Movimiento AS MV " +
                  "WHERE MV.transferencia_id = ? " +
                  "AND MV.entrante = ?");
          preparedStatement2.setInt(1, rs.getInt("T.transferencia_id"));
          preparedStatement2.setBoolean(2, !rs.getBoolean("MV.entrante"));
          preparedStatement2.executeQuery();
          ResultSet rs2 = preparedStatement2.getResultSet();

          int cuentaTerceroId = -1;
          if (rs2.next())
            cuentaTerceroId = rs2.getInt("MV.cuenta_id");
          else
            continue;

          // Traer info de la cuenta de terceros
          PreparedStatement preparedStatement3 = conn.prepare(
              "SELECT C.cod_tipo_cuenta, M.cod_moneda, M.nombre_moneda, C.alias, C.cbu, C.limite_giro, C.porcentaje_interes, C.saldo "
                  +
                  "FROM Cuenta AS C " +
                  "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " +
                  "WHERE C.cuenta_id = ?");
          preparedStatement3.setInt(1, cuentaTerceroId);
          preparedStatement3.executeQuery();
          ResultSet rs3 = preparedStatement3.getResultSet();

          if (rs3.next()) {
            if (rs3.getString("C.cod_tipo_cuenta").equals("SAV")) {
              transferencias.add(new Transferencia(
                  rs.getDate("T.fecha").toLocalDate(),
                  rs.getDouble("T.monto"),
                  new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
                  rs.getString("T.concepto"),
                  new CajaAhorro(
                      new Moneda(rs3.getString("M.cod_moneda"), rs3.getString("M.nombre_moneda")),
                      rs3.getString("C.alias"),
                      rs3.getString("C.cbu"),
                      rs3.getDouble("C.porcentaje_interes"),
                      cuentaTerceroId,
                      rs3.getDouble("C.saldo")),
                  rs.getBoolean("MV.entrante"),
                  Integer.parseInt(rs.getString("T.transferencia_id"))));
            } else {
              transferencias.add(new Transferencia(
                  rs.getDate("T.fecha").toLocalDate(),
                  rs.getDouble("T.monto"),
                  new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
                  rs.getString("T.concepto"),
                  new CuentaCorriente(
                      new Moneda(rs3.getString("M.cod_moneda"), rs3.getString("M.nombre_moneda")),
                      rs3.getString("C.alias"),
                      rs3.getString("C.cbu"),
                      rs3.getDouble("C.limite_giro"),
                      cuentaTerceroId,
                      rs3.getDouble("C.saldo")),
                  rs.getBoolean("MV.entrante"),
                  Integer.parseInt(rs.getString("T.transferencia_id"))));
            }
          }
        } else {
          transferencias.add(new Transferencia(
              rs.getDate("T.fecha").toLocalDate(),
              rs.getDouble("T.monto"),
              new Moneda(rs.getString("T.cod_moneda"), rs.getString("M.nombre_moneda")),
              rs.getString("T.concepto"),
              cuenta,
              tipoOperacion,
              rs.getBoolean("MV.entrante"),
              Integer.parseInt(rs.getString("T.transferencia_id"))));
        }
      }
      return transferencias;
    });
  }

  public void insertar(Transferencia elemento) throws DAOException {
    if (elemento.getTipoOperacion() == TipoOperacion.TRANSFERENCIA) {
      throw new DAOException("No se puede insertar una transferencia, debe insertarla con la cuenta origen");
    }

    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Transferencia (fecha, monto, cod_moneda, concepto, cod_tipo_operacion) " +
              "VALUES (?, ?, ?, ?, ?) ",
          java.sql.Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setDate(1, java.sql.Date.valueOf(elemento.getFecha()));
      preparedStatement.setDouble(2, elemento.getMonto());
      preparedStatement.setString(3, elemento.getMoneda().getCodigo());
      preparedStatement.setString(4, elemento.getConcepto());
      preparedStatement.setString(5, elemento.getTipoOperacion().getCodigo());
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

      if (elemento.getTipoOperacion() == TipoOperacion.CREDITO
          || elemento.getTipoOperacion() == TipoOperacion.DEPOSITO) {
        preparedStatement = conn.prepare(movimientoInsertSql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, elemento.getCuentaTercero().getId());
        preparedStatement.setBoolean(3, true);
        preparedStatement.executeUpdate();
      } else if (elemento.getTipoOperacion() == TipoOperacion.DEBITO
          || elemento.getTipoOperacion() == TipoOperacion.EXTRACCION) {
        preparedStatement = conn.prepare(movimientoInsertSql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, elemento.getCuentaTercero().getId());
        preparedStatement.setBoolean(3, false);
        preparedStatement.executeUpdate();
      }

      return null;
    });
  }

  public void insertar(Cuenta cuentaOrigen, Transferencia elemento) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      // Insertar la transferencia
      PreparedStatement preparedStatement = conn.prepare(
          "INSERT INTO Transferencia (fecha, monto, cod_moneda, concepto) " +
              "VALUES (?, ?, ?, ?) ",
          java.sql.Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setDate(1, java.sql.Date.valueOf(elemento.getFecha()));
      preparedStatement.setDouble(2, elemento.getMonto());
      preparedStatement.setString(3, elemento.getMoneda().getCodigo());
      preparedStatement.setString(4, elemento.getConcepto());
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
      preparedStatement.setInt(2, elemento.getCuentaTercero().getId());
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
