/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Presenters;

import appCafyd.BusinessLogicLayer.Models.Usuario;
import appCafyd.BusinessLogicLayer.Models.eCategory;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import appCafyd.PresentationLogicLayer.Views.ILoginView;
import appCafyd.PresentationLogicLayer.Views.UsuarioLoginForm;
import appCafyd.PresentationLogicLayer.Views.UsuarioRegistroForm;
/**
 *
 * @author Usuario
 */
public class LoginPresenter {
 
    private ILoginView view;
    private UsuarioService service;
 
    public LoginPresenter(ILoginView view, UsuarioService service) {
        this.view = view;
        this.service = service;
 
        this.view.setLoginAction(() -> {
            login();
        });
 
        this.view.setRegistroAction(() -> {
            registro();
        });
    }
 
    // Abre el menu en bucle hasta que el usuario entra o sale
    public void load() {
        boolean continuar = true;
        while (continuar) {
            this.view.open();
            // Si ya hay usuario logueado pasamos al calendario
            if (PresenterManager.inicialPresenter != null) {
                continuar = false;
            }
        }
    }
 
    private void login() {
        UsuarioLoginForm form = this.view.getLoginForm();
        Usuario u = service.login(form.Username, form.Contrasena);
 
        if (u == null) {
            this.view.mostrarLoginIncorrecto();
        } else {
            this.view.close();
            PresenterManager.inicialPresenter.load(u);
        }
    }
 
    private void registro() {
        UsuarioRegistroForm form = this.view.getRegistroForm();
        eCategory sexo = (form.Sexo == 1) ? eCategory.Hombre : eCategory.Mujer;
 
        Usuario nuevo = new Usuario(
                form.Username,
                form.Contrasena,
                form.Nombre,
                form.Apellido,
                form.Edad,
                sexo,
                form.Calorias
        );
 
        if (service.registrar(nuevo)) {
            this.view.mostrarRegistroExitoso();
            this.view.close();
            PresenterManager.inicialPresenter.load(nuevo);
        } else {
            this.view.mostrarUsernameExistente();
        }
    }
}
