/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.Application;

import appCafyd.BusinessLogicLayer.Models.*;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import java.time.LocalDate;
import java.util.Scanner;

public class Application {

    private static Scanner sc = new Scanner(System.in);
    private static UsuarioService service = new UsuarioService();
    private static Usuario usuarioLogueado = null;

    public static void main(String[] args) {
        menuPrincipal();
    }

    // INICIO
    private static void menuPrincipal() {
        int opt;
        do {
            System.out.println("\n========================================");
            System.out.println("        ¡BIENVENID@ al RETO CAFYD!     ");
            System.out.println("========================================");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opt = leerInt();

            switch (opt) {
                case 1: pantallaRegistro(); break;
                case 2: pantallaLogin();    break;
                case 0: System.out.println("¡Hasta pronto!"); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opt != 0);
    }

    // DATOS PERSONALES (REGISTRO)
    private static void pantallaRegistro() {
        System.out.println("\n--- DATOS PERSONALES ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        int edad = 0;
        do {
            System.out.print("Edad (1-120): ");
            edad = leerInt();
            if (edad < 1 || edad > 120) System.out.println("Edad no válida.");
        } while (edad < 1 || edad > 120);

        int s = 0;
        do {
            System.out.print("Sexo (1. Hombre / 2. Mujer): ");
            s = leerInt();
            if (s != 1 && s != 2) System.out.println("Opción no válida.");
        } while (s != 1 && s != 2);
        eCategory sexo = (s == 1) ? eCategory.Hombre : eCategory.Mujer;

        String user = "";
        do {
            System.out.print("Username: ");
            user = sc.nextLine().trim();
            if (user.isEmpty()) System.out.println("El username no puede estar vacío.");
        } while (user.isEmpty());

        String pass = "";
        do {
            System.out.print("Contraseña: ");
            pass = sc.nextLine().trim();
            if (pass.isEmpty()) System.out.println("La contraseña no puede estar vacía.");
        } while (pass.isEmpty());

        System.out.print("¿Mostrar recuento de calorías? (S/N): ");
        boolean calorias = sc.nextLine().equalsIgnoreCase("S");

        Usuario nuevo = new Usuario(user, pass, nombre, apellido, edad, sexo, calorias);

        if (service.registrar(nuevo)) {
            System.out.println("\n ¡Registrado! El reto de 30 días empieza HOY. ¡Mucho ánimo!");
            usuarioLogueado = nuevo;
            pantallaInicial();
        } else {
            System.out.println("\n Ese username ya existe. Prueba con otro.");
        }
    }

    // INICIAR SESION
    private static void pantallaLogin() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        usuarioLogueado = service.login(user, pass);

        if (usuarioLogueado == null) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }

        // Comprobar si se ha perdidio la racha
        LocalDate ayer = LocalDate.now().minusDays(1);
        boolean perdioRacha = service.verificarYResetearRacha(usuarioLogueado);

        if (perdioRacha) {
            // Aviso perdida de racha
            System.out.println("\n========================================");
            System.out.println("         HAS PERDIDO LA RACHA      ");
            System.out.println("  No te desanimes. Hoy empieza una      ");
            System.out.println("  racha aún más fuerte.                 ");
            System.out.println("========================================");

            // Formulario dia sin hacer
            // Solo si ayer era un día válido del reto (dentro de los 30 días)
            Entreno entrenoAyer = service.buscarEntrenoPorFecha(usuarioLogueado, ayer);
            if (entrenoAyer == null) {
                // No hay registro de ayer --> pedir el formulario
                formularioDiaSinHacer(ayer);
            }
        }

        pantallaInicial();
    }

