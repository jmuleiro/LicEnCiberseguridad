package programacion3.clase5;

import programacion3.clase2.entidades.Fecha;
import programacion3.clase3.entidades.EmpleadoContratado;
import programacion3.clase3.entidades.EmpleadoEfectivo;

public class Main {
  public static void main(String[] args) {
    EmpleadoContratado emp1 = new EmpleadoContratado("José Pérez", new Fecha(2, 9, 2025));
    EmpleadoEfectivo emp2 = new EmpleadoEfectivo("José José", new Fecha(1, 9, 2025));
  }
}
