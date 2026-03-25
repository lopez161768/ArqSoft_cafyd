/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

/**
 *
 * @author Usuario
 */
public interface IPerfilView {
    void open();
    void close();
 
    void setGuardarAction(Runnable guardarAction);
 
    void setUsuarioForm(PerfilForm form);
    PerfilForm getPerfilForm();
 
    void mostrarCambiosGuardados();

    boolean isVolver();
}
