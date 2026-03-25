package appCafyd.DataAccessLayer;

import appCafyd.BusinessLogicLayer.Services.IUsuario;
import appCafyd.BusinessLogicLayer.Models.Usuario;
import java.util.ArrayList;

public class UsuarioDAO implements IUsuario {

    private ArrayList<Usuario> usuariosBD = new ArrayList<>();

    @Override
    public void insertar(Usuario usuario) {
        Usuario uClonado = clonar(usuario);
        usuariosBD.add(uClonado);
        System.out.println("Usuario " + usuario.nombreUsuario + " registrado.");
    }

    @Override
    public void actualizar(Usuario usuarioModificado) {
        for (int i = 0; i < usuariosBD.size(); i++) {
            if (usuariosBD.get(i).nombreUsuario.equals(usuarioModificado.nombreUsuario)) {
                usuariosBD.set(i, clonar(usuarioModificado));
                System.out.println("Usuario " + usuarioModificado.nombreUsuario + " actualizado.");
                return;
            }
        }
        System.out.println("No se encontró el usuario para actualizar.");
    }

    @Override
    public void eliminar(String nombreUsuario) {
        for (int i = 0; i < usuariosBD.size(); i++) {
            if (usuariosBD.get(i).nombreUsuario.equals(nombreUsuario)) {
                usuariosBD.remove(i);
                System.out.println("Usuario " + nombreUsuario + " eliminado.");
                return;
            }
        }
    }

    @Override
    public Usuario buscarPorUsername(String nombreUsuario) {
        for (Usuario usuario : usuariosBD) {
            if (usuario.nombreUsuario.equals(nombreUsuario)) {
                return clonar(usuario);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> clon = new ArrayList<>();
        for (Usuario u : usuariosBD) {
            clon.add(clonar(u));
        }
        return clon;
    }

    private Usuario clonar(Usuario usuario) {
        Usuario clon = new Usuario(
                usuario.nombreUsuario,
                usuario.getContraseña(),
                usuario.nombre,
                usuario.apellido,
                usuario.edad,
                usuario.sexo,
                usuario.calorias
        );
        clon.racha = usuario.racha;
        clon.fechaRegistro = usuario.fechaRegistro;
        clon.seguimiento = usuario.seguimiento;
        return clon;
    }
}
