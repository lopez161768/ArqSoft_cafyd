/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Presenters;

/**
 *
 * @author Usuario
 */

import appCafyd.BusinessLogicLayer.Models.Entreno;
import appCafyd.BusinessLogicLayer.Models.Usuario;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import appCafyd.PresentationLogicLayer.Views.CalendarioForm;
import appCafyd.PresentationLogicLayer.Views.IInicialView;
import java.time.LocalDate;

public class InicialPresenter {

    private IInicialView view;
    private UsuarioService service;
    private Usuario usuarioActual;

    public InicialPresenter(IInicialView view, UsuarioService service) {
        this.view = view;
        this.service = service;

        this.view.setSeleccionarDiaAction(() -> {
            seleccionarDia();
        });

        this.view.setPerfilAction(() -> {
           PresenterManager.perfilPresenter.load(usuarioActual);
            // Refrescar usuario por si cambio algo en perfil
            usuarioActual = service.login(
                    usuarioActual.nombreUsuario,
                    usuarioActual.getContraseña()
            );
        });
        
       /* this.view.setCerrarSesionAction(() -> {
            cerrarSesion = true;
        });
        */
    }

    public void load(Usuario usuario) {
        this.usuarioActual = usuario;

        // Verificar racha al entrar
        boolean perdioRacha = service.verificarYResetearRacha(usuarioActual);
        if (perdioRacha) {
            LocalDate ayer = LocalDate.now().minusDays(1);
            Entreno entrenoAyer = service.buscarEntrenoPorFecha(usuarioActual, ayer);
            if (entrenoAyer == null) {
                PresenterManager.entrenoPresenter.loadFormularioDiaSinHacer(usuarioActual, ayer);
            }
        }

        // Comprobar si el reto ha terminado
        if (usuarioActual.getDiaReto() == 0) {
            this.view.mostrarRetoCompletado(usuarioActual.nombre);
            return;
        }

        // Bucle principal del calendario
        boolean continuar = true;
        while (continuar) {
            actualizarDatosVista();
            this.view.open();
            // Si el input fue 0 cerramos sesion

            if(this.view.isCerrarSesion()){
                continuar = false;
            }
            else{
                continuar = usuarioActual != null && usuarioActual.getDiaReto() != 0;
            }
        }

        this.view.close();
    }

    private void actualizarDatosVista() {
        this.view.setDiasVerdes(usuarioActual.seguimiento.diasVerdes);
        this.view.setDiasRojos(usuarioActual.seguimiento.diasRojos);
        this.view.setFechaRegistro(usuarioActual.fechaRegistro);
        this.view.setRacha(usuarioActual.racha);
        this.view.setDiaReto(usuarioActual.getDiaReto());
    }

    private void seleccionarDia() {
        CalendarioForm form = this.view.getCalendarioForm();
        if (form == null) return;

        LocalDate fecha = form.FechaSeleccionada;
        LocalDate hoy = LocalDate.now();
        LocalDate finReto = usuarioActual.fechaRegistro.plusDays(29);

        if (fecha.isBefore(usuarioActual.fechaRegistro) || fecha.isAfter(finReto)) {
            this.view.mostrarDiaFueraDelReto();
            return;
        }

        if (fecha.isAfter(hoy)) {
            this.view.mostrarDiaFuturo();
            return;
        }

        Entreno entreno = service.buscarEntrenoPorFecha(usuarioActual, fecha);

        if (fecha.isBefore(hoy)) {
            if (entreno != null && entreno.completado) {
                PresenterManager.entrenoPresenter.loadResumen(entreno);
            } else {
                this.view.mostrarDiaPasadoNoCompletado();
            }
            return;
        }

        // Dia de hoy
        if (entreno != null && entreno.completado) {
           PresenterManager.entrenoPresenter.loadResumen(entreno);
        } else {
           PresenterManager.entrenoPresenter.load(usuarioActual, fecha);
            // Refrescar usuario despues de entrenar
            usuarioActual = service.login(
                    usuarioActual.nombreUsuario,
                    usuarioActual.getContraseña()
            );
        }
    }
}
