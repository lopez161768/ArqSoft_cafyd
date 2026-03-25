package appCafyd.DataAccessLayer;

import appCafyd.BusinessLogicLayer.Models.Ejercicio;
import appCafyd.BusinessLogicLayer.Services.IEntreno;
import appCafyd.BusinessLogicLayer.Models.Entreno;
import java.time.LocalDate;
import java.util.ArrayList;

public class EntrenoDAO implements IEntreno {

    private ArrayList<Entreno> entrenosBD = new ArrayList<>();

    @Override
    public void guardar(Entreno entreno) {
        entrenosBD.add(clonar(entreno));
        System.out.println("Entreno del " + entreno.fecha + " guardado.");
    }

    @Override
    public Entreno buscarPorFecha(LocalDate fecha) {
        for (Entreno entreno : entrenosBD) {
            if (entreno.fecha.equals(fecha)) {
                return clonar(entreno);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Entreno> listarTodos() {
        ArrayList<Entreno> clon = new ArrayList<>();
        for (Entreno e : entrenosBD) {
            clon.add(clonar(e));
        }
        return clon;
    }

    private Entreno clonar(Entreno entreno) {
        Entreno clon = new Entreno(entreno.fecha, entreno.diaReto);
        clon.completado = entreno.completado;
        clon.foto = entreno.foto;
        clon.dificultad = entreno.dificultad;
        for (Ejercicio ej : entreno.ejercicios) {
            clon.añadirEjercicio(ej);
        }
        return clon;
    }
    
}
