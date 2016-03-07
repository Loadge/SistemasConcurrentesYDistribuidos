import java.rmi.*;
    import java.rmi.server.*;
    import java.rmi.registry.Registry;
    import java.rmi.registry.LocateRegistry;
    import java.net.*;
    import java.io.*;
    /**
     * Esta clase representa al sirviente de un objeto
     * distribuido de la clase Fecha, que implementa la interfaz
     * remota DiaDelaFechaInterface.
     */

    public class ServidorFecha  {
     public static void main(String args[]) {

      //***Preparamos los objetos necesarios para leer de la corriente de 
      //***entrada bloques de datos
       try{
      //Necesitamos saber el número de puerto bajo el que se va a ejecutar 
      //el registro RMI
         System.out.println("Entrar el numero de puerto del registro RMI:");
      //*** leeremos el numeroPuerto        

         lanzarRegistro(numeroPuertoRMI);

      // crear un objeto de la clase "ImplFecha" con el mensaje de la fecha
         /**********A completar****************/


      // construir el registroURL, para el protcolo rmi, concatenando en un 
      //"string" el nombre de la máquina servidora,
      // el numero de puerto y, al final, un nombre simbólico para el servicio 
      //que estamos implementando como un objeto distribuido
      /**********A completar****************/


      // Ligar el registro RMI con la referencia anterior del objeto "implHola"
    /**********A completar****************/

         System.out.println("El Servidor Fecha esta preparado.");
      }// fin try
      catch (Exception re) {
         System.out.println("Excepcion en el main() del  ServidorFecha: " + re);
      } // fin catch
  } // fin main

   // Este metodo inicia un registro RMI en el "host" local, si
   // todavia no existe en el numero de puerto especificado.
   private static void lanzarRegistro(int NumPuertoRMI)
      throws RemoteException{
      try {
         //obtener una referencia de objetos del registro RMI para que el
         //servicio dee nombres se ejecute bajo el puerto especificado
         // en el argumento
         /**********A completar****************/

        // La siguiente llamada levantara una excepcion si el registro RMI 
        // no existe todavia
         registro.list();
      }
      catch (RemoteException e) {
      //Si no hay ningun registro RMI valido ejecutandose en el numero que 
      //se paso como argumento, entonces hay que crear un registro 
      //en el puerto indicado
         /**********A completar****************/

      }
   } // fin lanzarRegistro


  // Este metodo lista los nombres registrados con el objeto registro RMI
     private static void listarRegistro(String registroURL)
     throws RemoteException, MalformedURLException {
         /**********A completar****************/

     } //fin listarRegistro
   } // fin class