    // FORMULARIO DIA SIN HACER
    private static void formularioDiaSinHacer(LocalDate fechaDia) {
        System.out.println("\n--- AYER NO HICISTE EL RETO... ---");
        System.out.println("Fecha: " + fechaDia);
        System.out.println("¿Por qué no has hecho el reto?");
        System.out.println("  1. He tenido un día ocupado");
        System.out.println("  2. No me encontraba bien");
        System.out.println("  3. Otro");
        System.out.print("Opción: ");
        int optMotivo = leerInt();

        FormularioDiaSinHacer.MotivoDia motivo;
        switch (optMotivo) {
            case 1:  
                motivo = FormularioDiaSinHacer.MotivoDia.DIA_OCUPADO;        
                break;
            case 2:  
                motivo = FormularioDiaSinHacer.MotivoDia.NO_ENCONTRABA_BIEN; 
                break;
            default: 
                motivo = FormularioDiaSinHacer.MotivoDia.OTRO;               
                break;
        }

        System.out.println("\nEstado anímico:");
        System.out.println("  1. Muy mal");
        System.out.println("  2. Mal");
        System.out.println("  3. Regular");
        System.out.println("  4. Bien");
        System.out.println("  5. Muy bien");
        System.out.print("Opción: ");
        int optAnimo = leerInt();

        FormularioDiaSinHacer.EstadoAnimico estado;
        switch (optAnimo) {
            case 1:  
                estado = FormularioDiaSinHacer.EstadoAnimico.MUY_MAL;  
                break;
            case 2:  
                estado = FormularioDiaSinHacer.EstadoAnimico.MAL;      
                break;
            case 3:  
                estado = FormularioDiaSinHacer.EstadoAnimico.REGULAR;  
                break;
            case 4:  
                estado = FormularioDiaSinHacer.EstadoAnimico.BIEN;     
                break;
            default: 
                estado = FormularioDiaSinHacer.EstadoAnimico.MUY_BIEN; 
                break;
        }

        FormularioDiaSinHacer form = new FormularioDiaSinHacer(fechaDia);
        form.registrarMotivo(motivo);
        form.registrarEstadoAnimico(estado);

        // Marcar ese día en rojo en el seguimiento
        Entreno entrenoFallado = new Entreno(fechaDia, calcularDiaReto(fechaDia));
        // completado = false --> comprobarRegistro lo pondrá en rojo
        usuarioLogueado.seguimiento.añadirEntreno(entrenoFallado);
        usuarioLogueado.seguimiento.comprobarRegistro(entrenoFallado);

        System.out.println("\n Formulario enviado.");
    }

