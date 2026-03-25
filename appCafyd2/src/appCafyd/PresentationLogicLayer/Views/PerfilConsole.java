/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

import java.util.Scanner;

public class PerfilConsole implements IPerfilView {

    private Scanner sc = new Scanner(System.in);
    private Runnable guardarAction;
    private PerfilForm curPerfilForm;
    // para que funcione menú correctamente
    private String opcionElegida = "0";
    private boolean volver = false;
   

    @Override
    public boolean isVolver() {
        return volver;
    }

    @Override
    public void open() {
        volver = false;

        System.out.println("\nPERFIL");
        System.out.println("Username  : " + curPerfilForm.Username);
        System.out.println("Nombre    : " + curPerfilForm.Nombre + " " + curPerfilForm.Apellido);
        System.out.println("\n1. Modificar nombre");
        System.out.println("2. Modificar apellido");
        System.out.println("3. Modificar username");
        System.out.println("4. Modificar contrasena");
        System.out.println("0. Volver");
        System.out.print("Opcion: ");

        opcionElegida = sc.nextLine().trim();
        switch (opcionElegida) {
            case "1":
            case "2":
            case "3":
            case "4":
                guardarAction.run();
                break;
            case "0":
                volver = true;
                break;
            default:
                System.out.println("Opcion no valida.");
                opcionElegida = "0";
        }
    }

    @Override
    public void close() {
        System.out.println("========================================");
    }

    @Override
    public void setGuardarAction(Runnable guardarAction) {
        this.guardarAction = guardarAction;
    }

    @Override
    public void setUsuarioForm(PerfilForm form) {
        this.curPerfilForm = form;
    }

    @Override
    public PerfilForm getPerfilForm() {
        String nombre    = curPerfilForm.Nombre;
        String apellido  = curPerfilForm.Apellido;
        String username  = curPerfilForm.Username;
        String contrasena = curPerfilForm.Contrasena;

        switch (opcionElegida) {
            case "1":
                System.out.print("Nuevo nombre: ");
                nombre = sc.nextLine();
                break;
            case "2":
                System.out.print("Nuevo apellido: ");
                apellido = sc.nextLine();
                break;
            case "3":
                System.out.print("Nuevo username: ");
                username = sc.nextLine();
                break;
            case "4":
                System.out.print("Nueva contrasena: ");
                contrasena = sc.nextLine();
                break;
        }

        return new PerfilForm(nombre, apellido, username, contrasena);
    }

    @Override
    public void mostrarCambiosGuardados() {
        System.out.println("Cambios guardados.");
    }
}