package programacion3.trabajo_practico.src.entidades;

import java.util.ArrayList;
import java.util.List;

public class UsuarioCliente extends Usuario {
  //* Atributos
  private List<Cuenta> cuentas;
  private List<Tarjeta> tarjetas;

  //* Constructores
  public UsuarioCliente(String nombre, String apellido) {
    super(nombre, apellido);
    this.cuentas = new ArrayList<>();
    this.tarjetas = new ArrayList<>();
  }

  public UsuarioCliente(String nombre, String apellido, List<Cuenta> cuentas, List<Tarjeta> tarjetas) {
    super(nombre, apellido);
    this.cuentas = cuentas;
    this.tarjetas = tarjetas;
  }

  public UsuarioCliente(String nombre, String apellido, List<Cuenta> cuentas, List<Tarjeta> tarjetas, int id) {
    super(nombre, apellido, id);
    this.cuentas = cuentas;
    this.tarjetas = tarjetas;
  }

  //* Métodos
  // Tarjetas
  public int altaTarjeta(Tarjeta t) {
    this.tarjetas.add(t);
    return t.getId();
  }

  public Tarjeta bajaTarjeta(int id) {
    for (Tarjeta t : this.tarjetas) {
      if (t.getId() == id) {
        this.tarjetas.remove(t);
        return t;
      }
    }
    throw new RuntimeException("Tarjeta no encontrada");
  }

  // Cuentas
  public int altaCuenta(Cuenta c) {
    this.cuentas.add(c);
    return c.getId();
  }

  public Cuenta bajaCuenta(int id) {
    for (Cuenta c : this.cuentas) {
      if (c.getId() == id) {
        this.cuentas.remove(c);
        return c;
      }
    }
    throw new RuntimeException("Cuenta no encontrada");
  }

  //* Getters & Setters
  public List<Cuenta> getCuentas() {
    return this.cuentas;
  }

  public List<Tarjeta> getTarjetas() {
    return this.tarjetas;
  }
}
