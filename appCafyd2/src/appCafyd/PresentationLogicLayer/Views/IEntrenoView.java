package appCafyd.PresentationLogicLayer.Views;

import appCafyd.BusinessLogicLayer.Models.Ejercicio;
import appCafyd.BusinessLogicLayer.Models.Entreno;
import java.util.ArrayList;

public interface IEntrenoView {
    void open();
    void close();

    void setFinalizarEntrenoAction(Runnable finalizarEntrenoAction);

    void setEjercicios(ArrayList<Ejercicio> ejercicios);
    void setDiaReto(int diaReto);

    // Devuelve true si el usuario quiere empezar
    boolean confirmarInicio();

    // Pantalla 4.1: muestra un ejercicio y devuelve si lo completo
    boolean mostrarEjercicio(Ejercicio ejercicio);

    // Pantalla 6 + 7: foto y formulario post-entreno
    EntrenoForm getEntrenoForm(int diaReto);

    // Pantalla 3.2: resumen de un entreno ya completado
    void mostrarResumenEntreno(Entreno entreno);

    // Pantalla 2.3: formulario dia sin hacer
    FormularioDiaSinHacerForm getFormularioDiaSinHacer();

    // Pantalla 2.2: aviso perdida de racha
    void mostrarPerdidaRacha();

    void mostrarEntrenoTerminado();
    void mostrarParadaEntreno();
}