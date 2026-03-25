/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Presenters;

import appCafyd.BusinessLogicLayer.Models.Usuario;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import appCafyd.PresentationLogicLayer.Views.IPerfilView;
import appCafyd.PresentationLogicLayer.Views.PerfilForm;

public class PerfilPresenter {

    private IPerfilView view;
    private UsuarioService service;
    private Usuario usuarioActual;

    public PerfilPresenter(IPerfilView view, UsuarioService service) {
        this.view = view;
        this.service = service;

        this.view.setGuardarAction(() -> {
            guardar();
        });
    }

    public void load(Usuario usuario) {
        this.usuarioActual = usuario;

        boolean continuar = true;
        while (continuar) {
            PerfilForm form = new PerfilForm(
                    usuarioActual.nombre,
                    usuarioActual.apellido,
                    usuarioActual.nombreUsuario,
                    usuarioActual.getContraseña()
            );
            this.view.setUsuarioForm(form);
            this.view.open();
            // Si el usuario eligio 0 salimos del bucle
            if(this.view.isVolver()){
                continuar = false;
            }
        }

        this.view.close();
    }

    private void guardar() {
        PerfilForm form = this.view.getPerfilForm();

        usuarioActual.nombre = form.Nombre;
        usuarioActual.apellido = form.Apellido;
        usuarioActual.nombreUsuario = form.Username;
        usuarioActual.setContraseña(form.Contrasena);

        service.actualizarUsuario(usuarioActual);
        this.view.mostrarCambiosGuardados();
    }
}
