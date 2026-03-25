/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Usuario
 */
public class InicialConsole implements IInicialView {

    private Scanner sc = new Scanner(System.in);
    private Runnable seleccionarDiaAction;
    private Runnable perfilAction;

    private ArrayList<LocalDate> diasVerdes = new ArrayList<>();
    private ArrayList<LocalDate> diasRojos  = new ArrayList<>();
    private LocalDate fechaRegistro;
    private int racha;
    private int diaReto;

    private String inputActual;
    private boolean cerrarSesion = false;

    @Override
    public boolean isCerrarSesion() {
        return cerrarSesion;
    }

    @Override
    public void open() {
        cerrarSesion = false;
        mostrarCalendario();
        System.out.println("Dia del reto: " + diaReto + "/30  |  Racha: " + racha + " dias");
        System.out.println("\nOpciones:");
        System.out.println("  [numero] Seleccionar dia del mes");
        System.out.println("  P        Ver perfil");
        System.out.println("  0        Cerrar sesion");
        System.out.print("\nOpcion: ");
        inputActual = sc.nextLine().trim();

        if (inputActual.equalsIgnoreCase("P")) {
            perfilAction.run();
        } else if (!inputActual.equals("0")) {
            cerrarSesion = true;
        }
        else{
            seleccionarDiaAction.run();
        }
    }

    @Override
    public void close() {
        System.out.println("Sesion cerrada. Hasta pronto!");
    }

    @Override
    public void setSeleccionarDiaAction(Runnable seleccionarDiaAction) {
        this.seleccionarDiaAction = seleccionarDiaAction;
    }

    @Override
    public void setPerfilAction(Runnable perfilAction) {
        this.perfilAction = perfilAction;
    }

    @Override
    public void setDiasVerdes(ArrayList<LocalDate> diasVerdes) {
        this.diasVerdes = diasVerdes;
    }

    @Override
    public void setDiasRojos(ArrayList<LocalDate> diasRojos) {
        this.diasRojos = diasRojos;
    }

    @Override
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public void setRacha(int racha) {
        this.racha = racha;
    }

    @Override
    public void setDiaReto(int diaReto) {
        this.diaReto = diaReto;
    }

    @Override
    public CalendarioForm getCalendarioForm() {
        try {
            int dia = Integer.parseInt(inputActual);
            LocalDate fecha = LocalDate.now().withDayOfMonth(dia);
            return new CalendarioForm(fecha);
        } catch (NumberFormatException | java.time.DateTimeException e) {
            System.out.println("Opcion no valida.");
            return null;
        }
    }

    @Override
    public void mostrarDiaFuturo() {
        System.out.println("Todavia no ha llegado ese dia.");
    }

    @Override
    public void mostrarDiaFueraDelReto() {
        System.out.println("Ese dia no forma parte del reto.");
    }

    @Override
    public void mostrarDiaPasadoNoCompletado() {
        System.out.println("No completaste ese dia. No se puede recuperar.");
    }

    @Override
    public void mostrarRetoCompletado(String nombre) {
        System.out.println("HAS COMPLETADO EL RETO DE 30 DIAS! Enhorabuena, " + nombre + "!");
        System.out.print("Pulsa ENTER para salir...");
        sc.nextLine();
    }

    private void mostrarCalendario() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = fechaRegistro;
        int diasEnMes = hoy.lengthOfMonth();
        int primerDiaSemana = hoy.withDayOfMonth(1).getDayOfWeek().getValue();

        System.out.println("\n========================================");
        System.out.printf("         %s %d%n", hoy.getMonth(), hoy.getYear());
        System.out.println("========================================");
        System.out.println("  L    M    X    J    V    S    D");

        for (int i = 1; i < primerDiaSemana; i++) System.out.print("     ");

        for (int dia = 1; dia <= diasEnMes; dia++) {
            LocalDate fecha = LocalDate.of(hoy.getYear(), hoy.getMonth(), dia);
            String etiqueta;

            LocalDate finReto = inicio.plusDays(29);
            boolean esDiaDeReto = !fecha.isBefore(inicio) && !fecha.isAfter(finReto);

            if (!esDiaDeReto) {
                etiqueta = "[]";
            } else if (fecha.isAfter(hoy)) {
                etiqueta = "[X]";
            } else if (diasVerdes.contains(fecha)) {
                etiqueta = "[V]";
            } else if (diasRojos.contains(fecha)) {
                etiqueta = "[R]";
            } else if (fecha.equals(hoy)) {
                etiqueta = "[" + dia + "]";
            } else {
                etiqueta = "[ ]";
            }

            System.out.printf(" %-4s", etiqueta);
            if ((dia + primerDiaSemana - 1) % 7 == 0) System.out.println();
        }

        System.out.println("\n----------------------------------------");
        System.out.println("[V] Hecho  [R] Fallado  [dia] Hoy  [X] Futuro  [] Fuera del reto");
    }
}
