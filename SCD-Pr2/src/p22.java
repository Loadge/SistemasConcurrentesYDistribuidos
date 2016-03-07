import Utilities.*;
import java.util.Random;

//Monitor para sincronizar la actuación de los 3 fumadores y el estanquero
class Control {
    // Declaracion de las variables de instancia del monitor
    // y de las variables estaticas finales que representan los estados del monitor:
    // LISTO = 0 : para indicar a 1os fumadores que ya hay 2 ingredientes en la mesa
    // ESPERANDO = 1 : mientras el fumador esta esperando los ingredientes adecuados
    // INICIO = 2 : estado inicial; el estanquero espera que las hebras fumadoras
    // sean lanzadas.

    private static final int LISTO = 0;
    private static final int ESPERANDO = 1;
    private static final int INICIO = 2;
    private int estado = INICIO;
    private int cont = 0;
    private char ingrediente = 'f'; // valor inicial no valido

    public synchronized void generar_Ingredientes() {
        /** Generar aletoriamente un numero del conjunto={1,2,3}
            y asignarlo a la variable selector **/
        Random ran = new Random();
        
        int selector = (Math.abs((ran.nextInt())%3));
        selector++;
        
        switch (selector) {
            case 1:
                // al fumador con cerillas se le autoriza fumar
                this.ingrediente = 'c';
                break;
            case 2:
                // al fumador con tabaco se le autoriza fumar
                this.ingrediente = 't';
                break;
            case 3:
                // al fumador con papel se le autoriza fumar
                this.ingrediente = 'p';
                break;
            default:
                System.out.println("Error al generar los ingredientes");
                break;
        }
    }
    
    // lee el ingrediente del fumador autorizado
    public synchronized char leer_Ingrediente() {
        return this.ingrediente;
    }
    
    // informa de que fumador esta liando
    public synchronized void informar_Liar(char ingred) {
        switch (ingred) {
            case 'c':
                System.out.println("El fumador con cerillas lia el cigarro \n");
                break;
            case 't':
                System.out.println("El fumador con tabaco lia el cigarro \n");
                break;
            case 'p':
                System.out.println("El fumador con papel lia el cigarro \n");
                break;
            default:
                System.out.println(
                "ERROR: lo que se ha pasado no es un ingrediente");
                break;
        }
    }
    
    public synchronized void fumador_Espera(char ingred) {
        // Los 3 fumadores han de ejecutar este metodo y se quedan esperando,
        // antes de que el fumador que tiene el ingrediente que falta pase 
        // a liar 1 cigarro. Solo cuando haya entrado aqui el tercer fumador 
        // puede lanzarse la hebra "estanquero"

        /** Esperar a que 'ingred' sea el de este fumador, 
            ingred == ingrediente, y a que el estanquero 
            notifique el estado == LISTO. **/
        while(ingred != ingrediente ||estado != LISTO){
            cont++;     //Un fumador entra en el bucle
            if(cont==3){
                estado = ESPERANDO;
            }
            try{
                wait();
            }catch (InterruptedException ex) {
                System.err.println("Error al esperar: Fumador");
            }
        }
		/** Solo cuando haya entrado el tercer fumador
			se podra modificar estado = ESPERANDO */
            if(cont==3){
                estado=ESPERANDO;
            }
			
        /** Los fumadores han de notificar a los demas fumadores para que 
            avancen si pueden.**/
            notifyAll();
        
        //Se informa ahora de que fumador pasa a liar el cigarro
        informar_Liar(ingred);
    }
    
    public synchronized void terminar() {
        //El fumador avisa que ha terminado de fumar
        System.out.println("El fumador termina de fumarse el cigarro\n"
                         + "/--------------------------------------/\n");
        
        /** notifica para que el estanquero ponga otros 2 ingredientes en la mesa **/
        cont=0;
        estado=ESPERANDO;
        notifyAll();
    }

    public synchronized void actuacion_Estanquero() {
        char autorizado;
        
        /** Codigo de sincronizacion del estanquero.
            El estanquero ha de esperar en el estado inicial hasta que
            las 3 hebras fumadoras pasen al estado ESPERANDO los ingredientes. **/
        while(estado != ESPERANDO){
        try{
                wait();
        }catch(InterruptedException ex){
            System.err.println(":(");
        }
            }  

        // El estanquero pone los 2 ingredientes en la mesa mediante generar_Ingredientes()
        generar_Ingredientes();
        autorizado = leer_Ingrediente();
        
        switch (autorizado) {
            case 'c':
                System.out.println("El estanquero pone en la mesa tabaco y papel");
                System.out.flush();
                break;
            case 't':
                System.out.println("El estanquero pone en la mesa cerillas y papel");
                System.out.flush();
                break;
            case 'p':
                System.out.println("El estanquero pone en la mesa tabaco y cerillas");
                System.out.flush();
                break;
            default:
                System.out.println(
                "ERROR: en el paso del parametro ingredientes");
        }
        
        /** Ya estan los ingredientes en la mesa y hay que cambiar el estado del monitor **/
        estado=LISTO;
        

        /** El estanquero notifica a los fumadores y se bloquea hasta que el fumador 
            correspondiente se haya fumado su cigarro. **/
        notifyAll();
        while(estado==LISTO){
            
           try{
                wait();
            }catch (InterruptedException ex) {
                System.err.println("Error al esperar: Estanquero");
            }
        }        
    }
} // end Control

