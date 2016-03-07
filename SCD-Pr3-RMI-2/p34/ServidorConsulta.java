
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

/**
 * Esta clase representa al objeto servidor de un objeto distribuido de 
 * la clase Consultas, que implementa la interfaz remota InterfazConsultas
 */
public class ServidorConsulta {

    public static void main(String args[]) {
        /**
         * Preparamos los objetos necesarios para leer de la corriente de
         * entrada bloques de datos
         */
        try {
            // Necesitamos saber el número de puerto bajo el que se va a 
            // ejecutar el registro RMI
            System.out.println("Entrar el numero de puerto del registro RMI:");

            /**
             * Leeremos el numeroPuerto
             */
            
            lanzarRegistro(numeroPuertoRMI);

            /**
             * Crear un objeto de la clase "ImplConsultas" desde el que se
             * ejecutaran los metodos
             */
            
            /**
             * Construir el registroURL, para el protcolo rmi, concatenando 
             * en un "string" el nombre de la máquina servidora,
             * el numero de puerto y un nombre simbólico para el servicio
             * que estamos implementando como un objeto distribuido
             */
            
            /**
             * Ligar el registro anterior con la referencia anterior del 
             * objeto "implConsultas"
             */
            System.out.println("El Servidor Consultas esta preparado.");

        } catch (Exception re) {
            System.out.println("Excepcion en el main() ServidorConsultas: " + re);
        }
    } // end main

    // Este metodo inicia un registro RMI en el "host" local, 
    // si todavia no existe en el numero de puerto especificado.
    private static void lanzarRegistro(int NumPuertoRMI)
            throws RemoteException {
        try {
            /**
             * Obtener una referencia de objetos del registro para que el
             * servicio dee nombres se ejecute bajo el puerto especificado
             * en el argumento.
             */
            
            // La siguiente llamada levantara una excepcion si el registro
            // no existe todavia
            registro.list();

        } catch (RemoteException e) {
            /**
             * Si no hay ningún registro válido ejecutándose en el número 
             * que se paso como argumento, hay que crear un registro en el 
             * puerto indicado
             */
            System.out.println(
                    "Registro RMI creado en el puerto " + NumPuertoRMI);
        }
    } // end lanzarRegistro

    // Este metodo lista los nombres registrados con el objeto registro
    private static void listarRegistro(String registroURL)
            throws RemoteException, MalformedURLException {
        System.out.println("El Registro " + registroURL + " contiene: ");
        String[] nombres = Naming.list(registroURL);
        for (int i = 0; i < nombres.length; i++) {
            System.out.println(nombres[i]);
        }
    } // end listarRegistro
} // end ServidorConsulta
