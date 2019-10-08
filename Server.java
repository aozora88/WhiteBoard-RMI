import java.rmi.Naming; 

public class Server
{
    // Implement the constructor of the class 
    public Server() 
    { 
        try { 
            // Create a object reference for the interface 
            ControlImpl c = new ControlImpl(); 
  
            // Bind the localhost with the service 
            Naming.rebind("rmi://localhost/whiteboard", c); 
        } 
        catch (Exception e) { 
            // If any error occur 
            System.out.println("ERR: " + e); 
        } 
    } 
  
    public static void main(String[] args) 
    { 
        // Create an object 
        new Server(); 
    } 
}