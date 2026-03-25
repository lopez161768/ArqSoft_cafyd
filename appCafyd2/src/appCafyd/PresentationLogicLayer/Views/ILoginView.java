/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

/**
 *
 * @author Usuario
 */
public interface ILoginView {
    void open();
    void close();

    void setLoginAction(Runnable loginAction);
    void setRegistroAction(Runnable registroAction);

    UsuarioLoginForm getLoginForm();
    UsuarioRegistroForm getRegistroForm();

    void mostrarLoginIncorrecto();
    void mostrarRegistroExitoso();
    void mostrarUsernameExistente();
    void mostrarHastaPronto();
}
