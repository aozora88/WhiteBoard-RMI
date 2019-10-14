package whiteboard.server;

import java.rmi.RemoteException;
import java.util.LinkedList;

import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.NotificationWrapper;
import whiteboard.util.User;

// Creating an Interface 
/**
 * @class Control
 * @brief Interface das funções remotas.
 */
public interface Control extends java.rmi.Remote { 
  
    // Declaring the method 
    /**
     * @param boardName nome do quadro
     * @param userName nome do cliente
     * @return NotificationWrapper<User>
     * @throws RemoteException
     * @brief Cria um quadro e insere o cliente nele
     */
    public NotificationWrapper<User> createBoard(String boardName, String userName) throws RemoteException;
    /**
     * @param boardName nome do quadro
     * @param userName nome do cliente
     * @return NotificationWrapper<User>
     * @throws RemoteException
     * @brief Faz um cliente entrar num quadro com o nome fornecido.
     */
    public NotificationWrapper<User> enterBoard(String boardName, String userName) throws RemoteException;
    /**
     * @param user Cliente que sai do quadro
     * @return NotificationWrapper<Void>
     * @throws RemoteException
     * @brief Retira um cliente de seu quadro.
     */
    public NotificationWrapper<Void> exitBoard(User user) throws RemoteException;
    /**
     * @return LinkedList<Board> dos quadros presentes no servidor
     * @throws RemoteException
     * @brief Retorna uma lista de todos os quadros do servidor
     */
    public LinkedList<Board> listBoards() throws RemoteException;

    /**
     * @param user cliente que deseja enviar a linha
     * @param point1 coordenada inicial da linha
     * @param point2 coordenada final da linha
     * @return NotificationWrapper<Void>
     * @throws RemoteException
     * Cria uma linha e coloca na lista de pooling de todos os clientes menos de quem enviou
     */
    public NotificationWrapper<Void> createLine(User user, double[] point1, double[] point2) throws RemoteException;
    /**
     * @param user cliente
     * @return NotificationWrapper<LinkedList<Line>>
     * @throws RemoteException
     * Envia todas as linhas a espera do pooling do cliente fornecido.
     */
    public NotificationWrapper<LinkedList<Line>> getLines(User user) throws RemoteException;

    /**
     * @param boardName nome do quadro
     * @return boolean se um dado servidor contem um dado quadro
     * @throws RemoteException
     * Verifica se um dado quadro esta contido no servidor.
     */
    public boolean containsBoard(String boardName) throws RemoteException;
    /**
     * @param boardName nome do quadro
     * @param destIP IP do novo servidor do quadro
     * @return Board do quadro que será transferido
     * @throws RemoteException
     * Todo processo de transferencia de um cliente para o próximo servidor já é preparado aqui.
     */
    public Board transferBoard(String boardName, String destIP) throws RemoteException;
    /**
     * 
     * @param board quadro recebido
     * @throws RemoteException
     * Recebe servidor e seus usuários de algum outro servidor desconhecido.
     */
    public void receiveBoard(Board board) throws RemoteException;
} 