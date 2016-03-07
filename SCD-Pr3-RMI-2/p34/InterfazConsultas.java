import java.rmi.*;

/**
 * Esto es una interfaz remota para consultar una encuesta. Se definen las
 * signaturas de los metodos enviarRecuento aceptarVoto y guardarRecuento
 */
public interface InterfazConsultas extends Remote {

    public String enviarRecuento() throws java.rmi.RemoteException;

    public String aceptarVoto(int voto) throws java.rmi.RemoteException;

    public void guardarRecuento(int voto) throws java.rmi.RemoteException;
    
} // end InterfazConsultas