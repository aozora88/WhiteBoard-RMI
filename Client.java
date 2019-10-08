import java.rmi.Naming; 
import java.net.MalformedURLException; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException; 

public class Client
{
    static Server con;

    public static void openConnection()
    {
        try{
            con = new Server();
            Naming.lookup("rmi://localhost/whiteboard");

        }catch(MalformedURLException murle) { 
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
        // cria a conexão com o servidor
        openConnection();

        // abre a interface
        new MouseTracker().start();
    }
}