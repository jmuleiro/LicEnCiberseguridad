package programacion3.trabajo_practico.src.service;

public class ServiceTemplate<T> {
  public T execute(ServiceAction<T> action) throws ServiceException {
    String nombreFuncion = this.getClass().getEnclosingMethod().getName();
    String nombreClase = this.getClass().getEnclosingClass().getName();
    try {
      return action.execute();
    } catch (Exception e) {
      System.out.println("ServiceException: " + e.getMessage());
      throw new ServiceException("Fallo al " + nombreFuncion + " en la clase " + nombreClase);
    }
  }
}
