package appCafyd.PresentationLogicLayer.Presenters;

import appCafyd.BusinessLogicLayer.Models.Ejercicio;
import appCafyd.BusinessLogicLayer.Models.Entreno;
import appCafyd.BusinessLogicLayer.Models.FormularioEntreno;
import appCafyd.BusinessLogicLayer.Models.FormularioDiaSinHacer;
import appCafyd.BusinessLogicLayer.Models.Usuario;
import appCafyd.BusinessLogicLayer.Services.UsuarioService;
import appCafyd.PresentationLogicLayer.Views.EntrenoForm;
import appCafyd.PresentationLogicLayer.Views.FormularioDiaSinHacerForm;
import appCafyd.PresentationLogicLayer.Views.IEntrenoView;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EntrenoPresenter {

    private IEntrenoView view;
    private UsuarioService service;

    public EntrenoPresenter(IEntrenoView view, UsuarioService service) {
        this.view = view;
        this.service = service;
    }

    // Pantalla 3.1 -> 4.1: realizar entreno del dia
    public void load(Usuario usuario, LocalDate fecha) {
        int diaReto = calcularDiaReto(usuario, fecha);
        Entreno entreno = new Entreno(fecha, diaReto);
        cargarEjerciciosDelDia(entreno, diaReto);

        this.view.setEjercicios(entreno.ejercicios);
        this.view.setDiaReto(diaReto);
        this.view.open(); // muestra la lista de ejercicios

        if (!this.view.confirmarInicio()) return;

        // Ejercicio por ejercicio
        for (Ejercicio ej : entreno.ejercicios) {
            boolean completado = this.view.mostrarEjercicio(ej);
            if (!completado) {
                this.view.mostrarParadaEntreno();
                return;
            }
        }

        // Todos completados
        this.view.mostrarEntrenoTerminado();

        // Pantalla 6 + 7: foto y formulario
        EntrenoForm form = this.view.getEntrenoForm(diaReto);

        FormularioEntreno formulario = new FormularioEntreno(entreno, form.Foto);
        formulario.registrarDificultad(form.Dificultad);
        formulario.registrarCompletados(form.TodosHechos);

        entreno.foto = form.Foto;
        entreno.dificultad = form.Dificultad;

        service.finalizarEntreno(usuario, formulario);
        this.view.close();
    }

    // Pantalla 3.2: ver resumen de entreno ya completado
    public void loadResumen(Entreno entreno) {
        this.view.mostrarResumenEntreno(entreno);
    }

    // Pantalla 2.2 + 2.3: perdida de racha y formulario dia sin hacer
    public void loadFormularioDiaSinHacer(Usuario usuario, LocalDate fechaDia) {
        this.view.mostrarPerdidaRacha();

        FormularioDiaSinHacerForm form = this.view.getFormularioDiaSinHacer();

        FormularioDiaSinHacer.MotivoDia motivo;
        switch (form.Motivo) {
            case 1:  motivo = FormularioDiaSinHacer.MotivoDia.DIA_OCUPADO;        break;
            case 2:  motivo = FormularioDiaSinHacer.MotivoDia.NO_ENCONTRABA_BIEN; break;
            default: motivo = FormularioDiaSinHacer.MotivoDia.OTRO;               break;
        }

        FormularioDiaSinHacer.EstadoAnimico estado;
        switch (form.EstadoAnimico) {
            case 1:  estado = FormularioDiaSinHacer.EstadoAnimico.MUY_MAL;  break;
            case 2:  estado = FormularioDiaSinHacer.EstadoAnimico.MAL;      break;
            case 3:  estado = FormularioDiaSinHacer.EstadoAnimico.REGULAR;  break;
            case 4:  estado = FormularioDiaSinHacer.EstadoAnimico.BIEN;     break;
            default: estado = FormularioDiaSinHacer.EstadoAnimico.MUY_BIEN; break;
        }

        FormularioDiaSinHacer formulario = new FormularioDiaSinHacer(fechaDia);
        formulario.registrarMotivo(motivo);
        formulario.registrarEstadoAnimico(estado);

        // Marcar ese dia en rojo en el seguimiento
        Entreno entrenoFallado = new Entreno(fechaDia, calcularDiaReto(usuario, fechaDia));
        usuario.seguimiento.añadirEntreno(entrenoFallado);
        usuario.seguimiento.comprobarRegistro(entrenoFallado);

        System.out.println("Formulario enviado.");
    }

    private int calcularDiaReto(Usuario usuario, LocalDate fecha) {
        long dias = ChronoUnit.DAYS.between(usuario.fechaRegistro, fecha);
        return (int) dias + 1;
    }

    // Ejercicios por dia del reto - CAMBIAR CON LOS DE CAFYD
    private void cargarEjerciciosDelDia(Entreno entreno, int diaReto) {
        switch (diaReto) {
            case 1:
                entreno.añadirEjercicio(ej("Sentadillas",         "sentadilla.mp4",  3, 10, "Rodillas hacia adentro"));
                entreno.añadirEjercicio(ej("Flexiones",           "flexiones.mp4",   3, 10, "Codos muy abiertos"));
                entreno.añadirEjercicio(ej("Plancha",             "plancha.mp4",     3, 20, "Cadera arriba o abajo"));
                break;
            case 2:
                entreno.añadirEjercicio(ej("Zancadas",            "zancada.mp4",     3, 10, "Pecho inclinado"));
                entreno.añadirEjercicio(ej("Fondos de triceps",   "fondos.mp4",      3, 10, "Bajar poco"));
                entreno.añadirEjercicio(ej("Puente de gluteos",   "puente.mp4",      3, 15, "No subir las caderas del todo"));
                break;
            case 3:
                entreno.añadirEjercicio(ej("Burpees",             "burpees.mp4",     3,  8, "No extender bien arriba"));
                entreno.añadirEjercicio(ej("Mountain climbers",   "mountain.mp4",    3, 20, "Caderas altas"));
                entreno.añadirEjercicio(ej("Gemelos",             "gemelos.mp4",     3, 20, "No subir del todo"));
                break;
            case 4:
                entreno.añadirEjercicio(ej("Sentadilla sumo",     "sumo.mp4",        4, 12, "Espalda encorvada"));
                entreno.añadirEjercicio(ej("Press de hombros",    "hombros.mp4",     3, 10, "Arquear la espalda baja"));
                entreno.añadirEjercicio(ej("Abdominales",         "abdominales.mp4", 3, 15, "Tirar del cuello"));
                break;
            case 5:
                entreno.añadirEjercicio(ej("Flexiones inclinadas","flexInc.mp4",     3, 12, "Codos muy abiertos"));
                entreno.añadirEjercicio(ej("Sentadilla bulgara",  "bulgara.mp4",     3, 10, "Rodilla delantera pasa el pie"));
                entreno.añadirEjercicio(ej("Plancha lateral",     "planchaLat.mp4",  3, 20, "Cadera que cae"));
                break;
            default:
                int bloque = diaReto % 3;
                if (bloque == 0) {
                    entreno.añadirEjercicio(ej("Sentadillas",    "sentadilla.mp4", 3 + diaReto / 10, 10 + diaReto / 5, "Rodillas hacia adentro"));
                    entreno.añadirEjercicio(ej("Flexiones",      "flexiones.mp4",  3 + diaReto / 10, 10 + diaReto / 5, "Codos muy abiertos"));
                    entreno.añadirEjercicio(ej("Plancha",        "plancha.mp4",    3, 20 + diaReto, "Cadera"));
                } else if (bloque == 1) {
                    entreno.añadirEjercicio(ej("Zancadas",       "zancada.mp4",    3 + diaReto / 10, 10 + diaReto / 5, "Pecho inclinado"));
                    entreno.añadirEjercicio(ej("Burpees",        "burpees.mp4",    3, 8 + diaReto / 5, "No extender"));
                    entreno.añadirEjercicio(ej("Puente gluteos", "puente.mp4",     3, 15 + diaReto / 5, "No subir del todo"));
                } else {
                    entreno.añadirEjercicio(ej("Fondos",         "fondos.mp4",     3 + diaReto / 10, 10 + diaReto / 5, "Bajar poco"));
                    entreno.añadirEjercicio(ej("Mountain climb", "mountain.mp4",   3, 20 + diaReto, "Caderas altas"));
                    entreno.añadirEjercicio(ej("Abdominales",    "abds.mp4",       3, 15 + diaReto / 5, "Tirar del cuello"));
                }
                break;
        }
    }

    private Ejercicio ej(String nombre, String video, int series, int reps, String fallo) {
        Ejercicio e = new Ejercicio(nombre, video, series, reps);
        e.añadirFallo(fallo);
        return e;
    }
}