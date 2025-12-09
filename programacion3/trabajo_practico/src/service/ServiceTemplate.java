package programacion3.trabajo_practico.src.service;

public class ServiceTemplate<T> {
  public T execute(ServiceAction<T> action) throws ServiceException {
    try {
      return action.execute();
    } catch (Exception e) {
      System.out.println("ServiceException: " + e.getMessage());
      throw new ServiceException("Fallo en el service");
    }
  }
}
