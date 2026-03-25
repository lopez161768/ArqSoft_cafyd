/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.PresentationLogicLayer.Views;

/**
 *
 * @author Usuario
 */
public class UsuarioRegistroForm {
    public String Username;
    public String Contrasena;
    public String Nombre;
    public String Apellido;
    public int Edad;
    public int Sexo; // 1 Hombre, 2 Mujer
    public boolean Calorias;
 
    public UsuarioRegistroForm(String username, String contrasena, String nombre,
                        String apellido, int edad, int sexo, boolean calorias) {
        this.Username = username;
        this.Contrasena = contrasena;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Edad = edad;
        this.Sexo = sexo;
        this.Calorias = calorias;
    }
}
