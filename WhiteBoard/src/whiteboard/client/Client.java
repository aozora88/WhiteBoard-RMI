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

/**
 * @file  Client.java
 * @brief Arquivo com as funções relacionadas a interface
*/

public class Client {
/*******************************************************************
*   GLOBAL VARIABLES
*******************************************************************/
    public static Control look_up;
    public static String IP;

/*******************************************************************
*   IMPLEMENTATION
*******************************************************************/

    public static void changeIP(String newIP)
    {
        IP = newIP;
        openConnection();
    }
    /**
    * @fn public static void changeIP(String newIP)
    * @brief muda o ip e reestabelece a conexão com outro server
    * @param String newIP - novo IP do server
    * @return null
    */


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
    /**
    * @fn public static String call_listarQuadro()
    * @brief acessa a função lista quadros ativos do server
    * @param null
    * @return String resp - string formatada com os nomes dos quadros ativos no server
    */


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
    /**
    * @fn public static NotificationWrapper<Void> call_sairQuadro(User user)
    * @brief acessa a função sair quadro do server
    * @param User user - usuario loggado no server
    * @return NotificationWrapper<Void> resp - resposta do server contendo: status, msg, data(void)
    */

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
    /**
    * @fn public static NotificationWrapper<User> call_entrarQuadro(String nome, String usu)
    * @brief acessa a função entrar quadro do server
    * @param String nome - nome do quadro a ser criado
    * @param String usu - nome do usuario a ser criado
    * @return NotificationWrapper<User> resp - resposta do server contendo: status, msg, data(User criado)
    */

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
    /**
    * @fn public static NotificationWrapper<User> call_criarQuadro(String nome, String usu)
    * @brief acessa a função criar quadro do server
    * @param String nome - nome do quadro existente
    * @param String usu - nome do usuario a ser criado
    * @return NotificationWrapper<User> resp - resposta do server contendo: status, msg, data(User criado)
    */


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
    /**
    * @fn public static void send_coord(User user, double x1, double y1, double x2, double y2)
    * @brief envia linha desenhada pelo usuario ao server
    * @param User user - usuario loggado
    * @param double x1 - posição inicial x da linha
    * @param double y1 - posição inicial y da linha
    * @param double x2 - posição final x da linha
    * @param double y2 - posição final y da linha
    * @return null
    */


    public static NotificationWrapper<LinkedList<Line>> atualizaBoard(User user)
    {
        try{
            NotificationWrapper<LinkedList<Line>> linhas = look_up.getLines(user);
            return linhas;
        }catch(Exception e){
            return null;
        }
    }
    /**
    * @fn public static NotificationWrapper<LinkedList<Line>> atualizaBoard(User user)
    * @brief pega linhas desenhadas no quadro do server
    * @param User user - usuario loggado
    * @return NotificationWrapper<LinkedList<Line>> linhas - lista de linhas presentes no quadro
    */


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
    /**
    * @fn public static void openConnection()
    * @brief gera conexão cliente/servidor
    * @param null
    * @return null
    */
    
    public static void main(String[] args)
    {
        IP = args[0];
        // cria a conexão com o servidor
        openConnection();

        // abre a interface
        new MouseTracker().start();
    }
    /**
    * @fn public static void main(String[] args)
    * @brief main do client - abre a conexão com o servidor e chama a interface MouseTracker
    * @param String[] args - argumentos contendo o IP do servidor
    * @return null
    */
}