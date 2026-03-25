/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

/**
 *
 * @author Usuario
 */
public class FormularioDiaSinHacerForm {
    public int Motivo;      // 1 dia ocupado, 2 no me encontraba bien, 3 otro
    public int EstadoAnimico; // 1-5

    public FormularioDiaSinHacerForm(int motivo, int estadoAnimico) {
        this.Motivo = motivo;
        this.EstadoAnimico = estadoAnimico;
    }
}
