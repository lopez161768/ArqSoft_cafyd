/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */

public interface IInicialView {
    void open();
    void close();

    void setSeleccionarDiaAction(Runnable seleccionarDiaAction);
    void setPerfilAction(Runnable perfilAction);

    void setDiasVerdes(ArrayList<LocalDate> diasVerdes);
    void setDiasRojos(ArrayList<LocalDate> diasRojos);
    void setFechaRegistro(LocalDate fechaRegistro);
    void setRacha(int racha);
    void setDiaReto(int diaReto);

    CalendarioForm getCalendarioForm();

    boolean isCerrarSesion();

    void mostrarDiaFuturo();
    void mostrarDiaFueraDelReto();
    void mostrarDiaPasadoNoCompletado();
    void mostrarRetoCompletado(String nombre);
}
