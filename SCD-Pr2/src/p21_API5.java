package API5;


import java.io.*;
import java.lang.*;
import java.lang.Math;
import java.util.*;
import java.util.Random;
import Utilities.*;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {

    private int numElementos = 0;
    private double[] buffer = null;
    private int atras = 0;
    private int frente = 0;
    private final int MAX;

    //tus variables
    public Lock lock = new ReentrantLock();
    public Condition lleno= lock.newCondition();
    public Condition vacio= lock.newCondition();
    
    public Buffer(int numElem) {
        // El argumento numElem indica el tamanio maximo del buffer
        this.MAX = numElem;
        this.buffer = new double[MAX];
        this.numElementos = 0;
        frente = 0;
        atras = 0;
        
        /** Inicializar tus variables **/
    }

    // Otros metodos, despositar y extraer
    public double extraer(int id) {
        double salida;
        int i;

        //hay un consumidor mas esperando

        /** tu codigo **/
	try{	
            lock.lock();
        
            while (numElementos == 0) {  //De este bucle sólo se sale cuando una hebra hace notifyAll(). Entonces, vuelve a entrar en el while, y seguramente la condición haya cambiado, y hará el código de abajo.
                try {
                    System.out.println("El consumidor "
                            + id + " esta esperando");
                    
                    /**sincronizacion**/
                    vacio.await();
                } catch (InterruptedException ex) {
                    System.err.println(
                            "Se ha producido un error al extraer: " + ex);
                }
            }
            
            /** tu codigo **/ //Extraer el dato:
            
            salida = buffer[atras];
            numElementos--;
            atras = (atras + 1) % MAX;
        
            /** notificar a los que esperan **/
            lleno.signal();
        }finally{
            lock.unlock();
        }
        return salida;
    }

    public void depositar(double valor, int id) {
        int i;
        //hay un productor esperando mas

        /** tu codigo **/

        try{
            lock.lock();
            while (numElementos >= MAX) {
                try {
                    System.out.println("El productor "
                            + id + " esta esperando");
                    
                    /** sincronizacion **/
                    lleno.await();
                } catch (InterruptedException ex) {
                    System.err.println(
                            "Se ha producido un error al depositar: " + ex);
                }
            }
            
            /** tu codigo **/
            
            buffer[frente] = valor;
            numElementos++;
            frente = (frente + 1) % MAX;
            
            /** notificar a los que esperan **/
            vacio.signal();
            //return null;
        }finally {
            lock.unlock();
        }
     }
} // end Buffer

class Productor extends p21_API5 implements Runnable {
    static final int MAXIMO = 10000;
    private volatile Thread continua;
    private int id;
    private int iteraciones;

    public void start() {
        continua = new Thread(this);
        continua.start();
    }

    //Re-implementamos el método stop() nosotros mismos. Así, cuando llamemos a stop() no aparecerá tachado.
    // mata la hebra.
    public void stop() {
        continua = null;
    }

    public Productor(int id, int iteraciones) {
        this.id = id;
        this.iteraciones = iteraciones;
        this.start();
    }

    public void run() {
        int i = 0;
        Random randGen = new Random();//Para los numeros aleatorios
        Thread estaHebra = Thread.currentThread();
        while ((continua == estaHebra) && (i < iteraciones)) {
            
            int dato = 1 + Math.abs((randGen.nextInt()) % MAXIMO);
            System.out.println("El productor " + id + " va a depositar en el buffer el valor " + dato);
            try {
                
                /** Depositar en el buffer el valor generado **/
                bb.depositar(dato, this.id);   //this se puede omitir.
                
                // Hacemos un pequenio sleep en terminos de segundos
                int parar = 1 + Math.abs((randGen.nextInt()) % MAXIMO);
                Thread.sleep(parar);
                
            } catch (InterruptedException ex) {
                System.out.println("Me fastidiaron el sleep!" + ex);
            }
            i++;
        }
    }
} // end Productor

class Consumidor extends p21_API5 implements Runnable {
    static final int MAXIMO = 10000;
    private volatile Thread continua;
    private int id;
    private int iteraciones;

