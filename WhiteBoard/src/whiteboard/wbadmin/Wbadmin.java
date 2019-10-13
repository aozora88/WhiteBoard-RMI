package whiteboard.wbadmin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import whiteboard.server.Control;
import whiteboard.util.Board;
import whiteboard.util.User;

public class Wbadmin
{
    //public static ControlImpl con;
    private static Control look_up;
    public static String IP;

    public static void openConnection()
    {
        try{
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

    public static void queryBoards() {
        try {
            LinkedList<Board> boards = look_up.listBoards();
            for(Board board : boards) {
                System.out.println(board.getName());
                for(User user : board.getUserList()) {
                    System.out.println("\t"+user.getNickname());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        if(args[0].equals("-q"))
        {
            IP = args[1];
            openConnection();
            queryBoards();
        }
    }
}