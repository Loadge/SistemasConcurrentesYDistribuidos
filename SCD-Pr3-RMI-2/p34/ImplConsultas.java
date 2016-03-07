
import java.rmi.*;
import java.rmi.server.*;

/**
 * Esta clase implementa la interfaz remota InterfazConsultas
 */
public class ImplConsultas extends UnicastRemoteObject
        implements InterfazConsultas {

    /**
     * Crear el array donde se guardara el recuento de cada una de 
     * las 3 posibles respuestas. Programar el constructor de la clase
     */
    /**
     * Comprueba que el voto es correcto y lo acepta
     * @param voto: numero que indica la votacion del cliente.
     * Si es correcto se llama a guardarRecuento(voto) y se devuelve un mensaje 
     * informando. Si no, tambien se informara con un mensaje.
     */
    public String aceptarVoto(int voto) throws RemoteException {
        /**
         * Completar
         */
    }

    /**
     * Una vez comprobado que el voto es correcto lo contabiliza 
     * en el array de recuento de votos.
     */
    public void guardarRecuento(int voto) throws RemoteException {
        /**
         * Completar
         */
    }

    /**
     * Devuelve al cliente el array con el recuento realizado
     */
    public String enviarRecuento() throws RemoteException {
        return "  Si:" + /** dato del array de recuentos */
                +"  No:" + /** dato del array de recuentos */
                +"  ns/nc:" + /** dato del array de recuentos */;
    }
} // fin ImplConsultas
