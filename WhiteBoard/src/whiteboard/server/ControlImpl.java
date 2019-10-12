package whiteboard.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.User;

public class ControlImpl extends java.rmi.server.UnicastRemoteObject implements Control {
    private static final long serialVersionUID = 1L;

    private int userIDs;
    private HashMap<String, Board> name_board;
    private HashMap<Integer, Board> userid_board;

    // Constructor Declaration
    public ControlImpl() throws java.rmi.RemoteException {
        super();
        userIDs = 0;
        name_board = new HashMap<String, Board>();
        userid_board = new HashMap<Integer, Board>();
    }

    @Override
    public User createBoard(String boardName, String userName) throws RemoteException {
        // Cria quadro e usuario
        Board board = new Board(boardName);
        User user = new User(userIDs, userName);
        // Insere
        name_board.put(boardName, board);
        userid_board.put(new Integer(userIDs), board);
        board.insertUser(user);
        userIDs += 1;
        return user;
    }
    
    @Override
    public void enterBoard(String boardName, String userName) throws RemoteException {
        Board board = name_board.get(boardName);
        User user = new User(userIDs, userName);
        userid_board.put(new Integer(userIDs), board);
        board.insertUser(user);
        userIDs += 1;
    }

    @Override
    public void exitBoard(User user) throws RemoteException {
        Board board = userid_board.get(new Integer(user.getID()));
        board.removeUser(user);
        userid_board.remove(new Integer(user.getID()));
    }
    
    @Override
    public LinkedList<Board> listBoards() throws RemoteException {
        return new LinkedList<Board>(name_board.values());
    }
    
    @Override
    public void createLine(User user, double[] point1, double[] point2) throws RemoteException {
        Board board = userid_board.get(new Integer(user.getID()));
        Line line = new Line(point1, point2, user.getColor());
        board.insertLine(user, line);
    }

    @Override
    public LinkedList<Line> getLines(User user) throws RemoteException {
        return userid_board.get(new Integer(user.getID())).getLines(user);
    }
}