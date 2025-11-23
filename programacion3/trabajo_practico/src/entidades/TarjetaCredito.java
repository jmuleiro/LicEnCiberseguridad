package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDate;

public class TarjetaCredito extends Tarjeta {
  // * Constructores
  public TarjetaCredito(String numero, LocalDate fechaVencimiento, int cvc, double limite) {
    super(numero, fechaVencimiento, cvc, limite);
  }

  public TarjetaCredito(String numero, LocalDate fechaVencimiento, int cvc, double limite, int id) {
    super(numero, fechaVencimiento, cvc, limite, id);
  }

  // * Métodos
  // Verifica si queda límite restante en el mes actual
  // Aviso: Realísticamente debería haber 1 solo límite que considere todas las
  // monedas posibles, pero se eligió obviar eso para el trabajo práctico.
  public void agregarConsumo(Consumo consumo) {
    double total = this.getConsumos().stream()
        .filter(c -> {
          return c.getMoneda() == consumo.getMoneda();
        })
        .mapToDouble(Consumo::getCantidad)
        .sum();

    if (total + consumo.getCantidad() > this.getLimite())
      throw new RuntimeException("El consumo excede el límite de la tarjeta");

    this.getConsumos().add(consumo);
  }
}
