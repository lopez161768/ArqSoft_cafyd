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
public class Seguimiento {
    public  ArrayList<Entreno> registros;/*he puesto entreno en vez de formularioentreno porque he 
    pensado que como la variable que determina si ha terminado el entreno es el boolean de Enteno, creo que tenia sentido meter entreno. Porque como tal
    todosCompletados era la pregunra que haciamos en el formulario pero que era un poco sinmas no haciamos nada con esa info (creo recordar que dijimos eso)*/
    public ArrayList<LocalDate> diasVerdes;//para saber en concreto qué dias poner en verde
    public ArrayList<LocalDate> diasRojos;

    public Seguimiento() {
        this.registros = new ArrayList<>();
        this.diasRojos =new ArrayList<>();
        this.diasVerdes=new ArrayList<>();
    }
    
    public void añadirEntreno(Entreno entreno){
        this.registros.add(entreno);
    }
    
    //PRIVADOS PORQUE ESTAS FUNCIONES NO INTERESA QUE LAS TOQUE NADIE MAS QUE LA FUNCION COMPROBARREGISTRO
    private void añadirVerde(LocalDate fecha){
        this.diasVerdes.add(fecha);
    }
    
    private void añadirRojo(LocalDate fecha){
        this.diasRojos.add(fecha);
    }
    
    public void comprobarRegistro(Entreno entreno){
        if (entreno.isCompletado()){
            añadirVerde(entreno.fecha);
        }
        else{
            añadirRojo(entreno.fecha);
        }
    }

    public ArrayList<Entreno> getRegistros() {
        return registros;
    }
    
    public Integer getDiasTotales(){
        return this.diasVerdes.size() + this.diasRojos.size();
    }
    
}
