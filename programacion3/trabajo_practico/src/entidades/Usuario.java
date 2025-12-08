package programacion3.trabajo_practico.src.entidades;

public abstract class Usuario {
  // * Atributos
  private int id;
  private String nombre;
  private String apellido;
  private String usuario;

  // * Constructores
  public Usuario() {
  }

  public Usuario(String nombre, String apellido, String usuario) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
  }

  public Usuario(String nombre, String apellido, String usuario, int id) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
    this.id = id;
  }

  // * Getters & Setters
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public String getUsuario() {
    return this.usuario;
  }

  // * Métodos
  @Override
  public String toString() {
    return this.nombre + " " + this.apellido;
  }
}
