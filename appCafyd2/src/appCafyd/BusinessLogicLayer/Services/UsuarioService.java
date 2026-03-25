package appCafyd.BusinessLogicLayer.Services;

import appCafyd.BusinessLogicLayer.Models.Entreno;
import appCafyd.BusinessLogicLayer.Models.FormularioEntreno;
import appCafyd.BusinessLogicLayer.Models.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioService {

    private IUsuario uDao;
    private IEntreno eDao;

    public UsuarioService(IUsuario uDao, IEntreno eDao) {
        this.uDao = uDao;
        this.eDao = eDao;
    }
    
    public boolean registrar(Usuario u) {
        if (uDao.buscarPorUsername(u.nombreUsuario) != null) {
            return false;
        }
        uDao.insertar(u);
        return true;
    }

    public Usuario login(String username, String contraseña) {
        Usuario usuario = uDao.buscarPorUsername(username);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        return null;
    }

    // Devuelve TRUE si el usuario perdio la racha
    public boolean verificarYResetearRacha(Usuario usuario) {
        LocalDate hoy = LocalDate.now();
        LocalDate ayer = hoy.minusDays(1);

        boolean entrenoHoy  = false;
        boolean entrenoAyer = false;

        for (Entreno entreno : usuario.seguimiento.registros) {
            if (entreno.fecha.equals(hoy)  && entreno.completado) 
                entrenoHoy  = true;
            if (entreno.fecha.equals(ayer) && entreno.completado) 
                entrenoAyer = true;
        }

        // Si no ha entrenado ni hoy ni ayer, y tenía racha --> la pierde
        if (!entrenoHoy && !entrenoAyer && usuario.racha > 0) {
            usuario.resetearRacha();
            uDao.actualizar(usuario);
            return true; 
        }
        return false;
    }

    public void finalizarEntreno(Usuario usuario, FormularioEntreno formulario) {
        Entreno entreno = formulario.entreno;
        entreno.completar();

        usuario.seguimiento.añadirEntreno(entreno);
        usuario.seguimiento.comprobarRegistro(entreno);

        // Aumentar racha si entreno ayer o es el primer entreno
        LocalDate ayer = LocalDate.now().minusDays(1);
        boolean entrenoAyer = false;
        for (Entreno reg : usuario.seguimiento.registros) {
            if (reg.fecha.equals(ayer) && reg.completado && reg != entreno) {
                entrenoAyer = true;
                break;
            }
        }

        if (usuario.racha == 0 || entrenoAyer) {
            usuario.aumentarRacha();
        } else {
            // La racha se reinicia a 1
            usuario.resetearRacha();
            usuario.aumentarRacha();
        }

        eDao.guardar(entreno);
        uDao.actualizar(usuario);
        System.out.println("Entreno guardado. Racha actual: " + usuario.racha + " días.");
    }

    public Entreno buscarEntrenoPorFecha(Usuario usuario, LocalDate fecha) {
        for (Entreno entreno : usuario.seguimiento.registros) {
            if (entreno.fecha.equals(fecha)) 
                return entreno;
        }
        return null;
    }

    public void actualizarUsuario(Usuario usuario) {
        uDao.actualizar(usuario);
    }

    public ArrayList<Entreno> obtenerHistorial(Usuario usuario) {
        return usuario.seguimiento.registros;
    }
}
