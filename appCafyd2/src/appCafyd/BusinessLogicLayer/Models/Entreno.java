/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Entreno {
    public LocalDate fecha;
    public ArrayList<Ejercicio> ejercicios;
    public Boolean completado; //cuando saca la foto se completa el entreno
    public String foto;
    public Integer dificultad; 
    public Integer diaReto;
    
    public Entreno(LocalDate fecha, Integer diaReto) {
        this.fecha = fecha;
        this.ejercicios = new ArrayList<>();
        this.completado = false;
        this.foto = "";
        this.dificultad = 0;
        this.diaReto = diaReto;
    }

    public void completar(){
        this.completado = true;
    }
    
    public void añadirEjercicio(Ejercicio ejercicio){
        this.ejercicios.add(ejercicio);
    }
    
    public Integer getNumEjercicios() {
        return ejercicios.size();
    }

    public Boolean isCompletado() {
        return completado;
    }

    public ArrayList<Ejercicio> getListaEjercicios() {
        return ejercicios;
    }

    
}
