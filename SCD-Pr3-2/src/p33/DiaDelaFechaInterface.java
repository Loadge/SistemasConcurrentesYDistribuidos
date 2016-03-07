// Archivo de interfaz RMI simple
import java.rmi.*;

/*
 * Interfaz remota del servicio
 */
public interface DiaDelaFechaInterface extends Remote {

    /*
     * Este metodo remoto devuelve un mensaje.
     * @devuelve una fecha
     */
    public String getDaytime() throws java.rmi.RemoteException;
} // end interface