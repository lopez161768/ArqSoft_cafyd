/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

import java.util.Scanner; 
/**
 *
 * @author Usuario
 */
public class LoginConsole implements ILoginView {

    private Scanner sc = new Scanner(System.in);
    private Runnable loginAction;
    private Runnable registroAction;

    @Override
    public void open() {
        System.out.println("\n---------------------------------------");
        System.out.println("        BIENVENID@ al RETO CAFYD!     ");
        System.out.println("---------------------------------------");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar Sesion");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");

        String op = sc.nextLine();
        switch (op) {
            case "1":
                registroAction.run();
                break;
            case "2":
                loginAction.run();
                break;
            case "0":
                mostrarHastaPronto();
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    @Override
    public void close() {
        System.out.println("----------------------------------------");
    }

    @Override
    public void setLoginAction(Runnable loginAction) {
        this.loginAction = loginAction;
    }

    @Override
    public void setRegistroAction(Runnable registroAction) {
        this.registroAction = registroAction;
    }

    @Override
    public UsuarioLoginForm getLoginForm() {
        System.out.println("\nINICIAR SESION");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Contrasena: ");
        String pass = sc.nextLine();
        return new UsuarioLoginForm(user, pass);
    }

    public UsuarioRegistroForm getRegistroForm() {
        System.out.println("\nDATOS PERSONALES");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        int edad = 0;
        do {
            System.out.print("Edad (1-120): ");
            edad = leerInt();
            if (edad < 1 || edad > 120) System.out.println("Edad no valida.");
        } while (edad < 1 || edad > 120);

        int sexo = 0;
        do {
            System.out.print("Sexo (1. Hombre / 2. Mujer): ");
            sexo = leerInt();
            if (sexo != 1 && sexo != 2) System.out.println("Opcion no valida.");
        } while (sexo != 1 && sexo != 2);

        String user = "";
        do {
            System.out.print("Username: ");
            user = sc.nextLine().trim();
            if (user.isEmpty()) System.out.println("El username no puede estar vacio.");
        } while (user.isEmpty());

        String pass = "";
        do {
            System.out.print("Contrasena: ");
            pass = sc.nextLine().trim();
            if (pass.isEmpty()) System.out.println("La contrasena no puede estar vacia.");
        } while (pass.isEmpty());

        System.out.print("Mostrar recuento de calorias? (S/N): ");
        boolean calorias = sc.nextLine().equalsIgnoreCase("S");

        return new UsuarioRegistroForm(user, pass, nombre, apellido, edad, sexo, calorias);
    }

    @Override
    public void mostrarLoginIncorrecto() {
        System.out.println("Usuario o contrasena incorrectos.");
    }

    @Override
    public void mostrarRegistroExitoso() {
        System.out.println("Registrado! El reto de 30 dias empieza HOY. Mucho animo!");
    }

    @Override
    public void mostrarUsernameExistente() {
        System.out.println("Ese username ya existe. Prueba con otro.");
    }

    @Override
    public void mostrarHastaPronto() {
        System.out.println("Hasta pronto!");
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
