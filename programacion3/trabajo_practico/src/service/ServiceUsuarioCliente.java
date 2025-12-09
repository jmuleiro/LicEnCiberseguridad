package programacion3.trabajo_practico.src.service;

import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuarioCliente;
import programacion3.trabajo_practico.src.dao.DAOTarjetaCredito;
import programacion3.trabajo_practico.src.dao.DAOCuenta;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.dao.DAOConsumo;
import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Consumo;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.TarjetaCredito;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceUsuarioCliente extends ServiceBaseS<UsuarioCliente, Integer> {
  private DAOEvento daoEvento;
  private DAOTarjetaCredito daoTarjetaCredito;
  private DAOCuenta daoCuenta;
  private DAOConsumo daoConsumo;
  private DAOTransferencia daoTransferencia;

  public ServiceUsuarioCliente(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOUsuarioCliente();
      daoEvento = new DAOEvento();
      daoTarjetaCredito = new DAOTarjetaCredito();
      daoCuenta = new DAOCuenta();
      daoConsumo = new DAOConsumo();
      daoTransferencia = new DAOTransferencia();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public UsuarioCliente consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, id.toString()), contexto);
      return usuarioCliente;
    });
  }

  public UsuarioCliente consultarCompleto(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, id.toString()), contexto);

      List<TarjetaCredito> tarjetas = daoTarjetaCredito.consultarTodos(usuarioCliente);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, "-1"), contexto);

      for (TarjetaCredito tarjeta : tarjetas) {
        List<Consumo> consumos = daoConsumo.consultarTodos(tarjeta);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CONSUMO, "-1"), contexto);
        tarjeta.setConsumos(consumos);
        usuarioCliente.altaTarjeta(tarjeta);
      }

      List<Cuenta> cuentas = daoCuenta.consultarTodos(usuarioCliente);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA, "-1"), contexto);

      for (Cuenta cuenta : cuentas) {
        List<Transferencia> transferencias = daoTransferencia.consultarTodos(cuenta);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TRANSFERENCIA, "-1"), contexto);
        cuenta.setTransferencias(transferencias);
        usuarioCliente.altaCuenta(cuenta);
      }

      return usuarioCliente;
    });
  }

  public UsuarioCliente consultarCompleto(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(username);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, username), contexto);

      List<TarjetaCredito> tarjetas = daoTarjetaCredito.consultarTodos(usuarioCliente);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, username), contexto);

      for (TarjetaCredito tarjeta : tarjetas) {
        List<Consumo> consumos = daoConsumo.consultarTodos(tarjeta);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CONSUMO, username), contexto);
        tarjeta.setConsumos(consumos);
        usuarioCliente.altaTarjeta(tarjeta);
      }

      List<Cuenta> cuentas = daoCuenta.consultarTodos(usuarioCliente);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA, username), contexto);

      for (Cuenta cuenta : cuentas) {
        List<Transferencia> transferencias = daoTransferencia.consultarTodos(cuenta);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TRANSFERENCIA, username), contexto);
        cuenta.setTransferencias(transferencias);
        usuarioCliente.altaCuenta(cuenta);
      }

      return usuarioCliente;
    });
  }

  @Override
  public UsuarioCliente consultar(String username) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = dao.consultar(username);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, username), contexto);
      return usuarioCliente;
    });
  }

  @Override
  public List<UsuarioCliente> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<UsuarioCliente>>().execute(() -> {
      List<UsuarioCliente> usuarioClientes = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);
      return usuarioClientes;
    });
  }

  public List<UsuarioCliente> consultarTodosCompleto() throws ServiceException {
    return new ServiceTemplate<List<UsuarioCliente>>().execute(() -> {
      List<UsuarioCliente> usuarioClientes = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);

      for (UsuarioCliente usuarioCliente : usuarioClientes) {
        List<TarjetaCredito> tarjetas = daoTarjetaCredito.consultarTodos(usuarioCliente);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TARJETA_CREDITO, usuarioCliente.getUsuario()),
            contexto);

        for (TarjetaCredito tarjeta : tarjetas) {
          usuarioCliente.altaTarjeta(tarjeta);
        }

        List<Cuenta> cuentas = daoCuenta.consultarTodos(usuarioCliente);
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA, usuarioCliente.getUsuario()),
            contexto);

        for (Cuenta cuenta : cuentas) {
          usuarioCliente.altaCuenta(cuenta);
        }
      }
      return usuarioClientes;
    });
  }

  @Override
  public void insertar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.USUARIO, elemento.getUsuario()), contexto);
      return null;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(new Evento(TipoEvento.ELIMINACION, TipoObjeto.USUARIO, id.toString()), contexto);
      return null;
    });
  }

  @Override
  public void modificar(UsuarioCliente elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      daoEvento.insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.USUARIO, elemento.getUsuario()), contexto);
      return null;
    });
  }

  // Funcionalidades
  public void agregarUsuario(String nombreString, String apellidoString, String usuarioString) throws ServiceException {
    if (nombreString.isEmpty() ||
        apellidoString.isEmpty() ||
        usuarioString.isEmpty()) {
      throw new ServiceException("Debe completar todos los campos");
    }

    this.insertar(
        new UsuarioCliente(
            nombreString,
            apellidoString,
            usuarioString));
  }

  public void modificarUsuario(String nombreString, String apellidoString, String usuarioString, Integer id)
      throws ServiceException {
    if (nombreString.isEmpty() ||
        apellidoString.isEmpty() ||
        usuarioString.isEmpty()) {
      throw new ServiceException("Debe completar todos los campos");
    }

    this.modificar(
        new UsuarioCliente(
            nombreString,
            apellidoString,
            usuarioString,
            id));
  }
}
