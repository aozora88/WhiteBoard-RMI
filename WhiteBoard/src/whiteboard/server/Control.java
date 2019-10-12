package whiteboard.server;

import java.rmi.RemoteException;
import java.util.LinkedList;

import whiteboard.util.Board;
import whiteboard.util.Line;
import whiteboard.util.User;

// Creating an Interface 
public interface Control extends java.rmi.Remote { 
  
    // Declaring the method 
    public User createBoard(String boardName, String userName) throws RemoteException;
    public void enterBoard(String boardName, String userName) throws RemoteException;
    public void exitBoard(User userName) throws RemoteException;
    public LinkedList<Board> listBoards() throws RemoteException;

    public void createLine(User user, double[] point1, double[] point2) throws RemoteException;
    public LinkedList<Line> getLines(User user) throws RemoteException;
} 