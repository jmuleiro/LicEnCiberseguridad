package programacion3.practica_preparcial.entidades;

public class Departamento extends Vivienda {
  //* Atributos
  private int cantidadHabitantes;
  
  //* Constructor
  public Departamento(int superficie, int cantidadHabitantes) {
    super(superficie);
    this.cantidadHabitantes = cantidadHabitantes;
  }
  
  public Departamento(int superficie, int cantidadHabitantes, double porcentajeDescuento) {
    super(superficie, porcentajeDescuento);
    this.cantidadHabitantes = cantidadHabitantes;
  }

  //* Métodos
  @Override
  public int caloriasNecesarias() {
    int brutoCalorias = this.getSuperficie() * 500;
    if (!this.getTieneAislacion())
      return brutoCalorias;
    return  brutoCalorias + (100 * this.getCantidadHabitantes()) - (int) (brutoCalorias * (this.getPorcentajeDescuento() / 100));
  }

  // Getters & Setters
  public int getCantidadHabitantes() {
    return this.cantidadHabitantes;
  }
}
