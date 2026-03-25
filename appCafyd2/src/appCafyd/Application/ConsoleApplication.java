/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.Application;

import appCafyd.BusinessLogicLayer.Models.Entreno;
import appCafyd.BusinessLogicLayer.Models.Usuario;
import appCafyd.BusinessLogicLayer.Models.eCategory;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import appCafyd.DataAccessLayer.EntrenoDAO;
import appCafyd.DataAccessLayer.UsuarioDAO;
import appCafyd.PresentationLogicLayer.Presenters.EntrenoPresenter;
import appCafyd.PresentationLogicLayer.Presenters.InicialPresenter;
import appCafyd.PresentationLogicLayer.Presenters.LoginPresenter;
import appCafyd.PresentationLogicLayer.Presenters.PerfilPresenter;
import appCafyd.PresentationLogicLayer.Presenters.PresenterManager;
import appCafyd.PresentationLogicLayer.Views.EntrenoConsole;
import appCafyd.PresentationLogicLayer.Views.InicialConsole;
import appCafyd.PresentationLogicLayer.Views.LoginConsole;
import appCafyd.PresentationLogicLayer.Views.PerfilConsole;
import java.time.LocalDate;

public class ConsoleApplication {

    public static void main(String[] args) {

        // DAOs
        UsuarioDAO uDao = new UsuarioDAO();
        EntrenoDAO eDao = new EntrenoDAO();

        // Service con dependencias inyectadas
        UsuarioService service = new UsuarioService(uDao, eDao);

        // Cargar usuarios de prueba
        cargarUsuariosDePrueba(service, uDao);

        // Vistas
        LoginConsole  loginView   = new LoginConsole();
        InicialConsole inicialView = new InicialConsole();
        EntrenoConsole entrenoView = new EntrenoConsole();
        PerfilConsole  perfilView  = new PerfilConsole();

        // Presenters
        LoginPresenter  loginPresenter   = new LoginPresenter(loginView,   service);
        InicialPresenter inicialPresenter = new InicialPresenter(inicialView, service);
        EntrenoPresenter entrenoPresenter = new EntrenoPresenter(entrenoView, service);
        PerfilPresenter  perfilPresenter  = new PerfilPresenter(perfilView,  service);

        // Inyectar en PresenterManager para que se comuniquen entre si
        PresenterManager.loginPresenter   = loginPresenter;
        PresenterManager.inicialPresenter = inicialPresenter;
        PresenterManager.entrenoPresenter = entrenoPresenter;
        PresenterManager.perfilPresenter  = perfilPresenter;

        // Arrancar la app
        loginPresenter.load();
    }

    // Usuarios de prueba precargados en memoria
    private static void cargarUsuariosDePrueba(UsuarioService service, UsuarioDAO uDao) {
        LocalDate hoy = LocalDate.now();

        // Usuario 1: Juan Perez - dia 3, racha 2
        Usuario juan = new Usuario("juan", "juan123", "Juan", "Perez", 28, eCategory.Hombre, true);
        juan.fechaRegistro = hoy.minusDays(2);

        Entreno e1 = new Entreno(hoy.minusDays(2), 1);
        e1.foto = "foto_dia1.jpg";
        e1.dificultad = 3;
        e1.completar();
        juan.seguimiento.añadirEntreno(e1);
        juan.seguimiento.comprobarRegistro(e1);
        juan.aumentarRacha();

        Entreno e2 = new Entreno(hoy.minusDays(1), 2);
        e2.foto = "foto_dia2.jpg";
        e2.dificultad = 4;
        e2.completar();
        juan.seguimiento.añadirEntreno(e2);
        juan.seguimiento.comprobarRegistro(e2);
        juan.aumentarRacha();

        service.registrar(juan);

        // Usuario 2: Maria Garcia - dia 2, racha 1
        Usuario maria = new Usuario("maria", "maria123", "Maria", "Garcia", 25, eCategory.Mujer, false);
        maria.fechaRegistro = hoy.minusDays(1);

        Entreno e3 = new Entreno(hoy.minusDays(1), 1);
        e3.foto = "foto_maria_dia1.jpg";
        e3.dificultad = 5;
        e3.completar();
        maria.seguimiento.añadirEntreno(e3);
        maria.seguimiento.comprobarRegistro(e3);
        maria.aumentarRacha();

        service.registrar(maria);

        System.out.println("Usuarios de prueba cargados:");
        System.out.println("   juan  / juan123  (dia 3, racha 2)");
        System.out.println("   maria / maria123 (dia 2, racha 1)");
    }
}