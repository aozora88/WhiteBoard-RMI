import java.net.MalformedURLException; 
import java.rmi.Naming; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException; 
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 * INCOMPLETE
 */
public class Client
{
    //public static ControlImpl con;
    private static Control look_up;
    public static String IP;

    public static String call_listarQuadro()
    {
        //call criar_quadro no ControlImpl
        
        return "- q1 \n - q2 \n - q3";
    }

    public static String call_sairQuadro(String nome, String usu)
    {
        //call criar_quadro no ControlImpl
        System.out.println(nome);
        System.out.println(usu);
        
        return "função sair quadro em produção";
    }

    public static String call_entrarQuadro(String nome, String usu)
    {
        //call criar_quadro no ControlImpl
        System.out.println(nome);
        System.out.println(usu);
        
        return "função entrar quadro em produção";
    }

    public static String call_criarQuadro(String nome, String usu)
    {
        //call criar_quadro no ControlImpl
        System.out.println(nome);
        System.out.println(usu);
        
        return "função criar quadro em produção";
    }

    public static double[] send_coord(double x1, double y1, double x2, double y2)
    {
        double[] resp = new double[4];
        resp[0] = 0;
        resp[1] = 0;
        resp[2] = 0;
        resp[3] = 0;
        try{
            resp = look_up.share_coord(x1, y1, x2, y2);
            System.out.println("Recebido");
            System.out.printf("%s %s %s %s%n", resp[0], resp[1], resp[2], resp[3]);
            System.out.println("Eviado");
            System.out.printf("%s %s %s %s%n", x1, y1, x2, y2);
            
        }catch(Exception e){ System.out.println("error client calling share_coord");}
        return resp;
    }


    public static void openConnection()
    {
        try{
            //con = new ControlImpl();
            //System.setProperty( "java.rmi.server.hostname", IP); 
            //LocateRegistry.createRegistry(8080); 
            look_up = (Control) Naming.lookup("rmi://"+IP+"/whiteboard");

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
        openConnection();

        // abre a interface
        new MouseTracker().start();
    }
}