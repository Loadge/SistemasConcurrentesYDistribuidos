
import java.io.*;
import java.rmi.*;

/**
 * Esta clase representa al objeto cliente de un objeto distribuido de la clase
 * Fecha, que implementa la interfaz remota DiaDelaFechaInterfaz
 */
public class ClienteFecha {

    public static void main(String args[]) {
        try {
            // Preparamos los objetos necesarios para leer de la corriente de
            // entrada bloques de datos.
            // Vamos a necesitar leer el nombre de la máquina en la que
            // se ejecuta el objeto servido, asi como el numero de puerto bajo
            // el que se ejecuta el Registro RMI
            int PuertoRMI;
            String nombreHost;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("Entrar el nombre del host del Registro RMI:");
            nombreHost = br.readLine();
            System.out.println("Entrar el numero de puerto del registro RMI:");
            String numeroPuerto = br.readLine();
            PuertoRMI = Integer.parseInt(numeroPuerto);

            /**
             * Construir el registroURL, para el protcolo rmi, concatenando en
             * un "string" el nombre de la máquina servidora, el numero de 
             * puerto y, al final, el nombre simbolico del servicio que se 
             * implementa con del objeto distribuido.
             */
            String URLServer = "rmi://"+nombreHost+":"+numeroPuerto+"/fecha";
            /**
             * Encontrar el objeto remoto (lookup) en servicio de nombres 
             * (Naming) del registro RMI y transmitirlo a un objeto de la 
             * interfaz remota ("DiaDelaFechaInterface")
             */
            DiaDelaFechaInterface objInterfaz = (DiaDelaFechaInterface)Naming.lookup(URLServer);

            System.out.println("Consulta completada ");

            /**
             * Ahora, ya podemos llamar al metodo remoto y mostrarlo en la
             * patalla local.
             */

			System.out.println(objInterfaz.getDaytime());
        } catch (Exception e) {
            System.out.println("Excepcion en el ClienteFecha: " + e);
        }
    } // end main   
}// end ClienteFecha
