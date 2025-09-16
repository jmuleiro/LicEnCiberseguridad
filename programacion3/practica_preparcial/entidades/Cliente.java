package programacion3.practica_preparcial.entidades;

public class Cliente {
  //* Atributos
  private String nombre;
  private String apellido;
  private String telefono;
  private Vivienda vivienda;

  //* Constructor
  public Cliente(String nombre, String apellido, String telefono, Vivienda vivienda) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.vivienda = vivienda;
  }

  //* Métodos
  @Override
  public String toString() {
    return "Nombre: " + this.getNombre() + ", Apellido: " + this.getApellido() + ", Teléfono: " + this.getTelefono();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Cliente)
      return this.telefono == ((Cliente) obj).getTelefono();
    return false;
  }

  // Getters & Setters
  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public Vivienda getVivienda() {
    return this.vivienda;
  }
}
