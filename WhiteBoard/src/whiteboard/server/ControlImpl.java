package whiteboard.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.NotificationWrapper;
import whiteboard.util.User;

public class ControlImpl extends java.rmi.server.UnicastRemoteObject implements Control {
    private static final long serialVersionUID = 1L;

    private int userIDs;
    private HashMap<String, Board> name_board;
    private HashMap<Integer, Board> userid_board;
    private HashMap<Integer, String> bdname_table;

    // Constructor Declaration
    public ControlImpl() throws java.rmi.RemoteException {
        super();
        userIDs = 0;
        name_board = new HashMap<String, Board>();
        userid_board = new HashMap<Integer, Board>();
        bdname_table = new HashMap<>();
    }

    @Override
    public NotificationWrapper<User> createBoard(String boardName, String userName) throws RemoteException {
        // Verifica se existe quadro com nome informado
        if (name_board.containsKey(boardName))
        {
            // Retorna mensagem de erro
            NotificationWrapper<User> notif = new NotificationWrapper<>(false, "Ja existe quadro com o nome informado");
            return notif;
        }
        // Cria quadro e usuario
        Board board = new Board(boardName);
        User user = new User(userIDs, userName);
        // Insere quadro nos HashMaps
        name_board.put(boardName, board);
        userid_board.put(userIDs, board);
        // Insere usuario no quadro
        board.insertUser(user);
        userIDs += 1;
        // Cria objeto de notificação
        NotificationWrapper<User> notif = new NotificationWrapper<>(true, null, user);
        return notif;
    }
    
    @Override
    public NotificationWrapper<User> enterBoard(String boardName, String userName) throws RemoteException {
        Board board;
        if((board = name_board.get(boardName)) == null)
        {
            NotificationWrapper<User> notif = new NotificationWrapper<>(false, "Nao foi encontrado quadro com este nome");
            return notif;
        }
        User user = new User(userIDs, userName);
        if(!board.insertUser(user))
        {
            NotificationWrapper<User> notif = new NotificationWrapper<>(false, "Ja existe usuario com o nome escolhido");
            return notif;
        }
        userid_board.put(userIDs, board);
        userIDs += 1;
        NotificationWrapper<User> notif = new NotificationWrapper<>(true, null, user);
        return notif;
    }

    @Override
    public NotificationWrapper<Void> exitBoard(User user) throws RemoteException {
        Board board = userid_board.get(user.getID());
        if(board == null)
        {
            if(bdname_table.containsKey(user.getID()))
            {
                NotificationWrapper<Void> notif = new NotificationWrapper<Void>(false, bdname_table.get(user.getID()));
                bdname_table.remove(user.getID());
                return notif;
            }
            else
            {
                return new NotificationWrapper<Void>(false, "Usuario nao pertencente a nenhum quadro");
            }
        }
        board.removeUser(user);
        userid_board.remove(user.getID());
        if(board.getUserSize() == 0)
        {
            name_board.remove(board.getName());
        }
        return new NotificationWrapper<Void>(true, null, null);
    }
    
    @Override
    public LinkedList<Board> listBoards() throws RemoteException {
        return new LinkedList<Board>(name_board.values());
    }
    
    @Override
    public NotificationWrapper<Void> createLine(User user, double[] point1, double[] point2) throws RemoteException {
        Board board = userid_board.get(user.getID());
        if(board == null)
        {
            if(bdname_table.containsKey(user.getID()))
            {
                NotificationWrapper<Void> notif = new NotificationWrapper<Void>(false, bdname_table.get(user.getID()));
                bdname_table.remove(user.getID());
                return notif;
            }
            else
            {
                return new NotificationWrapper<Void>(false, "Usuario nao pertencente a nenhum quadro");
            }
        }
        Line line = new Line(point1, point2, user.getColor());
        board.insertLine(user, line);
        return new NotificationWrapper<Void>(true, null, null);
    }
    
    @Override
    public NotificationWrapper<LinkedList<Line>> getLines(User user) throws RemoteException {
        Board board = userid_board.get(user.getID());
        if(board == null)
        {
            if(bdname_table.containsKey(user.getID()))
            {
                NotificationWrapper<LinkedList<Line>> notif = new NotificationWrapper<>(false, bdname_table.get(user.getID()));
                bdname_table.remove(user.getID());
                return notif;
            }
            else
            {
                return new NotificationWrapper<LinkedList<Line>>(false, "Usuario nao pertencente a nenhum quadro");
            }
        }
        return new NotificationWrapper<LinkedList<Line>>(true, null, board.getLines(user));
    }
}