package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

// TODO
public class TarjetaDebito extends Tarjeta {
  // * Constructores
  public TarjetaDebito(int numero, LocalDate fechaVencimiento, int cvc, double limite) {
    super(numero, fechaVencimiento, cvc, limite);
  }

  public TarjetaDebito(int numero, LocalDate fechaVencimiento, int cvc, double limite, int id) {
    super(numero, fechaVencimiento, cvc, limite, id);
  }
}
