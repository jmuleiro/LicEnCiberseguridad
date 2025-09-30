package programacion3.clase7.entidades;

public class Alumno {
  //* Atributos
  private int id;
  private String nombre;
  private String apellido;

  //* Constructor
  public Alumno() {}
  public Alumno(int id, String nombre, String apellido) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  //* Getters & Setters
  // ID
  public int getId() {
    return this.id;
  }

  public int setId() {}

  // Nombre
  public String getNombre() {
    return this.nombre;
  }

  // Apellido
  public String getApellido() {
    return this.apellido;
  }
}
