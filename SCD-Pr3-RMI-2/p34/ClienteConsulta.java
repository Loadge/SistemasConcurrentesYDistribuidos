
import java.io.*;
import java.rmi.*;

/**
 * Esta clase representa al objeto cliente de un objeto distribuido de la clase
 * Consultas, que implementa la interfaz remota InterfazConsultas
 */
public class ClienteConsulta {

    public static void main(String args[]) {
        try {
            /**
             * Preparamos los objetos necesarios para leer de la corriente
             * de entrada bloques de datos.
             */
            
            /**
             * Necesitamos leer el nombre de la máquina en la que se
             * ejecuta el objeto servidor, asi como el numero de puerto 
             * desde el que se ejecuta el Registro RMI.
             */
            
            // Leemos la contestacion que da el cliente a una pregunta.
            System.out.println("Cree que el Granada debe hacer un esfuerzo "
                    + "para mantener a Uche?");
            System.out.println("Conteste con si, no o ns/nc:");
            String voto = br.readLine();

            /** 
             * Asignamos un valor a decision erroneo
             * cambiamos ese valor si la respuesta se
             * ha realizado correctamente y asignamos a cada
             * contestacion su numero de decision correspondiente
             */
            
            /**
             * Construir el registroURL, para el protocolo rmi, concatenando
             * en un "string" el nombre de la máquina servidora, el numero 
             * de puerto y, al final, el nombre simbólico del
             * servicio que se implementa con del objeto distribuido.
             */
            
            /** 
             * Encontrar el objeto remoto (lookup) en servicio de nombres 
             * (Naming) del Registro RMI y transmitirlo a un objeto de la
             * interfaz remota ("InterfazConsultas").
             */
            
            System.out.println("Consulta completada ");

            /**
             * Ahora, ya podemos llamar al metodo remoto que se encarga de 
             * aceptar el voto emitido y mostrarlo en la patalla local
             */
            
            // Leemos otra linea por pantalla para preguntar al cliente si 
            // quiere ver el recuento obtenido hasta ahora
            System.out.println("Quiere ver el recuento obtenido hasta ahora? "
                    + "(si o no):");
            String recuento = br.readLine();

            // Llamamos al metodo remoto si la contestacion ha sido afirmativa
            if (recuento.equalsIgnoreCase("si")) {
                /**
                 * Completar
                 */
                System.out.println(mensaje);
            }
        } catch (Exception e) {
            System.out.println("Excepcion en el ClienteConsulta: " + e);
        }
    } // end main
}// end ClienteConsulta
