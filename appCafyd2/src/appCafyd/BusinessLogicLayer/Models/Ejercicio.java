/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Models;

import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Ejercicio {
    public String nombreEj;
    public String descripcionIm;//foto o video que nos dan los de cafyd
    public ArrayList<String> fallosComunes; //se meten a mano uno a uno con la funcion
    public Integer series;
    public Integer repeticiones;

    public Ejercicio(String nombreEj, String descripcionIm, Integer series, Integer repeticiones) {
        this.nombreEj = nombreEj;
        this.descripcionIm = descripcionIm;
        this.fallosComunes = new ArrayList<>();
        this.series = series;
        this.repeticiones = repeticiones;
    }
    
    public void modificarSeries(Integer series){
        this.series = series;
    }
    
    public void modificarReps(Integer reps){
        this.repeticiones = reps;
    }
    
    public void añadirFallo(String nuevoFallo){
        this.fallosComunes.add(nuevoFallo);
    }
    
    public void eliminarFallo(Integer indice){//indice empezando por 0
        this.fallosComunes.remove(indice);
    }

    public String getNombreEj() {
        return nombreEj;
    }

    public String getDescripcionIm() {
        return descripcionIm;
    }

    public ArrayList<String> getFallosComunes() {
        return fallosComunes;
    }

    public Integer getSeries() {
        return series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }
}
