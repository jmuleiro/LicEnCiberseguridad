package programacion3.trabajo_practico.src.entidades;

public class UsuarioAdmin extends Usuario {
  //* Atributos
  private String rol;

  //* Constructores
  public UsuarioAdmin() {
    super();
  }

  public UsuarioAdmin(String nombre, String apellido, String rol) {
    super(nombre, apellido);
    this.rol = rol;
  }

  public UsuarioAdmin(String nombre, String apellido, String rol, int id) {
    super(nombre, apellido, id);
    this.rol = rol;
  }

  //* Getters & Setters
  public String getRol() {
    return this.rol;
  }

  //* Métodos
  @Override
  public String toString() {
    return this.getRol() + " " + super.toString();
  }
}
