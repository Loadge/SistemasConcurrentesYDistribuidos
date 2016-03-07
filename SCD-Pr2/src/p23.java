
import Utilities.*;
import java.util.Random;

//El programa no tiene en cuenta las veces que se repiten las personas.
class Habitacion {
    //declaracion de las variables del monitor
    protected int na1, na2, nb1, nb2;

	/** Declara las variables adicionales que puedas necesitar **/
    protected boolean saliendo;
    
	
    public Habitacion() {
        this.na1 = 0;
        this.na2 = 0;
        this.nb1 = 0;
        this.nb2 = 0;
        /** Inicializa las variables adicionales que puedas necesitar **/
        saliendo = false;
    }

    public synchronized void entra_A() throws Exception {
        /** Codigo del metodo. Se sugiere incluir los mensajes:
            System.out.println("Persona A intentando entrar...");
            System.out.println("...Persona A en habitacion - na = " + (na1+1));
            para dar mejor "visualizacion" a lo que se va a realizar.**/
        System.out.println("PERSONA A intentando entrar");
        /** Controlar que no entren As cuando esta saliendo una pandilla. **/
        while(saliendo){
            wait();
        }
        na1++;
        System.out.println("Personas A en habitación: "+na1);
        System.out.println("Personas B en habitación: "+nb1);
        /** Controlar que solo salga una persona A con cada pandilla, independiente
            de que en el momento de salir pueda haber mas de una en la habitacion. **/
        ComprobarCola(0);
        notifyAll();
    }

    public synchronized void entra_B() throws Exception{
        /** Codigo del metodo. Se sugiere el incluir en este los mensajes:
            System.out.println("Persona B intentando entrar...");
            System.out.println("...Persona B en habitacion - nb = " + (nb1+1));
            para dar mejor "visualizacion" a lo que se va a realizar.**/
        System.out.println("PERSONA B intentando entrar");
        
        /** Controlar que no entren Bs cuando esta saliendo una pandilla. **/
        while(saliendo){
            wait();
        }
        nb1++;
        System.out.println("Personas A en habitación: "+na1);
        System.out.println("Personas B en habitación: "+nb1);
        
        /** Controlar que salgan hasta 10 Bs con cada pandilla, independiente
            de que en el momento de salir pueda haber mas de 10 en la habitacion. **/
        ComprobarCola(1);
        notifyAll();
    }

    public synchronized void sale_B() throws Exception{
        /** Un thread-persona B sale del monitor.
            Controlar que se espera a que la persona A
            este preparada para salir con la pandilla
            y que no salgan mas de 10 Bs. **/
        while(!(na2 == 1 && nb2 == 10)){        //Mientras no se cumplan las condiciones de salida.
            wait();
        }
        PandillaSale();
    }

    public synchronized void sale_A() throws Exception {
        /** Un thread-persona A sale del monitor.
            Controlar que se espera a que la persona A
            este preparada para salir con la pandilla
            y que no se nos salgan mas de 10 Bs. **/
       while(!(na2 == 1 && nb2 == 10)){        //Mientras no se cumplan las condiciones de salida.
            wait();
        }
        PandillaSale();
        
    }

    /*
    public synchronized void terminarSalida() {
        na1 -=1;
        nb1 -=10;
        na2=0;
        nb2=0;
        saliendo=false; //Hemos terminado de salir.
        notifyAll();
    }
    */
    
    /** otros metodos que pudieras necesitar **/
    public synchronized void PandillaSale(){
       saliendo=true;
       while(nb2>0){
           nb2--;
           System.out.println("Personas B restantes en la cola: " + nb2);
       }
       while(na2!=0){
           na2=0;
           System.out.println("Personas A restantes en la cola: " + na2);
       }
       System.out.println("COLA: " + (nb2+na2));
       //Reajustar los valores na1,nb1
        na1 = na1 - 1;
        nb1 = nb1 - 10;
        nb2 = nb2 + nb1;
        saliendo=false;
        notifyAll();
    }
    public synchronized void ComprobarCola(int identificador){
        switch(identificador){
            case 0:
                if(na2<1){   //Comprobar si me puedo unir a una pandilla.
                    na2++;
                }
                break;
            case 1:
                if(nb2<10){   //Comprobar si me puedo unir a una pandilla.
                    nb2++;
                }
                break;
        }
        //Ponemos en cola la gente que está dentro pero no se ha metido en la cola.
        if(na2 == 0 && na1>0){
            na2++;
        }
        if(nb2 == 0 && nb2>0){
            nb2++;
        }
        System.out.println("Personas A en la cola: "+na2);
        System.out.println("Personas B en la cola: "+nb2);
    }
} // end Habitacion

class PersonaA extends p23 implements Runnable
{
    /** Declarar la variables. Utilizar el modificador de 
        ambito apropiado: private, public, protected,... **/
    private volatile Thread continua;
    /** Declarar los metodos necesarios para crear, iniciar y 
        parar las instancias de esta clase. **/
    
    public PersonaA(){
        this.start();
    }
    
    public void start(){
        continua = new Thread(this);
        continua.start();
    }
    
    public void stop(){
        continua = null;
    }
    
