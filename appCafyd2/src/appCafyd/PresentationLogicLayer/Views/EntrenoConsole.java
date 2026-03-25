package appCafyd.PresentationLogicLayer.Views;

import appCafyd.BusinessLogicLayer.Models.Ejercicio;
import appCafyd.BusinessLogicLayer.Models.Entreno;
import java.util.ArrayList;
import java.util.Scanner;

public class EntrenoConsole implements IEntrenoView {

    private Scanner sc = new Scanner(System.in);
    private Runnable finalizarEntrenoAction;

    private ArrayList<Ejercicio> ejercicios = new ArrayList<>();
    private int diaReto;

    // open() solo muestra la lista de ejercicios, el bucle lo controla el presenter
    @Override
    public void open() {
        System.out.println("\nENTRENO DE HOY - Dia " + diaReto + "/30");
        System.out.println("Ejercicios de hoy:");
        for (Ejercicio ej : ejercicios) {
            System.out.println("  - " + ej.nombreEj + " - " + ej.series + "x" + ej.repeticiones);
        }
    }

    @Override
    public void close() {
        System.out.println("========================================");
    }

    @Override
    public void setFinalizarEntrenoAction(Runnable finalizarEntrenoAction) {
        this.finalizarEntrenoAction = finalizarEntrenoAction;
    }

    @Override
    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public void setDiaReto(int diaReto) {
        this.diaReto = diaReto;
    }

    @Override
    public boolean confirmarInicio() {
        System.out.print("\nEmpezamos? (S/N): ");
        return sc.nextLine().equalsIgnoreCase("S");
    }

    // Pantalla 4.1: muestra el ejercicio y devuelve si lo completo
    @Override
    public boolean mostrarEjercicio(Ejercicio ej) {
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

        System.out.print("Tienes dudas? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            System.out.println("Contacta con nuestros entrenadores:");
            System.out.println("   Telefono: 666 55 55 55");
            System.out.println("   Correo: fida.entreno@gmail.com");
        }

        System.out.print("Has completado este ejercicio? (S/N): ");
        return sc.nextLine().equalsIgnoreCase("S");
    }

    // Pantalla 6 + 7: foto y formulario post-entreno
    @Override
    public EntrenoForm getEntrenoForm(int diaReto) {
        String foto = "";
        do {
            System.out.print("Nombre de la foto (ej: foto_dia" + diaReto + ".jpg): ");
            foto = sc.nextLine().trim();
            if (foto.isEmpty()) System.out.println("La foto no puede estar vacia.");
        } while (foto.isEmpty());

        int dificultad = 0;
        do {
            System.out.print("Cuanto esfuerzo ha supuesto? (1-5): ");
            dificultad = leerInt();
            if (dificultad < 1 || dificultad > 5) System.out.println("Valor entre 1 y 5.");
        } while (dificultad < 1 || dificultad > 5);

        System.out.print("Has podido hacer todos los ejercicios? (S/N): ");
        boolean todosHechos = sc.nextLine().equalsIgnoreCase("S");

        return new EntrenoForm(foto, dificultad, todosHechos);
    }

    // Pantalla 3.2: resumen entreno ya completado
    @Override
    public void mostrarResumenEntreno(Entreno entreno) {
        System.out.println("\nENTRENO COMPLETADO - Dia " + entreno.diaReto + "/30 - " + entreno.fecha);
        System.out.println("Foto      : " + entreno.foto);
        System.out.println("Dificultad: " + entreno.dificultad + "/5");
        System.out.println("Ejercicios realizados:");
        for (Ejercicio ej : entreno.ejercicios) {
            System.out.println("  - " + ej.nombreEj + " - " + ej.series + " series x " + ej.repeticiones + " reps");
        }
        System.out.print("\nPulsa ENTER para volver...");
        sc.nextLine();
    }

    // Pantalla 2.3: formulario dia sin hacer
    @Override
    public FormularioDiaSinHacerForm getFormularioDiaSinHacer() {
        System.out.println("\nAYER NO HICISTE EL RETO...");
        System.out.println("Por que no has hecho el reto?");
        System.out.println("  1. He tenido un dia ocupado");
        System.out.println("  2. No me encontraba bien");
        System.out.println("  3. Otro");
        System.out.print("Opcion: ");
        int motivo = leerInt();
        if (motivo < 1 || motivo > 3) motivo = 3;

        System.out.println("\nEstado animico:");
        System.out.println("  1. Muy mal");
        System.out.println("  2. Mal");
        System.out.println("  3. Regular");
        System.out.println("  4. Bien");
        System.out.println("  5. Muy bien");
        System.out.print("Opcion: ");
        int animo = leerInt();
        if (animo < 1 || animo > 5) animo = 3;

        return new FormularioDiaSinHacerForm(motivo, animo);
    }

    // Pantalla 2.2: aviso perdida de racha
    @Override
    public void mostrarPerdidaRacha() {
        System.out.println("\n========================================");
        System.out.println("         HAS PERDIDO LA RACHA");
        System.out.println("  No te desanimes. Hoy empieza una");
        System.out.println("  racha aun mas fuerte.");
        System.out.println("========================================");
    }

    @Override
    public void mostrarEntrenoTerminado() {
        System.out.println("\nENTRENO TERMINADO!");
    }

    @Override
    public void mostrarParadaEntreno() {
        System.out.println("\nHas parado el entreno. Manana mas!");
    }

    private int leerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero: ");
            }
        }
    }
}