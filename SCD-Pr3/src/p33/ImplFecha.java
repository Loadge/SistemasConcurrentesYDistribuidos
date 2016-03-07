package p33;

import java.rmi.*;
import java.util.*;
import java.rmi.server.*;

/**
 * Esta clase implementa la interfaz remota
 * DiaDelaFechaInterface
 */
public class ImplFecha extends UnicastRemoteObject
        implements DiaDelaFechaInterface {
    /**
     * Programar aqui los metodos necesarios: el constructor
     */
    public ImplFecha() throws RemoteException{
        super();
    }
    //Métodos que diga la interfaz a la que estamos extendiendo: que solo es 1.
    public String getDaytime() throws java.rmi.RemoteException{
        return new Date().toString();
    }
} // end ImplFecha
