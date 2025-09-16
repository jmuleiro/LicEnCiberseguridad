package programacion3.practica_preparcial;

import programacion3.practica_preparcial.entidades.Casa;
import programacion3.practica_preparcial.entidades.Comercio;
import programacion3.practica_preparcial.entidades.Departamento;

public class Main {
  public static void main(String[] args) {
    System.out.println("Comienza la ejecución");
    Comercio comercio = new Comercio("El Gasificador");

    System.out.println("Agregando un cliente de departamento sin aislamiento");
    comercio.agregarCliente(null, null, "1", new Departamento(38, 2));
    System.out.println("Consultar cliente 1: " + comercio.consultarCliente("1"));

    System.out.println("Agregando un cliente de departamento con aislamiento");
    comercio.agregarCliente(null, null, "2", new Departamento(38, 3, 10.5));
    System.out.println("Consultar cliente 2: " + comercio.consultarCliente("2"));

    System.out.println("Agregando un cliente de casa sin aislamiento");
    comercio.agregarCliente(null, null, "3", new Casa(72));
    System.out.println("Consultar cliente 3: " + comercio.consultarCliente("3"));

    System.out.println("Agregando un cliente de casa con aislamiento");
    comercio.agregarCliente(null, null, "4", new Casa(72, 5.0));
    System.out.println("Consultar cliente 4: " + comercio.consultarCliente("4"));

    System.out.println("Calorías a cubrir 1: " + comercio.caloriasACubrir("1"));
    System.out.println("Calorías a cubrir 2 (ais): " + comercio.caloriasACubrir("2"));
    System.out.println("Calorías a cubrir 3: " + comercio.caloriasACubrir("3"));
    System.out.println("Calorías a cubrir 4 (ais): " + comercio.caloriasACubrir("4"));

    System.out.println("Calorías necesarias totales: " + comercio.caloriasTotalesNecesarias());
  }
}
