/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Services;

import appCafyd.BusinessLogicLayer.Models.Entreno;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author Usuario
 */
public interface IEntreno {
    void guardar(Entreno entreno);
    ArrayList<Entreno> listarTodos();
    Entreno buscarPorFecha(LocalDate fecha);
}
