package programacion3.trabajo_practico.src.entidades;

import java.time.LocalDateTime;

public class Evento {
  // * Atributos
  private int id;
  private TipoEvento tipo;
  private TipoObjeto objeto;
  private String idObjeto;
  private LocalDateTime fechaHora;
  private boolean exitoso;

  // * Constructores
  public Evento(TipoEvento tipo, TipoObjeto objeto, String idObjeto) {
    this.tipo = tipo;
    this.objeto = objeto;
    this.idObjeto = idObjeto;
    this.fechaHora = LocalDateTime.now();
    this.exitoso = true;
  }

  public Evento(TipoEvento tipo, TipoObjeto objeto, String idObjeto, boolean exitoso) {
    this.tipo = tipo;
    this.objeto = objeto;
    this.idObjeto = idObjeto;
    this.fechaHora = LocalDateTime.now();
    this.exitoso = exitoso;
  }

  public Evento(TipoEvento tipo, TipoObjeto objeto, String idObjeto, LocalDateTime fechaHora, int id) {
    this.id = id;
    this.tipo = tipo;
    this.objeto = objeto;
    this.idObjeto = idObjeto;
    this.fechaHora = fechaHora;
    this.exitoso = true;
  }

  public Evento(TipoEvento tipo, TipoObjeto objeto, String idObjeto, LocalDateTime fechaHora, int id, boolean exitoso) {
    this.id = id;
    this.tipo = tipo;
    this.objeto = objeto;
    this.idObjeto = idObjeto;
    this.fechaHora = fechaHora;
    this.exitoso = exitoso;
  }

  // * Getters
  public int getId() {
    return id;
  }

  public TipoEvento getTipo() {
    return tipo;
  }

  public TipoObjeto getObjeto() {
    return objeto;
  }

  public String getIdObjeto() {
    return idObjeto;
  }

  public LocalDateTime getFechaHora() {
    return fechaHora;
  }

  public boolean isExitoso() {
    return exitoso;
  }
}
