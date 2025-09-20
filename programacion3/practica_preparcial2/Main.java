package programacion3.practica_preparcial2;

import programacion3.practica_preparcial2.entidades.Gimnasio;
import programacion3.practica_preparcial2.entidades.Pileta;
import programacion3.practica_preparcial2.entidades.ActividadGeneral;
import programacion3.practica_preparcial2.entidades.EntrenadorPersonal;

public class Main {
  public static void main(String[] args) {
    Gimnasio gym = new Gimnasio("Siempre en Forma");
    int currentSocio = 1;

    // Socio 1
    System.out.println("1: Agregando socio");
    gym.agregarSocio(currentSocio, "Foo Bar", 10000, new Pileta("1", "Pileta 1", 1500));
    System.out.println("1: Inscribiendo socio a nueva actividad");
    gym.inscribirSocioActividad(currentSocio, new ActividadGeneral("1", "AG1", 2, 500));
    System.out.println("1: Inscribiendo socio a nueva actividad 2");
    gym.inscribirSocioActividad(currentSocio, new EntrenadorPersonal("1", "Bar Foo", 1000, 16));
    System.out.println("1: Calculando total de cuota del socio");
    System.out.println("1: Importe: " + gym.importeSocio(gym.consultarSocio(currentSocio)));
    System.out.println("1: Calculando total de ingresos");
    System.out.println("1: Total: " + gym.ingresoTotalPorPagoCuotas());
    
    System.out.println("==================");

    // Socio 2
    currentSocio++;
    System.out.println("2: Agregando socio");
    gym.agregarSocio(currentSocio, "Foobar Barfoo", 15000, new Pileta("2", "Pileta 2", 900));
    System.out.println("2: Inscribiendo socio a nueva actividad");
    gym.inscribirSocioActividad(currentSocio, new EntrenadorPersonal("2", "Lorem Ipsum", 850, 10));
    System.out.println("2: Inscribiendo socio a nueva actividad 2");
    gym.inscribirSocioActividad(currentSocio, new ActividadGeneral("2", "AG2", 5, 500));
    System.out.println("2: Calculando total de cuota del socio");
    System.out.println("2: Importe: " + gym.importeSocio(gym.consultarSocio(currentSocio)));
    System.out.println("2: Calculando total de ingresos");
    System.out.println("2: Total: " + gym.ingresoTotalPorPagoCuotas());
  }
}