    public void start() {
        continua = new Thread(this);
        continua.start(); //ejecuta run() (el de más abajo).
    }

    //Re-implementamos el método stop() nosotros mismos. Mata la hebra.
    public void stop() {
        continua = null;
    }

    public Consumidor(int id, int iteraciones) {
        this.id = id;
        this.iteraciones = iteraciones;
        this.start();
    }

    public void run() {
        int i = 0;
        double valor;
        Random randGen = new Random();//Para los numeros aleatorios
        Thread estaHebra = Thread.currentThread();
        while ((continua == estaHebra) && (i < iteraciones)) {
            try {
                
                /** Extraer un valor del buffer **/
                valor = bb.extraer(this.id); //this se puede omitir
                
                //Hacemos un pequenio sleep en terminos de seg.
                int parar = 1 + Math.abs((randGen.nextInt()) % MAXIMO)*2000;
                
                System.out.println("El consumidor " + id + " va a consumir del buffer el elemento " + valor);
                Thread.sleep(parar);
            } catch (InterruptedException ex) {
                System.out.println("Me fastidiaron el sleep!" + ex);
            }
            i++;
        }
    }
} // end Consumidor

public class p21_API5 {
    static final int tamBuffer = 20;
    static Buffer bb;
    static int P = 10; //numero de productores
    static int C = 10; //numero de consumidores
    static int vecesP = 1; //numero de veces que se ejecuta el productor
    static int vecesC = 1; //numero de veces que se ejecuta el consumidor

    public static void main(String[] args) {
        GetOpt opt = new GetOpt(args, "UP:v:C:w:E:");
        Scheduler scheduler = new Scheduler(5);
        bb = new Buffer(tamBuffer);

        int e = 20;
        int opcion = -1;
        System.out.println("**");

        while ((opcion = opt.getopt()) != opt.optEOF) //procesa argumentos
        {
            if ((char) opcion == 'U') {
                System.out.println("Uso: -P <numero de productores en la simulacion>");
                System.out.println("     -v <numero de veces que cada productor actua>");
                System.out.println("     -C <numero de consumidores en la simulacion>");
                System.out.println("     -w <numero de veces que cada consumidor actua>");
                System.out.println("     -E <tiempo de ejecucion>");
                System.exit(1);
            } else if ((char) opcion == 'P') {
                P = opt.processArg(opt.optArgGet(), P);
            } else if ((char) opcion == 'v') {
                vecesP = opt.processArg(opt.optArgGet(), vecesP);
            } else if ((char) opcion == 'C') {
                C = opt.processArg(opt.optArgGet(), C);
            } else if ((char) opcion == 'w') {
                vecesC = opt.processArg(opt.optArgGet(), vecesC);
            } else if ((char) opcion == 'E') {
                e = opt.processArg(opt.optArgGet(), e);
            } else {
                System.out.println("los argumentos no se han obtenido correctamente");
                System.out.flush();
                System.exit(1);
            }
        }
        
        System.out.println("comienza la simulacion con np= " + P + "  y con nc= " + C);
        
        // Crear los objetos productores y consumidores      
        Productor[] productor = new Productor[P];
        Consumidor[] consumidor = new Consumidor[C];

        for (int i = 0; i < P; i++) {
            productor[i] = new Productor(i, vecesP);
        }
        for (int i = 0; i < C; i++) {
            consumidor[i] = new Consumidor(i, vecesC);
        }

        // Dar tiempo a la simulacion
        try {
            Thread.sleep(e * 2000);
        } catch (InterruptedException ex) {
            System.err.println("Siesta interrumpida en Principal" + ex);
        }

        // Terminar con el metodo propio la ejecucion de los objetos
        for (int i = 0; i < P; i++) {
            productor[i].stop();
        }
        for (int i = 0; i < C; i++) {
            consumidor[i].stop();
        }

        System.out.println();
        System.out.println("Se acabo!!!");
        System.exit(0);
    }
} // end p21
