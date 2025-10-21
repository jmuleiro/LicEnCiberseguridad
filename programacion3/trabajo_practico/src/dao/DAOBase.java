package programacion3.trabajo_practico.src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import programacion3.trabajo_practico.src.helpers.ConnectionHelper;

import java.sql.DriverManager;

public class DAOBase {
  //* Atributos
  protected ConnectionHelper conn;
  protected String entityName;

  //* Constructor
  public DAOBase() throws DAOException {
    try {
      this.conn = new ConnectionHelper();
    } catch (SQLException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo al iniciar DAO: " + this.getClass().getName());
    } catch (ClassNotFoundException e) {
      System.out.println("Exception: " + e.getMessage());
      throw new DAOException("Fallo de drivers en: " + this.getClass().getName());
    }
    this.entityName = this.getClass().getSimpleName().substring(3);
  }
}
