import java.rmi.*;
import java.util.*;
import java.rmi.server.*;


/**
 * Esta clase implementa la interfaz remota
 * DiaDelaFechaInterface
 */
public class ImplFecha extends UnicastRemoteObject
        implements DiaDelaFechaInterface {
    /**
     * Programar aqui los metodos necesarios
     */

		public ImplFecha() throws java.rmi.RemoteException {
			super();
		}

		public String getDaytime() throws java.rmi.RemoteException{
			String fecha = new String();

			fecha = "Servidor: ";

			fecha += (new Date()).toString();

			return fecha;
		}
} // end ImplFecha
