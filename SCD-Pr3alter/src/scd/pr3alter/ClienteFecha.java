 import java.io.*;
    import java.rmi.*;
    /**
     * Esta clase representa al objeto cliente de un objeto distribuido
     * de la clase Fecha, que implementa la interfaz remota
     * DiaDelaFechaInterface
     */
    public class ClienteFecha {
      public static void main(String args[]) {
         try {
          //Preparamos los objetos necesarios para leer de la corriente de 
          //entrada bloques de datos.
         // Ahora vamos a necesitar leer el nombre de la máquina en la que 
         //se ejecuta el objeto servido, asi como el numero de puerto bajo 
         //el que se ejecuta el Registro RMI
         int PuertoRMI;
         String nombreHost;
         InputStreamReader is = new InputStreamReader(System.in);
         BufferedReader br = new BufferedReader(is);
         System.out.println("Entrar el nombre del host del Registro RMI:");
         nombreHost = br.readLine();
         System.out.println("Entrar el numero de puerto del registro RMI:");
         String numeroPuerto = br.readLine();
         PuertoRMI = Integer.parseInt(numeroPuerto);
         // construir el registroURL, para el protcolo rmi, concatenando en 
         //un "string" el nombre de la máquina servidora, el numero de puerto y, 
         //al final, el nombre simbolico del servicio que se implementa con del 
         //objeto distribuido
        /**************A completar************************
         // encontrar el objeto remoto (lookup) en servicio de nombres (Naming) 
         // del Rgistro RMI  y transmitirlo a un objeto de la interfaz 
         // remota ("InterfazHola")
        /**************A completar************************/
         System.out.println("Consulta completada " );
         // Ahora, ya poddemos llamar al metodo remoto y mostrarlo en la 
         //patalla local
       /**************A completar************************/
      } // fin try
      catch (Exception e) {
         System.out.println("Excepcion en el ClienteFecha: " + e);
      }
   } //fin main
}//fin class