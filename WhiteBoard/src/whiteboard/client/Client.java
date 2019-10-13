package whiteboard.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import whiteboard.server.Control;
import whiteboard.util.MouseTracker;
import java.util.LinkedList;
import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.User;
import whiteboard.util.NotificationWrapper;

public class Client {
    // public static ControlImpl con;
    public static Control look_up;
    public static String IP;

    public static void changeIP(String newIP)
    {
        IP = newIP;
        openConnection();
    }

    public static String call_listarQuadro()
    {
        String resp = new String("Quadros:\n");
        try{
            LinkedList<Board> lista =  look_up.listBoards();
            for (Board quadro : lista) {
                resp = resp.concat("- "+quadro.getName());
                resp = resp.concat("\n");
            }
            return resp;
        }catch(Exception e){
            System.out.println("Erro calling function listBoards");
        }
        return resp;
    }

    public static NotificationWrapper<Void> call_sairQuadro(User user)
    {
        NotificationWrapper<Void> resp;
        try{
            resp = look_up.exitBoard(user);
            return resp;
        }catch(Exception e){
            resp = new NotificationWrapper<Void>(false, "erro calling exitBoard");
            return resp;
        }
    }

    public static NotificationWrapper<User> call_entrarQuadro(String nome, String usu)
    {
        NotificationWrapper<User> resp;
        try{
            resp = look_up.enterBoard(nome, usu);
            return resp;
        }catch(Exception e){
            resp = new NotificationWrapper<User>(false, "erro calling enterBoard");
            e.printStackTrace();
            return resp;
        }
    }

    public static NotificationWrapper<User> call_criarQuadro(String nome, String usu)
    {
        NotificationWrapper<User> resp;
        try{
            resp = look_up.createBoard(nome, usu);
            return resp;
        }catch(RemoteException e){
            e.printStackTrace();
            System.out.println("RemoteException");

        }

        resp = new NotificationWrapper<User>(false, "erro calling createBoard");
        return resp;
    }

    public static void send_coord(User user, double x1, double y1, double x2, double y2)
    {
        double[] point1 = new double[2];
        double[] point2 = new double[2];

        point1[0] = x1;
        point1[1] = y1;
        point2[0] = x2;
        point2[1] = y2;
        try{
            look_up.createLine(user, point1, point2);
        }catch(Exception e){ 
            System.out.println("error client calling createLine");
        }
    }

    public static NotificationWrapper<LinkedList<Line>> atualizaBoard(User user)
    {
        try{
            NotificationWrapper<LinkedList<Line>> linhas = look_up.getLines(user);
            return linhas;
        }catch(Exception e){
            return null;
        }
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