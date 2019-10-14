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
    private static Control look_up2;
    public static String IP;

    public static void openConnection(String IP, boolean lk1)
    {
        try{
            if(lk1)
                look_up = (Control) Naming.lookup("rmi://"+IP+"/whiteboard");
            else
                look_up2 = (Control) Naming.lookup("rmi://"+IP+"/whiteboard");
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

    public static void transferBoard(String[] args) {
        openConnection(args[3], false);
        try {
            if (look_up.containsBoard(args[2])) {
                Board board = look_up.transferBoard(args[2], args[3]);
                look_up2.receiveBoard(board);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        IP = args[1];
        openConnection(IP, true);
        if(args[0].equals("-q"))
        {
            queryBoards();
        } else if(args[0].equals("-t"))
        {
            transferBoard(args);
        }
    }
}