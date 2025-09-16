package programacion3.practica_preparcial.entidades;

import java.util.ArrayList;
import java.util.List;

public class Comercio {
  //* Atributos
  private String nombre;
  private List<Cliente> clientes;

  //* Constructor
  public Comercio(String nombre) {
    this.nombre = nombre;
    this.clientes = new ArrayList<>();
  }

  //* Métodos
  public void agregarCliente(String nombre, String apellido, String telefono, Vivienda vivienda) {
    this.clientes.add(new Cliente(nombre, apellido, telefono, vivienda));
  }

  public int caloriasACubrir(String telefono) {
    Vivienda vivienda;
    for (Cliente cliente : this.clientes) {
      if (cliente.getTelefono() == telefono) {
        vivienda = cliente.getVivienda();
        return vivienda.caloriasNecesarias();
      }
    }
    throw new RuntimeException("No se encontró el cliente");
  }

  public Cliente consultarCliente(String telefono) {
    for (Cliente cliente : this.clientes) {
      if (cliente.getTelefono() == telefono)
        return cliente;
    }
    throw new RuntimeException("No se encontró el cliente");
  }

  public int caloriasTotalesNecesarias() {
    int caloriasTotales = 0;
    for (Cliente cliente : this.clientes) {
      caloriasTotales += cliente.getVivienda().caloriasNecesarias();
    }
    return caloriasTotales;
  }

  // Getters & Setters
  public String getNombre() {
    return this.nombre;
  }
}