    // PANTALLA INICIAL (CALENDARIO)
    private static void pantallaInicial() {
        // Comprobar si el reto de 30 dias ya ha terminado
        int diaReto = usuarioLogueado.getDiaReto();
        if (diaReto == 0) {
            System.out.println("\n ¡HAS COMPLETADO EL RETO DE 30 DÍAS! ¡Enhorabuena, " + usuarioLogueado.nombre + "!");
            System.out.print("Pulsa ENTER para salir...");
            sc.nextLine();
            usuarioLogueado = null;
            return;
        }

        String input;
        do {
            mostrarCalendario();
            System.out.println("Día del reto: " + diaReto + "/30  |  Racha: " + usuarioLogueado.racha + " días");
            System.out.println("\nOpciones:");
            System.out.println("  [número] Seleccionar día del mes");
            System.out.println("  P        Ver perfil");
            System.out.println("  0        Cerrar sesión");
            System.out.print("\nOpción: ");
            input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("P")) {
                pantallaPerfil();
            } else if (input.equals("0")) {
                System.out.println("Sesión cerrada. ¡Hasta pronto, " + usuarioLogueado.nombre + "!");
                usuarioLogueado = null;
                break;
            } else{
                int dia = Integer.parseInt(input);
                LocalDate fecha = LocalDate.now().withDayOfMonth(dia);
                gestionarDia(fecha);
                diaReto = usuarioLogueado.getDiaReto();

            }
        } while(true);
    }

    // Calendario de los 30 días del reto
    private static void mostrarCalendario() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = usuarioLogueado.fechaRegistro;
        int diasEnMes = hoy.lengthOfMonth();
        int primerDiaSemana = hoy.withDayOfMonth(1).getDayOfWeek().getValue();

        System.out.println("\n--------------------------------------");
        System.out.printf("         %s %d%n", hoy.getMonth(), hoy.getYear());
        System.out.println("----------------------------------------");
        System.out.println("  L    M    X    J    V    S    D");

        for (int i = 1; i < primerDiaSemana; i++) System.out.print("     ");

        for (int dia = 1; dia <= diasEnMes; dia++) {
            LocalDate fecha = LocalDate.of(hoy.getYear(), hoy.getMonth(), dia);
            String etiqueta = null;

            // Solo mostrar dias dentro del reto (desde fechaRegistro hasta +29 días)
            LocalDate finReto = inicio.plusDays(29);
            boolean esDiaDeReto = !fecha.isBefore(inicio) && !fecha.isAfter(finReto);

            if (!esDiaDeReto) {
                etiqueta = "[]"; // fuera del reto
            } else if (fecha.isAfter(hoy)) {
                etiqueta = "[X]"; // futuro
            } else if (usuarioLogueado.seguimiento.diasVerdes.contains(fecha)) {
                etiqueta = "[V]"; // completado
            } else if (usuarioLogueado.seguimiento.diasRojos.contains(fecha)) {
                etiqueta = "[R]"; // fallado
            } else if (fecha.equals(hoy)) {
                etiqueta = "["+dia+"]"; // hoy pendiente
            }
            System.out.printf(" %-4s", etiqueta);
            if ((dia + primerDiaSemana - 1) % 7 == 0) System.out.println();
        }

        System.out.println("\n----------------------------------------");
        System.out.println("[V] Hecho  [R] Fallado  [dia] Hoy  [X]Futuro  [] Fuera del reto");
    }


    // GESTION DEL DIA SELECCIONADO
    private static void gestionarDia(LocalDate fecha) {
        LocalDate hoy = LocalDate.now();
        LocalDate finReto = usuarioLogueado.fechaRegistro.plusDays(29);

        // Fuera del reto
        if (fecha.isBefore(usuarioLogueado.fechaRegistro) || fecha.isAfter(finReto)) {
            System.out.println("Ese día no forma parte del reto.");
            return;
        }

        // Dia futuro
        if (fecha.isAfter(hoy)) {
            System.out.println("Todavía no ha llegado ese día.");
            return;
        }

        Entreno entreno = service.buscarEntrenoPorFecha(usuarioLogueado, fecha);

        if (fecha.isBefore(hoy)) {
            // Día pasado
            if (entreno != null && entreno.completado) {
                mostrarResumenEntreno(entreno);
            } else {
                System.out.println("No completaste ese día. No se puede recuperar.");
            }
            return;
        }

        // Dia de hoy
        if (entreno != null && entreno.completado) {
            System.out.println("Ya completaste el entreno de hoy!");
            mostrarResumenEntreno(entreno);
        } else {
            realizarEntreno(fecha);
        }
    }

    //RESUMEN ENTRENO YA COMPLETADO
    private static void mostrarResumenEntreno(Entreno entreno) {
        System.out.println("\n--- ENTRENO COMPLETADO · Día " + entreno.diaReto + "/30 · " + entreno.fecha + " ---");
        System.out.println("Foto      : " + entreno.foto);
        System.out.println("Dificultad: " + entreno.dificultad + "/5");
        System.out.println("Ejercicios realizados:");
        for (Ejercicio ej : entreno.ejercicios) {
            System.out.println("  · " + ej.nombreEj + " — " + ej.series + " series x " + ej.repeticiones + " reps");
        }
        System.out.print("\nPulsa ENTER para volver...");
        sc.nextLine();
    }

    //REALIZAR ENTRENO DEL DIA
    private static void realizarEntreno(LocalDate fecha) {
        int diaReto = calcularDiaReto(fecha);
        Entreno entreno = new Entreno(fecha, diaReto);
        cargarEjerciciosDelDia(entreno, diaReto);

        System.out.println("\n---------------------------------------");
        System.out.println("  ENTRENO DE HOY · Día " + diaReto + "/30");
        System.out.println("-----------------------------------------");
        System.out.println("Ejercicios de hoy:");
        for (Ejercicio ej : entreno.ejercicios) {
            System.out.println("  · " + ej.nombreEj + " — " + ej.series + "x" + ej.repeticiones);
        }
        System.out.print("\n¿Empezamos? (S/N): ");
        if (!sc.nextLine().equalsIgnoreCase("S")) return;

        // Ejercicio por ejercicio
        for (Ejercicio ej : entreno.ejercicios) {
            boolean completado = mostrarEjercicio(ej);
            if (!completado) {
                System.out.println("\nHas parado el entreno. ¡Mañana más!");
                return;
            }
        }

        // Todos completados
        System.out.println("ENTRENO TERMINADO!");

        //Foto (simulada en consola)
        String foto = "";
        do {
            System.out.print("Nombre de la foto (ej: foto_dia" + diaReto + ".jpg): ");
            foto = sc.nextLine().trim();
            if (foto.isEmpty()) System.out.println("La foto no puede estar vacía.");
        } while (foto.isEmpty());

        //Formulario post-entreno
        int dificultad = 0;
        do {
            System.out.print("¿Cuánto esfuerzo ha supuesto? (1-5): ");
            dificultad = leerInt();
            if (dificultad < 1 || dificultad > 5) System.out.println("Valor entre 1 y 5.");
        } while (dificultad < 1 || dificultad > 5);

        System.out.print("¿Has podido hacer todos los ejercicios? (S/N): ");
        boolean todosHechos = sc.nextLine().equalsIgnoreCase("S");

        // Rellenar formulario y guardar
        FormularioEntreno formulario = new FormularioEntreno(entreno, foto);
        formulario.registrarDificultad(dificultad);
        formulario.registrarCompletados(todosHechos);

        entreno.foto = foto;
        entreno.dificultad = dificultad;

        service.finalizarEntreno(usuarioLogueado, formulario);
    }

    // EJERCICIO INDIVIDUAL
    private static boolean mostrarEjercicio(Ejercicio ej) {
        System.out.println("\n----------------------------------------");
        System.out.println("EJERCICIO: " + ej.nombreEj.toUpperCase());
        System.out.println("Video/Foto: " + ej.descripcionIm);
        System.out.println("Series: " + ej.series + "  |  Reps: " + ej.repeticiones);

        if (!ej.fallosComunes.isEmpty()) {
            System.out.println("Fallos comunes:");
            for (String fallo : ej.fallosComunes) {
                System.out.println("   - " + fallo);
            }
        }

        // Contacto dudas
        System.out.print("¿Tienes dudas? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.println("Contacta con nuestros entrenadores:");
            System.out.println(" 666 66 66 66 |  hola.entreno@gmail.com");
        }

        System.out.print("¿Has completado este ejercicio? (S/N): ");
        return sc.nextLine().equalsIgnoreCase("S");
    }

    // PERFIL
    private static void pantallaPerfil() {
        int opt;
        do {
            System.out.println("\n--- PERFIL ---");
            System.out.println("Username  : " + usuarioLogueado.nombreUsuario);
            System.out.println("Nombre    : " + usuarioLogueado.nombre + " " + usuarioLogueado.apellido);
            System.out.println("Edad      : " + usuarioLogueado.edad);
            System.out.println("Sexo      : " + usuarioLogueado.sexo);
            System.out.println("Racha     : " + usuarioLogueado.racha + " días");
            System.out.println("Día reto  : " + usuarioLogueado.getDiaReto() + "/30");
            System.out.println("Inicio    : " + usuarioLogueado.fechaRegistro);
            System.out.println("\n1. Modificar nombre");
            System.out.println("2. Modificar apellido");
            System.out.println("3. Modificar username");
            System.out.println("4. Modificar contraseña");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opt = leerInt();

            switch (opt) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    usuarioLogueado.nombre = sc.nextLine(); break;
                case 2:
                    System.out.print("Nuevo apellido: ");
                    usuarioLogueado.apellido = sc.nextLine(); break;
                case 3:
                    System.out.print("Nuevo username: ");
                    usuarioLogueado.nombreUsuario = sc.nextLine(); break;
                case 4:
                    System.out.print("Nueva contraseña: ");
                    usuarioLogueado.setContraseña(sc.nextLine()); break;
            }

            if (opt >= 1 && opt <= 4) {
                service.actualizarUsuario(usuarioLogueado);
                System.out.println("Cambios guardados.");
            }
        } while (opt != 0);
    }

    // EJERCICIOS POR DIA DEL RETO (dias 1-30) *****CAMBIAR CON LOS AQUE NOS DEL LOS DE CAFYD
    private static void cargarEjerciciosDelDia(Entreno entreno, int diaReto) {
        switch (diaReto) {
            case 1:
                entreno.añadirEjercicio(ej("Sentadillas",      "sentadilla.mp4",  3, 10, "Rodillas hacia adentro"));
                entreno.añadirEjercicio(ej("Flexiones",        "flexiones.mp4",   3, 10, "Codos muy abiertos"));
                entreno.añadirEjercicio(ej("Plancha",          "plancha.mp4",     3, 20, "Cadera arriba o abajo"));
                break;
            case 2:
                entreno.añadirEjercicio(ej("Zancadas",         "zancada.mp4",     3, 10, "Pecho inclinado"));
                entreno.añadirEjercicio(ej("Fondos de tríceps","fondos.mp4",       3, 10, "Bajar poco"));
                entreno.añadirEjercicio(ej("Puente de glúteos","puente.mp4",       3, 15, "No subir las caderas del todo"));
                break;
            case 3:
                entreno.añadirEjercicio(ej("Burpees",          "burpees.mp4",     3,  8, "No extender bien arriba"));
                entreno.añadirEjercicio(ej("Mountain climbers","mountain.mp4",    3, 20, "Caderas altas"));
                entreno.añadirEjercicio(ej("Gemelos",          "gemelos.mp4",     3, 20, "No subir del todo"));
                break;
            case 4:
                entreno.añadirEjercicio(ej("Sentadilla sumo",  "sumo.mp4",        4, 12, "Espalda encorvada"));
                entreno.añadirEjercicio(ej("Press de hombros", "hombros.mp4",     3, 10, "Arquear la espalda baja"));
                entreno.añadirEjercicio(ej("Abdominales",      "abdominales.mp4", 3, 15, "Tirar del cuello"));
                break;
            case 5:
                entreno.añadirEjercicio(ej("Flexiones inclinadas","flexInc.mp4",  3, 12, "Codos muy abiertos"));
                entreno.añadirEjercicio(ej("Sentadilla búlgara",  "bulgara.mp4",  3, 10, "Rodilla delantera pasa el pie"));
                entreno.añadirEjercicio(ej("Plancha lateral",     "planchaLat.mp4",3,20, "Cadera que cae"));
                break;
            // Días 6-30: patrón rotativo para no dejar el switch vacío
            default:
                // Rotamos entre 3 bloques según el día
                int bloque = diaReto % 3;
                if (bloque == 0) {
                    entreno.añadirEjercicio(ej("Sentadillas",   "sentadilla.mp4",  3 + diaReto/10, 10 + diaReto/5, "Rodillas hacia adentro"));
                    entreno.añadirEjercicio(ej("Flexiones",     "flexiones.mp4",   3 + diaReto/10, 10 + diaReto/5, "Codos muy abiertos"));
                    entreno.añadirEjercicio(ej("Plancha",       "plancha.mp4",     3, 20 + diaReto, "Cadera"));
                } else if (bloque == 1) {
                    entreno.añadirEjercicio(ej("Zancadas",      "zancada.mp4",     3 + diaReto/10, 10 + diaReto/5, "Pecho inclinado"));
                    entreno.añadirEjercicio(ej("Burpees",       "burpees.mp4",     3, 8 + diaReto/5, "No extender"));
                    entreno.añadirEjercicio(ej("Puente glúteos","puente.mp4",      3, 15 + diaReto/5, "No subir del todo"));
                } else {
                    entreno.añadirEjercicio(ej("Fondos",        "fondos.mp4",      3 + diaReto/10, 10 + diaReto/5, "Bajar poco"));
                    entreno.añadirEjercicio(ej("Mountain climb","mountain.mp4",    3, 20 + diaReto, "Caderas altas"));
                    entreno.añadirEjercicio(ej("Abdominales",   "abds.mp4",        3, 15 + diaReto/5, "Tirar del cuello"));
                }
                break;
        }
    }

    // Funcion para crear ejercicio con un fallo comun
    private static Ejercicio ej(String nombre, String video, int series, int reps, String fallo) {
        Ejercicio e = new Ejercicio(nombre, video, series, reps);
        e.añadirFallo(fallo);
        return e;
    }

    // UTILIDADES
    // Calcula el numero de dia del reto para una fecha dada
    private static int calcularDiaReto(LocalDate fecha) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(
                usuarioLogueado.fechaRegistro, fecha);
        return (int) dias + 1;
    }

    // Leer entero de consola sin romper el Scanner
    private static int leerInt() {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Introduce un número: ");
            }
        }
    }
}