    @Override
    public void run() {
        int i=0;
        Random randGen = new Random();//Para los numeros aleatorios
        
        Thread estaHebra = Thread.currentThread();
        
        /** Controlar que 1 hebra-persona A puede entrar varias veces a la habitacion
            y que hay que pararla para que termine bien. **/
        while((continua != null && continua==estaHebra) && (i < p23.vecesA)){
           try{
               m.entra_A();
               
               // Hacemos un pequenio sleep en terminos de seg.
               int parar= 1 + Math.abs((randGen.nextInt()) % p23.ociosoA)*10;
               Thread.sleep(parar);
               m.sale_A();
           }catch(Exception ex){
               System.out.println("Algo ha fallado.");
           }
            
           i++;
    }
    }
} // end PersonaA

class PersonaB extends p23 implements Runnable/** falta indicar las relaciones de herencia e implementacion **/
{
    /** Declarar las variables. Utilizar el modificador de 
        ambito apropiado: private, public, protected,... **/
    
    private volatile Thread continua;
    
    public PersonaB(){
        this.start();
    }
    
    public void start(){
        continua = new Thread(this);
        continua.start();
    }
    
    public void stop(){
        continua = null;
    }

    /** Declarar los metodos necesarios para crear, iniciar y parar 
        las instancias de esta clase. **/
    
    @Override
    public void run() {
        int i=0;
        Random randGen = new Random();//Para los numeros aleatorios
        
        Thread estaHebra = Thread.currentThread();
        
        /** Controlar que 1 hebra-persona A puede entrar varias veces a la habitacion
            y que hay que pararla para que termine bien. **/
        while((continua != null && continua==estaHebra) && (i<p23.vecesB)){
           try{
               m.entra_B();
               
               // Hacemos un pequenio sleep en terminos de seg.
               int parar= 1 + Math.abs((randGen.nextInt()) % p23.ociosoB)*10;
               Thread.sleep(parar);
               m.sale_B();
           }catch(Exception ex){
               System.out.println("Algo ha fallado.");
           }
           i++;
    }
    }
} // end PersonaB

class p23 {

    static Habitacion m = new Habitacion();
    static int A = 10;
    static int B = 100;
    static int vecesA = 1;
    static int vecesB = 1;
    static int ociosoA = 1;
    static int ociosoB = 1;

    public static void main(String[] args) {
        GetOpt opt = new GetOpt(args, "UA:v:t:B:w:s:E:");
        Scheduler scheduler = new Scheduler(5);

        /** Declara las variables y crea los objetos que necesites **/
        int e = 20;
        int opcion = -1;
        
        System.out.println("**");
        
        while ((opcion = opt.getopt()) != opt.optEOF) //procesa argumentos
        {
            if ((char) opcion == 'U') {
                System.out.println("Uso: -A <numero personas 'A' en la simulacion>");
                System.out.println(
                "     -v <numero de veces que 1 persona 'A' entra en la habitacion>");
                System.out.println("     -t <tiempo ocioso de persona A en habitacion>");
                System.out.println("     -B <numero personas 'B' en la simulacion>");
                System.out.println(
                "     -w <numero de veces que 1 persona 'B' entra en la habitacion>");
                System.out.println("     -s <tiempo ocioso de persona B en habitacion>");
                System.out.println("     -E <tiempo de ejecucion>");
                System.exit(1);
            } else if ((char) opcion == 'A') {
                A = opt.processArg(opt.optArgGet(), A);
            } else if ((char) opcion == 'v') {
                vecesA = opt.processArg(opt.optArgGet(), vecesA);
            } else if ((char) opcion == 't') {
                ociosoA = opt.processArg(opt.optArgGet(), ociosoA);
            } else if ((char) opcion == 'B') {
                B = opt.processArg(opt.optArgGet(), B);
            } else if ((char) opcion == 'w') {
                vecesB = opt.processArg(opt.optArgGet(), vecesB);
            } else if ((char) opcion == 's') {
                ociosoB = opt.processArg(opt.optArgGet(), ociosoB);
            } else if ((char) opcion == 'E') {
                e = opt.processArg(opt.optArgGet(), e);
            } else {
                System.out.println("los argumentos no se han obtenido correctamente");
                System.out.flush();
                System.exit(1);
            }
        }
        System.out.println("comienza la simulacion con na= " + A + "  y con nb= " + B);
        
        /** Crear un array de hebras-personas para cada tipo de personas, A y B, e
            inicializar sus objetos segun el tipo de personas. **/
        PersonaA[] personasA = new PersonaA[A];
        PersonaB[] personasB = new PersonaB[B];
        
        for(int i=0 ; i < A ; i++){
            personasA[i]= new PersonaA();
        }
        for(int i=0 ; i < B ; i++){
            personasB[i]=new PersonaB();
        }

        // Dar tiempo a la simulacion
        try {
            Thread.sleep(e * 1000);
        } catch (InterruptedException ex) {
            System.err.println("Siesta interrumpida en Principal" + ex);
        }

        /** Terminar la ejecucion de los objetos-hebras llamando al metodo adecuado. **/
        for(int i=0 ; i < A ; i++){
            personasA[i].stop();
        }
        for(int i=0 ; i < B ; i++){
            personasB[i].stop();
        }

        System.out.println();
        System.out.println("Se acabo!!!");
        System.exit(0);
    }
} // end p23