
import java.rmi.Naming; 

public class Server
{
    public static ControlImpl c;
    public static String IP;
    // Implement the constructor of the class 
    public Server() 
    { 
        try { 
            // Create a object reference for the interface 
             c = new ControlImpl(); 
  
            // Bind the localhost with the service 
            Naming.rebind("rmi://"+IP+"/whiteboard", c); 
        } 
        catch (Exception e) { 
            // If any error occur 
            System.out.println("ERR: " + e); 
        } 
    } 

    public static void main(String args[])
    {
        IP = args[0];
        new Server();
    }
}