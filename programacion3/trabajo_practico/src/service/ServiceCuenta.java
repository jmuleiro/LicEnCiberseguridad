package programacion3.trabajo_practico.src.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import programacion3.trabajo_practico.src.dao.DAOCuenta;
import programacion3.trabajo_practico.src.dao.DAOEvento;
import programacion3.trabajo_practico.src.dao.DAOException;
import programacion3.trabajo_practico.src.dao.DAOMoneda;
import programacion3.trabajo_practico.src.dao.DAOTransferencia;
import programacion3.trabajo_practico.src.entidades.CajaAhorro;
import programacion3.trabajo_practico.src.entidades.Cuenta;
import programacion3.trabajo_practico.src.entidades.Moneda;
import programacion3.trabajo_practico.src.entidades.CuentaCorriente;
import programacion3.trabajo_practico.src.entidades.Evento;
import programacion3.trabajo_practico.src.entidades.TipoEvento;
import programacion3.trabajo_practico.src.entidades.TipoObjeto;
import programacion3.trabajo_practico.src.entidades.TipoOperacion;
import programacion3.trabajo_practico.src.entidades.Transferencia;
import programacion3.trabajo_practico.src.entidades.UsuarioCliente;

public class ServiceCuenta extends ServiceBase<Cuenta, Integer> {
  private DAOCuenta dao;
  private DAOTransferencia daoTransferencia;
  private DAOEvento daoEvento;
  private DAOMoneda daoMoneda;

  public ServiceCuenta(Map<String, String> contexto) throws ServiceException {
    super(contexto);
    try {
      dao = new DAOCuenta();
      daoTransferencia = new DAOTransferencia();
      daoEvento = new DAOEvento();
      daoMoneda = new DAOMoneda();
    } catch (DAOException e) {
      System.out.println("DAOException: " + e.getMessage());
      throw new ServiceException("Fallo al iniciar DAO en: " + this.getClass().getName());
    }
  }

