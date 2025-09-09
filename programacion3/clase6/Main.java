package programacion3.clase6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import programacion3.clase2.entidades.Fecha;
import programacion3.clase3.entidades.EmpleadoContratado;
import programacion3.clase3.entidades.EmpleadoEfectivo;
import programacion3.clase6.entidades.Empresa;

public class Main {
  public static void main(String[] args) {
    List<Integer> numeros = new ArrayList<>();
    numeros.add(5);
    numeros.add(10);
    numeros.add(3);
    numeros.add(8);
    for (int i=0; i < numeros.size(); i++) {
      System.out.println("(for) Posicion: " + i + ", valor: " + numeros.get(i));
    }
    for (Integer aux:numeros) {
      System.out.println("(foreach) Valor: " + aux);
    }
    Iterator<Integer> it = numeros.iterator();
    while (it.hasNext()) {
      System.out.println("(while) Valor: " + it.next());
    }

    Empresa miEmpresa = new Empresa("UP");
    EmpleadoContratado emp1 = new EmpleadoContratado("José Pérez", new Fecha(2, 9, 2025));
    EmpleadoEfectivo emp2 = new EmpleadoEfectivo("José José", new Fecha(1, 9, 2025));

    miEmpresa.agregarEmpleado(emp1);
    miEmpresa.agregarEmpleado(emp2);
    System.out.println("Importe necesario para sueldos " + miEmpresa.totalSueldos());
    miEmpresa.pagarSueldos();
    miEmpresa.despedirEmpleado(emp2);
    miEmpresa.pagarSueldos();
  }
}
