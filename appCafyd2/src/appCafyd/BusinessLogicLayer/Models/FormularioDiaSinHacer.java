/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCafyd.BusinessLogicLayer.Models;

/**
 *
 * @author Usuario
 */
import java.time.LocalDate;

public class FormularioDiaSinHacer {
    public enum MotivoDia {
        DIA_OCUPADO,
        NO_ENCONTRABA_BIEN,
        OTRO
    }
 
    public enum EstadoAnimico {
        MUY_MAL,
        MAL,
        REGULAR,    
        BIEN,     
        MUY_BIEN  
    }
 
    public LocalDate fechaDia;   // el día que no se hizo
    public MotivoDia motivo;
    public EstadoAnimico estadoAnimico;
 
    public FormularioDiaSinHacer(LocalDate fechaDia) {
        this.fechaDia = fechaDia;
    }
 
    public void registrarMotivo(MotivoDia motivo) {
        this.motivo = motivo;
    }
 
    public void registrarEstadoAnimico(EstadoAnimico estado) {
        this.estadoAnimico = estado;
    }
}

