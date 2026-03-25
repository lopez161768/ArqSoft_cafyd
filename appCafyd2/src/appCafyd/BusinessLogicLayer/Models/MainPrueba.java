/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appCafyd.BusinessLogicLayer.Models;

import static java.time.Clock.system;
import java.time.LocalDate;

/**
 *
 * @author ivann
 */
public class MainPrueba {
    public static void main(String args[]) {
        //se registra
        Usuario nuevo = new Usuario("MartinAguirre","contraseña", "Martin","Aguirre",24,eCategory.Hombre,true);

        //pincha en el entreno del dia
        Ejercicio sentadilla = new Ejercicio("Sentadilla", "blabla",3,8);
        Ejercicio plancha =new Ejercicio("Plancha", "blabla",3,8);

        Entreno entreno = new Entreno(LocalDate.now(),1);
        entreno.añadirEjercicio(sentadilla);
        entreno.añadirEjercicio(plancha);
        
        //hace el entreno
        FormularioEntreno formulario = new FormularioEntreno(entreno,"blabla");
        //info del formulario pero que no usamos para nada
        formulario.registrarCompletados(true);
        formulario.registrarDificultad(3);
        //hace la foto y sale del formulario
        entreno.completar();
        
        //pasamos info al seguimiento
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.añadirEntreno(entreno);
        seguimiento.comprobarRegistro(entreno);
        
        //AHORA COMO TAL TENEMOS INFORMACION GUARDADA EN NUESTA APP
        //PRUEBAS PARA VER QUE TODO FUNCIONA:
        nuevo.nombre = "MANOLOOOO";
        System.out.println(nuevo.nombre); //Salida esperada: MANOLOOOO
        
        System.out.println(entreno.getNumEjercicios());//2
        Ejercicio nuevoej = new Ejercicio("pinopuente", "blabla", 4,5);
        entreno.añadirEjercicio(nuevoej);
        System.out.println(entreno.getNumEjercicios());//3
        
        System.out.print("Dias en verde: ");
        System.out.println(seguimiento.diasVerdes);//uno solo que es hoy pues saldra 18
        System.out.print("Dias en rojo: ");
        System.out.println(seguimiento.diasRojos);//0
        System.out.print("Dias en verde: ");
        System.out.println(seguimiento.getDiasTotales());//18
        
    }
}
