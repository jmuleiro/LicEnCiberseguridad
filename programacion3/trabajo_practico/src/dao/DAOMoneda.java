package programacion3.trabajo_practico.src.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import programacion3.trabajo_practico.src.entidades.Moneda;

public class DAOMoneda extends DAOBase<Moneda, String> {
  public DAOMoneda() throws DAOException {
    super();
  }

  @Override
  public void insertar(Moneda elemento) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void eliminar(String id) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public void modificar(Moneda elemento) throws DAOException {
    throw new UnsupportedOperationException("Método no implementado");
  }

  @Override
  public Moneda consultar(String cod) throws DAOException {
    return new DAOTemplate<Moneda>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT cod_moneda, nombre_moneda FROM Moneda WHERE cod_moneda = ?");
      preparedStatement.setString(1, cod);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Moneda(
            rs.getString("cod_moneda"),
            rs.getString("nombre_moneda"));
      }
      return null; // No hubo resultados
    });
  }

  @Override
  public List<Moneda> consultarTodos() throws DAOException {
    return new DAOTemplate<List<Moneda>>().execute(entityName, () -> {
      PreparedStatement preparedStatement = conn.prepare(
          "SELECT cod_moneda, nombre_moneda FROM Moneda");
      ResultSet rs = preparedStatement.executeQuery();
      List<Moneda> monedas = new ArrayList<>();
      while (rs.next()) {
        monedas.add(new Moneda(
            rs.getString("cod_moneda"),
            rs.getString("nombre_moneda")));
      }
      return monedas;
    });
  }
}
