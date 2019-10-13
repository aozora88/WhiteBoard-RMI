package whiteboard.server;

import java.rmi.RemoteException;
import java.util.LinkedList;

import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.NotificationWrapper;
import whiteboard.util.User;

// Creating an Interface 
public interface Control extends java.rmi.Remote { 
  
    // Declaring the method 
    public NotificationWrapper<User> createBoard(String boardName, String userName) throws RemoteException;
    public NotificationWrapper<User> enterBoard(String boardName, String userName) throws RemoteException;
    public NotificationWrapper<Void> exitBoard(User userName) throws RemoteException;
    public LinkedList<Board> listBoards() throws RemoteException;

    public NotificationWrapper<Void> createLine(User user, double[] point1, double[] point2) throws RemoteException;
    public NotificationWrapper<LinkedList<Line>> getLines(User user) throws RemoteException;
} 