  @Override
  public void insertar(Cuenta elemento) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("El método insertar requiere el usuario como parámetro");
  }

  public void insertar(Cuenta elemento, UsuarioCliente usuario) throws ServiceException {
    if (elemento instanceof CajaAhorro) {
      insertar((CajaAhorro) elemento, usuario);
    } else if (elemento instanceof CuentaCorriente) {
      insertar((CuentaCorriente) elemento, usuario);
    }
  }

  public void insertar(CuentaCorriente elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      daoEvento.insertar(
          new Evento(TipoEvento.CREACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  public void insertar(CajaAhorro elemento, UsuarioCliente usuario) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.insertar(elemento, usuario);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
  }

  @Override
  public Cuenta consultar(Integer id) throws ServiceException {
    return new ServiceTemplate<Cuenta>().execute(() -> {
      Cuenta cuenta = dao.consultar(id);
      if (cuenta instanceof CajaAhorro) {
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, Integer.toString(id)),
            contexto);
      } else if (cuenta instanceof CuentaCorriente) {
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(id)),
            contexto);
      }
      return cuenta;
    });
  }

  public Cuenta consultarConTransferencia(Integer id) throws ServiceException {
    return new ServiceTemplate<Cuenta>().execute(() -> {
      Cuenta cuenta = dao.consultar(id);
      if (cuenta instanceof CajaAhorro) {
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CAJA_AHORRO, Integer.toString(id)),
            contexto);
      } else if (cuenta instanceof CuentaCorriente) {
        daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(id)),
            contexto);
      }
      List<Transferencia> transferencias = daoTransferencia.consultarTodos(cuenta);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.TRANSFERENCIA, Integer.toString(id)),
          contexto);
      for (Transferencia transferencia : transferencias) {
        cuenta.agregarTransferencia(transferencia);
      }
      return cuenta;
    });
  }

  @Override
  public List<Cuenta> consultarTodos() throws ServiceException {
    return new ServiceTemplate<List<Cuenta>>().execute(() -> {
      List<Cuenta> cuentas = dao.consultarTodos();
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA, "-1"),
          contexto);
      return cuentas;
    });
  }

  public List<Cuenta> consultarTodos(UsuarioCliente usuario) throws ServiceException {
    return new ServiceTemplate<List<Cuenta>>().execute(() -> {
      List<Cuenta> cuentas = dao.consultarTodos(usuario);
      daoEvento.insertar(new Evento(TipoEvento.CONSULTA, TipoObjeto.CUENTA, "-1"),
          contexto);
      return cuentas;
    });
  }

  @Override
  public void eliminar(Integer id) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.eliminar(id);
      daoEvento.insertar(new Evento(TipoEvento.ELIMINACION, TipoObjeto.CUENTA, Integer.toString(id)),
          contexto);
      return null;
    });
  }

  @Override
  public void modificar(Cuenta elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      if (elemento instanceof CajaAhorro) {
        daoEvento.insertar(
            new Evento(TipoEvento.MODIFICACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
            contexto);
      } else if (elemento instanceof CuentaCorriente) {
        daoEvento.insertar(
            new Evento(TipoEvento.MODIFICACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
            contexto);
      }
      return null;
    });
  }

  public void modificarConTransferencia(Cuenta elemento) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      dao.modificar(elemento);
      if (elemento instanceof CajaAhorro) {
        daoEvento.insertar(
            new Evento(TipoEvento.MODIFICACION, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
            contexto);
      } else if (elemento instanceof CuentaCorriente) {
        daoEvento.insertar(
            new Evento(TipoEvento.MODIFICACION, TipoObjeto.CUENTA_CORRIENTE, Integer.toString(elemento.getId())),
            contexto);
      }
      for (Transferencia transferencia : elemento.getTransferencias()) {
        if (daoTransferencia.consultar(transferencia.getId()) == null) {
          daoTransferencia.insertar(elemento, transferencia);
          daoEvento.insertar(
              new Evento(TipoEvento.CREACION, TipoObjeto.TRANSFERENCIA, Integer.toString(transferencia.getId())),
              contexto);
        }
      }
      return null;
    });
  }

  // Movimientos
  public double extraer(Cuenta elemento, String monto) throws ServiceException {
    try {
      if (monto == null || monto.isEmpty()) {
        throw new ServiceException("El monto no puede ser nulo o vacio");
      }

      Double.parseDouble(monto);

      return extraer(elemento, Double.parseDouble(monto));
    } catch (NumberFormatException e) {
      throw new ServiceException("El monto debe ser un numero");
    }
  }

  public double extraer(Cuenta elemento, double monto) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      elemento.extraer(monto);
      dao.modificar(elemento);
      daoTransferencia.insertar(
          new Transferencia(LocalDate.now(), monto, elemento.getMoneda(), "Extracción", elemento,
              TipoOperacion.EXTRACCION, false));
      daoEvento.insertar(new Evento(TipoEvento.DEBITO, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
    return monto;
  }

  public double depositar(Cuenta elemento, String monto) throws ServiceException {
    try {
      if (monto == null || monto.isEmpty()) {
        throw new ServiceException("El monto no puede ser nulo o vacio");
      }

      Double.parseDouble(monto);

      return depositar(elemento, Double.parseDouble(monto));
    } catch (NumberFormatException e) {
      throw new ServiceException("El monto debe ser un numero");
    }
  }

  public double depositar(Cuenta elemento, double monto) throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      elemento.depositar(monto);
      dao.modificar(elemento);
      daoTransferencia.insertar(
          new Transferencia(LocalDate.now(), monto, elemento.getMoneda(), "Depósito", elemento,
              TipoOperacion.DEPOSITO, true));
      daoEvento.insertar(new Evento(TipoEvento.CREDITO, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return null;
    });
    return monto;
  }

  // Funcionalidades
  public void modificarCuenta(Cuenta cuenta, String alias, String porcentajeInteres, String limiteGiro)
      throws ServiceException {
    try {
      cuenta.setAlias(alias);
      if (cuenta instanceof CajaAhorro) {
        ((CajaAhorro) cuenta).setPorcentajeInteres(Double.parseDouble(porcentajeInteres));
      } else if (cuenta instanceof CuentaCorriente) {
        ((CuentaCorriente) cuenta).setLimiteGiro(Double.parseDouble(limiteGiro));
      }
      this.modificar(cuenta);
      daoEvento.insertar(new Evento(TipoEvento.MODIFICACION, TipoObjeto.CUENTA, Integer.toString(cuenta.getId())),
          contexto);
    } catch (DAOException e) {
      throw new ServiceException("Fallo al modificar la cuenta en: " + this.getClass().getName());
    }
  }

  public void agregarCuenta(UsuarioCliente usuario, String cbuString, String limiteOPorcentajeString,
      String monedaString, String aliasString, String tipoCuentaString, String tipoCajaAhorro,
      String tipoCuentaCorriente) throws ServiceException {
    try {
      Cuenta cuenta = null;
      if (cbuString.isEmpty() ||
          limiteOPorcentajeString.isEmpty() ||
          monedaString.isEmpty() ||
          aliasString.isEmpty() ||
          tipoCuentaString.isEmpty()) {
        throw new ServiceException("Debe completar todos los campos");
      }

      if (cbuString.length() != 22 || !cbuString.matches("\\d+")) {
        throw new ServiceException("CBU debe ser un número de 22 caracteres");
      }

      Double.parseDouble(limiteOPorcentajeString);

      Moneda moneda = daoMoneda.consultar(monedaString);
      if (moneda == null) {
        throw new ServiceException("Moneda no encontrada");
      }

      TipoObjeto tipoObjeto = null;

      if (tipoCuentaString.equals(tipoCajaAhorro)) {
        tipoObjeto = TipoObjeto.CAJA_AHORRO;
        cuenta = new CajaAhorro(moneda, aliasString, cbuString, Double.parseDouble(limiteOPorcentajeString));
      } else if (tipoCuentaString.equals(tipoCuentaCorriente)) {
        tipoObjeto = TipoObjeto.CUENTA_CORRIENTE;
        cuenta = new CuentaCorriente(moneda, aliasString, cbuString, Double.parseDouble(limiteOPorcentajeString));
      }
      this.insertar(cuenta, usuario);
      daoEvento.insertar(new Evento(TipoEvento.CREACION, tipoObjeto, Integer.toString(cuenta.getId())),
          contexto);
    } catch (DAOException e) {
      throw new ServiceException("Fallo al agregar la cuenta en: " + this.getClass().getName());
    } catch (NumberFormatException e) {
      throw new ServiceException("El límite o porcentaje de interés deben ser un número");
    }
  }

  public void transferir(String cuentaSeleccionadaString, String montoString, String destinoString)
      throws ServiceException {
    try {
      if (cuentaSeleccionadaString.isEmpty() || montoString.isEmpty() || destinoString.isEmpty()) {
        throw new ServiceException("Debe completar todos los campos");
      }
      String[] partes = cuentaSeleccionadaString.split(" ");
      Moneda moneda = daoMoneda.consultar(partes[1].replace(":", ""));
      if (moneda == null) {
        throw new ServiceException("Moneda no encontrada");
      }

      Double.parseDouble(montoString);

      List<Cuenta> cuentas = this.consultarTodos();
      Cuenta cuentaOrigen;

      for (Cuenta cuentaDestino : cuentas) {
        // Seguir hasta encontrar la cuenta
        if (!(Integer.toString(cuentaDestino.getId()).equals(destinoString)
            || cuentaDestino.getCbu().equals(destinoString)
            || cuentaDestino.getAlias().equals(destinoString)))
          continue;

        if (!(cuentaDestino.getMoneda().getCodigo().equals(moneda.getCodigo()))) {
          throw new ServiceException("Las cuentas no son de la misma moneda");
        }

        Double montoD = Double.valueOf(montoString);
        // Encontrar cuenta origen
        cuentaOrigen = this.consultar(Integer.valueOf(partes[2]));
        cuentaOrigen.extraer(montoD);
        cuentaDestino.depositar(montoD);

        Transferencia transferencia = new Transferencia(LocalDate.now(), montoD,
            moneda, "Transferencia", cuentaDestino, false);
        cuentaOrigen.agregarTransferencia(transferencia);

        // Actualizar cuenta origen en DB y agregar transferencia
        this.modificarConTransferencia(cuentaOrigen);

        // Actualizar cuenta destino en DB
        this.modificar(cuentaDestino);

        return;
      }
      throw new ServiceException("Cuenta no encontrada");

    } catch (NumberFormatException e) {
      throw new ServiceException("El monto debe ser un numero");
    } catch (DAOException e) {
      System.out.println(e.getMessage());
      throw new ServiceException("Fallo al transferir en: " + this.getClass().getName());
    }
  }

  // Reporte
  public List<String> generarReporteMovimientos(Cuenta elemento) throws ServiceException {
    try {
      List<String> reporte = new ArrayList<String>();
      reporte.add("id,fecha,monto,concepto,moneda,cbu_origen,cbu_destino\n");
      for (Transferencia transferencia : elemento.getTransferencias()) {
        if (transferencia.getEntrante())
          reporte.add(transferencia.getId() + "," + transferencia.getFecha() + "," + transferencia.getMonto() + ","
              + transferencia.getConcepto() + "," + transferencia.getMoneda() + ","
              + transferencia.getCuentaTercero().getCbu() + "," + elemento.getCbu() + "\n");
        else
          reporte.add(transferencia.getId() + "," + transferencia.getFecha() + "," + transferencia.getMonto() + ","
              + transferencia.getConcepto() + "," + transferencia.getMoneda() + "," + elemento.getCbu() + ","
              + transferencia.getCuentaTercero().getCbu() + "\n");
      }
      daoEvento.insertar(new Evento(TipoEvento.REPORTE, TipoObjeto.CAJA_AHORRO, Integer.toString(elemento.getId())),
          contexto);
      return reporte;
    } catch (DAOException e) {
      throw new ServiceException("Fallo al generar reporte de movimientos en: " + this.getClass().getName());
    }
  }

  // Intereses
  public void aplicarIntereses() throws ServiceException {
    new ServiceTemplate<Void>().execute(() -> {
      List<Cuenta> cuentas = dao.consultarTodos();
      for (Cuenta cuenta : cuentas) {
        if (cuenta instanceof CajaAhorro) {
          CajaAhorro cajaAhorro = (CajaAhorro) cuenta;
          double monto = cajaAhorro.calcularInteres(cajaAhorro.getSaldo());
          cajaAhorro.aplicarInteres();
          dao.modificar(cajaAhorro);
          daoTransferencia.insertar(
              new Transferencia(LocalDate.now(), monto, cajaAhorro.getMoneda(), "Interés",
                  cajaAhorro, TipoOperacion.CREDITO, true));
          daoEvento.insertar(
              new Evento(TipoEvento.CREDITO, TipoObjeto.CAJA_AHORRO, Integer.toString(cajaAhorro.getId())),
              contexto);
        }
      }
      return null;
    });
  }
}
