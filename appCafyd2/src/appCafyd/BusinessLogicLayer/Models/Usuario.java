/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Models;

/**
 *
 * @author alumno
 */
import java.time.LocalDate;
public class Usuario {
    public String nombreUsuario;
    private String contraseña;
    public String nombre;
    public String apellido;
    public Integer edad;
    public eCategory sexo;
    public Boolean calorias;
    public Integer racha;
    public LocalDate fechaRegistro;
    public Seguimiento seguimiento;
    //public String foto; PARA CUANDO HAYA FOTO DE PERFIL

    public Usuario(String nombreUsuario, String contraseña, String nombre, String apellido, Integer edad, eCategory sexo, Boolean calorias) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.calorias = calorias;
        this.racha = 0;
        this.fechaRegistro = LocalDate.now(); // el reto empieza
        this.seguimiento = new Seguimiento();
    }
    

    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) { 
        this.contraseña = contraseña; 
    }
    
    
    public void aumentarRacha(){
        this.racha = this.racha+1;
    }
    
    public void resetearRacha(){
        this.racha =0;
    }

    // Devuelve en qué día del reto está hoy (1-30), o 0 si ya pasaron 30 días
    public int getDiaReto() {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaRegistro, LocalDate.now());
        int dia = (int) dias + 1;
        if(dia >= 1 && dia <= 30){
            return dia;
        }
        return 0;
    }
    /*public void cambiarFoto(String nuevaFoto){
        this.foto = nuevaFoto;
    }*/
}
