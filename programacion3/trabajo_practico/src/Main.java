package programacion3.trabajo_practico.src;

import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;
import programacion3.trabajo_practico.src.service.ServiceUsuarioAdmin;

public class Main {
  public static void main(String[] args) {
    UsuarioAdmin usuarioAdmin = new UsuarioAdmin("Jose", "Suarez", "Bancario");
    try {
      ServiceUsuarioAdmin serviceUsuarioAdmin = new ServiceUsuarioAdmin();
      serviceUsuarioAdmin.insertar(usuarioAdmin);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
