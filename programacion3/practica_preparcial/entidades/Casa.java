package programacion3.practica_preparcial.entidades;

public class Casa extends Vivienda {
  //* Constructor
  public Casa (int superficie) {
    super(superficie);
  }
  
  public Casa(int superficie, double porcentajeDescuento) {
    super(superficie, porcentajeDescuento);
  }

  //* Métodos
  @Override
  public int caloriasNecesarias() {
    int brutoCalorias = this.getSuperficie() * 700;
    if (!this.getTieneAislacion())
      return brutoCalorias;
    return brutoCalorias - (int) (brutoCalorias * (this.getPorcentajeDescuento() / 100));
  }
}
