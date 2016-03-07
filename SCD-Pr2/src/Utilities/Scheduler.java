package Utilities;
public class Scheduler implements Runnable {
  private int timeSlice = 0; //milisegundos
  private Thread t = null;
  public Scheduler (int timeSlice) {
    this.timeSlice = timeSlice;
    t = new Thread(this, "scheduler"); //crea hebra
    t.setPriority(Thread.MAX_PRIORITY); //indica la maxima prioridad
    t.setDaemon(true);  //creamos la hebra como demonio
    t.start();
  }
  public void run() {
   while (true) 
     try {
        Thread.sleep(timeSlice);
     }
     catch (InterruptedException e) {
       System.out.println(t.getName() + " interrumpida la multiplexacion en tiempo!!!");
     }
  }
}
