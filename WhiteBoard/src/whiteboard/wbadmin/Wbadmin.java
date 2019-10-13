package whiteboard.wbadmin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import whiteboard.server.Control;

import java.rmi.registry.LocateRegistry;


public class Wbadmin
{
    //public static ControlImpl con;
    private static Control look_up;
    public static String IP;

    /**
     * INCOMPLETE
     */
    public static void openConnection(String args[])
    {
        try{
            //con = new ControlImpl();
            //System.setProperty( "java.rmi.server.hostname", IP); 
            //LocateRegistry.createRegistry(8080); 
            look_up = (Control) Naming.lookup("rmi://"+IP+"/whiteboard");

            //get data and send args info to server

            //print data

        }
        catch (MalformedURLException murle) { 
            System.out.println("\nMalformedURLException: "
                               + murle); 
        }
        catch (RemoteException re) { 
            System.out.println("\nRemoteException: "
                               + re); 
        } 
  
        catch (NotBoundException nbe) { 
            System.out.println("\nNotBoundException: "
                               + nbe); 
        } 
  
        catch (java.lang.ArithmeticException ae) { 
            System.out.println("\nArithmeticException: " + ae); 
        } 
    }
    
    public static void main(String[] args)
    {
        IP = args[0];
        // cria a conexão com o servidor
        openConnection(args);

        
    }
}