/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Models;

/**
 *
 * @author alumno
 */
public class FormularioEntreno {
    public Entreno entreno;
    public Integer dificultad;
    public Boolean todosCompletados;
    public String foto;

    public FormularioEntreno(Entreno entreno,String foto) {
        this.entreno = entreno;
        this.dificultad = 0;
        this.todosCompletados = false;
        this.foto = foto;
    }
    
    public void registrarDificultad(Integer dificultad){
        this.dificultad = dificultad;
    }
    
    public void registrarCompletados(Boolean respuesta){
        this.todosCompletados = respuesta;
    }

    public Entreno getEntreno() {
        return entreno;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public Boolean getTodosCompletados() {
        return todosCompletados;
    }

    public String getFoto() {
        return foto;
    }
}
