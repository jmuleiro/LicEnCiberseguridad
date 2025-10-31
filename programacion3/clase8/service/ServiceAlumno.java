package programacion3.clase7.service;

import programacion3.clase7.dao.DAOAlumno;
import programacion3.clase7.dao.DAOException;
import programacion3.clase7.entidades.Alumno;

public class ServiceAlumno {
  public void insertar(Alumno alumno) {
    DAOAlumno dao = new DAOAlumno();
    try{
      dao.insertar(alumno);
    } catch (DAOException e) {
      System.out.println("DAOException: " + e);
    }
  }
}