class Fumador extends p22 implements Runnable
{
    //Ahora se declaran las variables de la clase, utilizando el modificador de 
    //ambito apropiado: private, public, protected...
    private volatile Thread continua;
    private char ing; //Para almacenar el ingrediente que posee el fumador
    
    // Declarando los metodos necesarios para crear, iniciar y parar las 
    // instancias de esta clase.
    public Fumador(char ing) {
        this.ing = ing;
        this.start();
    }

    public void start() {
        continua = new Thread(this);
        continua.start();
    }

    public void stop() {
        continua = null;
    }

    public void run() {
        Random randGen = new Random();
        /** Simula la actuacion del fumador en un bucle indefinido 
            (hasta que se ejecute el metodo de parada). El tiempo que tarda
            en fumarse un cigarro se puede programar con:
               Random randGen= new Random();
               ...
               int fumada =  1 + Math.abs((randGen.nextInt())%t_max_fumando)*1000;
               System.out.print("...fumando..."); System.out.flush();
               Thread.sleep(fumada);
          Se ha de llamar a los metodos fumador_espera(ing) del monitor control 
          y al final al metodo terminar() para avisar al estaquero que ya ha 
          terminado de fumar y pase a poner otros 2 ingredientes en la mesa. **/
        while(true){
            m.fumador_Espera(ing);
            
            int fumada = 1+Math.abs((randGen.nextInt())%t_max_fumando)*1000;
            System.out.println("El fumador " + ing + " está fumando.");
            System.out.flush();
            try{
                Thread.sleep(fumada);
            }catch(InterruptedException ex){
                System.out.println("Error al intentar fumar.");
            }
            m.terminar();
        }
    }
} // end Fumador

class Estanquero extends p22 implements Runnable
{
    // Ahora se declaran las variables de la clase. Utilizar el modificador de 
    // ambito apropiado: private, public, protected...
    private volatile Thread continua;
    
    // Declarando los metodos necesarios para crear, iniciar y 
    // parar las instancias de esta clase.

    public Estanquero() {
        this.start();
    }

    public void start() {
        continua = new Thread(this);
        continua.start();
    }

    public void stop() {
        continua = null;
    }

    public void run() {
        Thread estaHebra = Thread.currentThread();
        /** Simula la actuacion del estanquero en un bucle indefinido 
            (hasta que se ejecute el metodo de parada). **/
        while(true){
            m.actuacion_Estanquero();
        }
    }
} // end Estanquero


//*********************** Clase del Principal ******************
class p22 {

    // el tiempo maximo que tarda un fumador en fumar un cigarro liado
    static int t_max_fumando = 1; 
    // el tiempo de ejecucion de la simulacion 
    static int t_max_simulacion = 20; 
    // declaracion del monitor 'Control'
    static Control m = new Control();

    public static void main(String[] args) {
        GetOpt opt = new GetOpt(args, "Ut:E:");
        Scheduler scheduler = new Scheduler(5);
        int opcion = -1;
        
        // procesa argumentos
        while ((opcion = opt.getopt()) != opt.optEOF) 
        {
            if ((char) opcion == 'U') {
                System.out.println("Uso: -t <tiempo maximo de fumada de cualquier fumador >");
                System.out.println("     -E <tiempo de ejecucion de la simulacion>");
                System.exit(1);
            } else if ((char) opcion == 't') {
                t_max_fumando = opt.processArg(opt.optArgGet(), t_max_fumando);
            } else if ((char) opcion == 'E') {
                t_max_simulacion = opt.processArg(opt.optArgGet(), t_max_simulacion);
            } else {
                System.out.println("Los argumentos no se han obtenido correctamente");
                System.out.flush();
                System.exit(1);
            }
        }
        
        /** Crear los 3 objetos fumador[i]  y estanquero 
            (e iniciarlos, si no se ha previsto ya dentro del constructor) **/
        Fumador fumadorC;
        Fumador fumadorT;
        Fumador fumadorP;
        Estanquero estanquero;
        
        fumadorC=new Fumador('c');
        fumadorT=new Fumador('t');
        fumadorP=new Fumador('p');
        estanquero = new Estanquero();

        // A dormir para dejar actuar a las hebras...
        try {
            Thread.sleep(t_max_simulacion * 1000);
        } catch (InterruptedException ex) {
            System.err.println("Interrumpido en el Principal" + ex);
        }
        
        /** Terminar la ejecucion de los objetos con un metodo propio de la 
            clase Fumador y Estanquero */
        fumadorC.stop();
        fumadorT.stop();
        fumadorP.stop();
        estanquero.stop();

        //... y ahora se termina la aplicacion completa sin esperar mas.
        System.out.println();
        System.out.println("Se acabo!!!");
        System.exit(0);
    }
} // end p22
