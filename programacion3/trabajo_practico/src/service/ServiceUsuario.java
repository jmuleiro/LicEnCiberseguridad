package programacion3.trabajo_practico.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOUsuario;
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
import programacion3.trabajo_practico.src.entidades.Usuario;
import programacion3.trabajo_practico.src.entidades.UsuarioAdmin;

public class ServiceUsuario extends ServiceBaseS<Usuario, Integer> {
  private DAOEvento daoEvento;
  private DAOTarjetaCredito daoTarjetaCredito;
  private DAOCuenta daoCuenta;
  private DAOConsumo daoConsumo;
  private DAOTransferencia daoTransferencia;

  public ServiceUsuario(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOUsuario();
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
  public Usuario consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<Usuario>().execute(() -> {
      Usuario usuario = dao.consultar(id);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, id.toString()), contexto);
      return usuario;
    });
  }

  public UsuarioCliente consultarCompleto(Integer id) throws ServiceException {
    return new ServiceTemplate<UsuarioCliente>().execute(() -> {
      UsuarioCliente usuarioCliente = (UsuarioCliente) dao.consultar(id);
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
      UsuarioCliente usuarioCliente = (UsuarioCliente) dao.consultar(username);
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
  public Usuario consultar(String username) throws ServiceException {
    return new ServiceTemplate<Usuario>().execute(() -> {
      Usuario usuario = dao.consultar(username);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, username), contexto);
      return usuario;
    });
  }

  @Override
  public List<Usuario> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<Usuario>>().execute(() -> {
      List<Usuario> usuarios = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);
      return usuarios;
    });
  }

  public List<UsuarioCliente> consultarTodosCompleto() throws ServiceException {
    return new ServiceTemplate<List<UsuarioCliente>>().execute(() -> {
      List<Usuario> usuarios = dao.consultarTodos();
      List<UsuarioCliente> usuarioClientes = new ArrayList<>();

      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.USUARIO, "-1"), contexto);

      for (Usuario usuario : usuarios) {
        if (!(usuario instanceof UsuarioCliente)) {
          continue;
        }
        UsuarioCliente usuarioCliente = (UsuarioCliente) usuario;
        usuarioClientes.add(usuarioCliente);
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
  public void insertar(Usuario elemento) throws ServiceException {
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
  public void modificar(Usuario elemento) throws ServiceException {
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

  public UsuarioAdmin login(String username, String password) throws ServiceException {
    return new ServiceTemplate<UsuarioAdmin>().execute(() -> {
      UsuarioAdmin usuarioAdmin = (UsuarioAdmin) dao.consultar(username);
      if (usuarioAdmin == null) {
        daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username, false));
        return null;
      }

      daoEvento.insertar(new Evento(TipoEvento.LOGIN, TipoObjeto.USUARIO, username));
      return usuarioAdmin;
    });
  }

  public void logout(String username) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      daoEvento.insertar(new Evento(TipoEvento.LOGOUT, TipoObjeto.USUARIO, username), contexto);
      return null;
    });
  }
}
