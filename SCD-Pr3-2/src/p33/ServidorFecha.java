import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

/**
 * Esta clase representa al sirviente de un objeto distribuido de la clase
 * Fecha, que implementa la interfaz remota DiaDelaFechaInterfaz.
 */
public class ServidorFecha {

    public static void main(String args[]) {
        /**
         * Preparamos los objetos necesarios para leer de la corriente de
         * entrada bloques de datos
         */

		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String numeroPuerto, registroURL;

        try {
            // Necesitamos saber el n�mero de puerto bajo el que se va a 
            // ejecutar el registro RMI
            System.out.println("Entrar el numero de puerto del registro RMI:");

            /**
             * Leeremos el numeroPuerto
             */
            numeroPuerto = (br.readLine()).trim();
			int numeroPuertoRMI = Integer.parseInt(numeroPuerto);

            lanzarRegistro(numeroPuertoRMI);

            /**
             * Crear un objeto de la clase "ImplFecha" con el mensaje de la 
             * fecha
             */
            ImplFecha objFecha = new ImplFecha();

            /**
             * Construir el registroURL, para el protcolo rmi, concatenando 
             * en un "string" el nombre de la m�quina servidora, el numero de 
             * puerto y, al final, un nombre simb�lico para el servicio
             * que estamos implementando como un objeto distribuido
             */
            
			String myURL = "rmi://localhost:"+numeroPuertoRMI+"/fecha";

            /**
             * Ligar el registro RMI con la referencia anterior del objeto 
             * "implHola"
             */
			Naming.rebind(myURL, objFecha);

            System.out.println("El Servidor Fecha esta preparado.");
        } catch (Exception re) {
            System.out.println("Excepcion en el main() del  ServidorFecha: " + re);
        }
    } // end main

    // Este metodo inicia un registro RMI en el "host" local, si
    // todavia no existe en el numero de puerto especificado.
    private static void lanzarRegistro(int NumPuertoRMI)
            throws RemoteException {
        try {
            /**
             * Obtener una referencia de objetos del registro RMI para que el
             * servicio de nombres se ejecute bajo el puerto especificado
             * en el argumento
             */
             Registry registro = LocateRegistry.getRegistry(NumPuertoRMI);
            
            // La siguiente llamada levantara una excepcion si el registro RMI
            // no existe todavia
            registro.list();
        } catch (RemoteException e) {
            /**
             * Si no hay ningun registro RMI valido ejecutandose en el numero 
             * que se paso como argumento, entonces hay que crear un registro
             * en el puerto indicado.
             */
			Registry registro = LocateRegistry.createRegistry(NumPuertoRMI);
        }
    } // end lanzarRegistro

    // Este metodo lista los nombres registrados con el objeto registro RMI
    private static void listarRegistro(String registroURL)
            throws RemoteException, MalformedURLException {
        /**
         * Completar la funcionalidad del metodo
         */
		System.out.println("El Registro " + registroURL + " contiene: ");

        String[] listaNombres = Naming.list(registroURL);

        for (int i = 0; i < listaNombres.length; i++) {
            System.out.println(listaNombres[i]);
        }
    } // end listarRegistro
} // end ServidorFecha
