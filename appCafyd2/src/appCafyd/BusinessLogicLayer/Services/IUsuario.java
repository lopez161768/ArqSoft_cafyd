/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Services;

import appCafyd.BusinessLogicLayer.Models.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IUsuario {
    void insertar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(String nombreUsuario);
    Usuario buscarPorUsername(String nombreUsuario);
    ArrayList<Usuario> obtenerTodos();
}
