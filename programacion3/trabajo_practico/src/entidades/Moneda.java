package programacion3.trabajo_practico.src.entidades;

public class Moneda {
  //* Atributos
  private String codigo;
  private String nombre;

  //* Constructores
  public Moneda(String codigo, String nombre) {
    this.codigo = codigo;
    this.nombre = nombre;
  }

  //* Getters & Setters
  public String getCodigo() {
    return this.codigo;
  }

  public String getNombre() {
    return this.nombre;
  }

  //* Métodos
  @Override
  public String toString() {
    return this.nombre;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Moneda)
      return this.codigo == ((Moneda) obj).getCodigo();
    return false;
  }
}
