package programacion3.trabajo_practico.src.dao;

import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.Consumo;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DAOTarjetaCredito extends DAOBase implements IDAO<TarjetaCredito, Integer> {
  public DAOTarjetaCredito() throws DAOException {
    super();
  }

  @Override
  // todo
  public void insertar(UsuarioCliente usuario, TarjetaCredito elemento) throws DAOException {
    new DAOTemplate<TarjetaCredito>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "INSERT INTO Tarjeta (usuario_id, cod_tipo_tarjeta, limite, numero, fecha_vencimiento, cvc)" + 
        "VALUES (?, \"CRE\", ?, ?, ?, ?)"
      );
      preparedStatement.setInt(1, usuario.getId());
      preparedStatement.setDouble(2, elemento.getLimite());
      preparedStatement.setInt(3, elemento.getNumero());
      preparedStatement.setDate(4, java.sql.Date.valueOf(elemento.getFechaVencimiento()));
      preparedStatement.setInt(5, elemento.getCvc());
      preparedStatement.executeUpdate();
      return null; // Necesario para que no tire error por el tipo Void
    });
  }

  @Override
  public void eliminar(Integer id) throws DAOException {
    new DAOTemplate<Void>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
        "DELETE FROM Tarjeta WHERE tarjeta_id = ?"
      );
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
        "WHERE tarjeta_id = ?"
      );
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
        "SELECT numero, fecha_vencimiento, cvc, limite, tarjeta_id" + 
        "FROM Tarjeta " + 
        "WHERE tarjeta_id = ?"
      );
      preparedStatement.setInt(1, id.intValue());
      ResultSet rs = preparedStatement.executeQuery();
      if (!(rs.next()))
        return null; // No hubo resultados
      
      TarjetaCredito tarjeta = new TarjetaCredito(
        rs.getInt("numero"),
        rs.getDate("fecha_vencimiento").toLocalDate(),
        rs.getInt("cvc"),
        rs.getDouble("limite"),
        rs.getInt("tarjeta_id")
      );

      // Traer los consumos
      preparedStatement = conn.prepare(
        "SELECT C.cantidad, C.fecha, C.cod_moneda, C.consumo_id, M.nombre_moneda " + 
        "FROM Consumo AS C " + 
        "INNER JOIN Moneda AS M ON C.cod_moneda = M.cod_moneda " + 
        "WHERE C.tarjeta_id = ?"
      );
      preparedStatement.setInt(1, id.intValue());
      rs = preparedStatement.executeQuery();
      while (rs.next()) {
        tarjeta.agregarConsumo(
          new Consumo(
            rs.getDouble("C.cantidad"),
            rs.getDate("C.fecha").toLocalDate(),
            new Moneda(
              rs.getString("C.cod_moneda"),
              rs.getString("M.nombre_moneda")
            ),
            rs.getInt("C.consumo_id")
          )
        );
      }
      return tarjeta;
    });
  }

  @Override
  public List<TarjetaCredito> consultarTodos() throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  // todo: consultar todos por usuario
}
