package programacion3.trabajo_practico.src.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ConnectionHelper {
  //* Atributos
  private String DB_URL = "jdbc:mysql://localhost:3306/prog3";
  private String DB_USER = "root";
  private String DB_PASSWORD = "Root1234!";
  private String DB_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private Connection conn;

  public ConnectionHelper() throws SQLException, ClassNotFoundException{
    Class.forName(DB_JDBC_DRIVER);
    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  //* Helper de DB
  public PreparedStatement prepare(String statement) throws SQLException {
    return conn.prepareStatement(statement);
  }

  public void close() throws SQLException {
    this.conn.close();
  }
}
