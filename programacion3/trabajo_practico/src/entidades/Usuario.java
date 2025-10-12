package programacion3.trabajo_practico.src.entidades;

public abstract class Usuario {
  //* Atributos
  private int id;
  private String nombre;
  private String apellido;

  //* Constructores
  public Usuario() {}
  
  public Usuario(String nombre, String apellido) {
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public Usuario(String nombre, String apellido, int id) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.id = id;
  }

  //* Getters & Setters
  public int getId() {
    return this.id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  //* Métodos
  @Override
  public String toString() {
    return this.nombre + " " + this.apellido;
  }
}
