package programacion3.practica1.ej7;

public class CajaVelocidades {
  //* Atributos
  private String fabricante;
  private int cantidadMarchas;
  private char tipoRelacion;

  //* Constructor
  public CajaVelocidades(String fabricante, int cantidadMarchas, char tipoRelacion) throws Exception {
    this.fabricante = fabricante.strip();
    this.setCantidadMarchas(cantidadMarchas);
    this.setTipoRelacion(tipoRelacion);
  }

  //* Getters & Setters
  public String getFabricante() {
    return this.fabricante;
  }

  public int getCantidadMarchas() {
    return this.cantidadMarchas;
  }

  // Setter privado porque no se debe usar fuera de la Clase
  private void setCantidadMarchas(int cantidad) throws Exception {
    if (cantidad < 2 || cantidad > 7)
      throw new Exception("La cantidad de marchas debe estar entre 2 y 7");
    this.cantidadMarchas = cantidad;
  }

  // Idem
  private void setTipoRelacion(char tipo) throws Exception {
    if (tipo != 'L' && tipo != 'M' && tipo != 'C')
      throw new Exception("El tipo de relación debe ser L, M, o C");
    this.tipoRelacion = tipo;
  }

  public char getTipoRelacion() {
    return this.tipoRelacion;
  }

  //* Métodos
  public String toString() {
    return "Fabricante: " + this.fabricante + ", cant. marchas: " + this.cantidadMarchas + ", tipo de rel.: " + this.tipoRelacion;
  }
}
