package programacion3.trabajo_practico.src.service;

import programacion3.trabajo_practico.src.dao.DAOException;

@FunctionalInterface
public interface ServiceAction<T> {
  T execute() throws DAOException;
}