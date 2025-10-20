package programacion3.trabajo_practico.src.dao;

import java.sql.SQLException;

@FunctionalInterface // T is the return type
public interface DAOAction<T> {
  T execute() throws SQLException;
}