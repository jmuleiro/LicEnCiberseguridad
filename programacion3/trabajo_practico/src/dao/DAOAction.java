package programacion3.trabajo_practico.src.dao;

import java.sql.SQLException;

@FunctionalInterface
public interface DAOAction {
  void execute() throws SQLException;
}