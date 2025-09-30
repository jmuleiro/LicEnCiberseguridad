package programacion3.clase7.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import programacion3.clase7.entidades.Alumno;

public class DAOAlumno implements IDAO<Alumno> {

  private String DB_URL = "jdbc:mysql://localhost/prog3?user=root&password=root";

  @Override
  public void insertar(Alumno elemento) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DriverManager.getConnection(DB_URL);
      preparedStatement = connection.prepareStatement("INSERT INTO Alumno VALUES(?,?,?)");
      preparedStatement.setInt(1, elemento.getId());
      preparedStatement.setString(2, elemento.getNombre());
      preparedStatement.setString(3, elemento.getApellido());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo en base de datos");
    }
  }

  @Override
  public void modificar(Alumno elemento) throws DAOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'modificar'");
  }

  @Override
  public void eliminar(int id) throws DAOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
  }

  @Override
  public Alumno consultar(int id) throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DriverManager.getConnection(DB_URL);
      preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM Alumno WHERE id = ?");
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return new Alumno(
          rs.getInt("id"),
          rs.getString("nombre"),
          rs.getString("apellido")
        );
      }
    } catch (SQLException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo en base de datos");
    }
  }

  @Override
  public List<Alumno> consultarTodos() throws DAOException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DriverManager.getConnection(DB_URL);
      preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM Alumno");
      ResultSet rs = preparedStatement.executeQuery();
      List<Alumno> alumnos = new ArrayList<>();
      while (rs.next()) {
        alumnos.add(new Alumno(
          rs.getInt("id"), 
          rs.getString("nombre"), 
          rs.getString("apellido")
        ));
      }
    } catch (SQLException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo en base de datos");
    }
  }
  
}
