import Utilities.*;
import java.lang.*;

class B1 extends Thread {

    int vueltas = 0;
    long siesta = 0;
    boolean yielD = false;

    public B1(String nombre, int vueltas, long siesta, boolean yielD) {
        super(nombre);           // llama al constructor de Thread
        this.siesta = siesta;
        this.vueltas = vueltas;
        this.yielD = yielD;
    }

    public void run() {
        for (int i = 0; i < vueltas || vueltas == 0; i++) {
            System.out.println("Hola no." + i + ", soy "+
                     /** llamar a getName para obtener nombre **/ this.getName() );
            if (siesta > 0) {
                try {
                   Thread.sleep(siesta);
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName()
                            + ": me fastidiaron la siesta!");
                }
            } else if (yielD){
                /** llamar a yield para pasar la hebra a estado ejecutable **/
                Thread.yield();
            }
        }
    }
} // end B1

class p20 {

    public static void main(String[] args) {
        GetOpt opt = new GetOpt(args, "Un:p:s:v:t:y");
        Scheduler scheduler = null;            // referencia al planificador
        opt.optErr = true;
        int opcion = -1;
        int nHebras = 1;                          // numero de hebras a crear
        int prio = Thread.currentThread().getPriority();  // prioridad hebras
        int sched = 0;                 // quantum de tiempo para planificador
        long siesta = 0;               // valor de tiempo para sleep
        int vueltas = 0;               // numero de iteraciones de las hebras
        boolean yielD = false;         // indica si llamar o no a yield

        while ((opcion = opt.getopt()) != opt.optEOF) // procesa argumentos
        {
            if ((char) opcion == 'U') {
                System.out.println("Uso:  -n <numero de Hebras> ");
                System.out.println("      -p + | -      // prioridad relativa a main");
                System.out.println("      -s <tiempo de planificacion>");
                System.out.println("      -v <numero de vueltas>");
                System.out.println("      -t <tiempo de siesta> | 0");
                System.out.println("      -y            // hacer yield() al iterar");
                System.exit(1);
            } else if ((char) opcion == 'n') {
                nHebras = opt.processArg(opt.optArgGet(), nHebras);
            } else if ((char) opcion == 'p') {
                char signo = opt.optArgGet().charAt(0);
                if (signo == '+') {
                    prio = Thread.currentThread().getPriority() + 1; // sube prioridad
                } else if (signo == '-') {
                    prio = Thread.currentThread().getPriority() - 1; // baja prioridad
                } else {
                    prio = Thread.currentThread().getPriority();     // misma prioridad
                }
            } else if ((char) opcion == 's') {
                if ((sched = opt.processArg(opt.optArgGet(), sched)) > 0) {
                    scheduler = new Scheduler(sched);  // crea planificador
                }
            } else if ((char) opcion == 'v') {
                vueltas = opt.processArg(opt.optArgGet(), vueltas);
            } else if ((char) opcion == 't') {
                siesta = opt.processArg(opt.optArgGet(), siesta);
            } else if ((char) opcion == 'y') {
                yielD = true;
            } else {
                System.exit(1);
            }
        }

        System.out.println("nHebras=" + nHebras + ", prio=" + prio + ", sched=" + sched
                + ", vueltas=" + vueltas + ", tsiesta=" + siesta + ", yield=" + yielD);

        /** Declarar un array la clase B1 **/
        B1 []b= new B1[nHebras];
        
        // Para cada objeto del array de la clase B1
        for (int i = 0; i < nHebras; i++) {
            /** Crear una nueva hebra [i] de la clase B1 **/
            b[i] = new B1(Integer.toString(i), vueltas, siesta, yielD);
            /** Indicar prioridad para [i] **/
            b[i].setPriority(prio);           
            /** Lanzar la hebra (llamar a start) **/
            b[i].start();
        }
        try {
            Thread.sleep(1000);
            System.out.println("main(): termine de dormir");
        } catch (InterruptedException e) {
            System.out.println("main(): me fastidiaron la siesta!");
        }
        if (vueltas == 0) // si las hebras se ejecutan en un bucle infinito
        {
            for (int i = 0; i < nHebras; i++) {
                /** Invocar stop para la hebra [i] del array de la clase B1 **/
                b[i].stop(); //Está tachado porque está obsoleto.
            }
        } else // si las hebras ejecutan un numero de vueltas finito
        {
            for (int i = 0; i < nHebras; i++) {
                try {
                    /** invocar join para la hebra [i] del array de la clase B1 **/
                    b[i].join();
                } catch (InterruptedException e) {
                    System.out.println("no puedo hacer join con hebra b-" + i);
                }
            }
        }
    }
} // end p20
