package whiteboard.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @class Board
 * @brief Classe de quadro, armazena usuários e linhas a receber pooling.
 */
public class Board implements Serializable{

    private static final long serialVersionUID = -8603786601852999452L;
    private String name;
    private LinkedList<User> userList;
    private HashMap<User, LinkedList<Line>> user_line;
    
    public Board(String name) {
        super();
        this.name = name;
        userList = new LinkedList<User>();
        user_line = new HashMap<User, LinkedList<Line>>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userList
     */
    public LinkedList<User> getUserList() {
        return userList;
    }

    /**
     * @brief Retorna true/false se há um dado cliente num quadro.
     * @param user
     * @return boolean
     */
    public boolean insertUser(User user) {
        for(User ituser : userList) {
            if(ituser.equals(user) || ituser.getNickname().equals(user.getNickname())) {
                return false;
            }
        }
        userList.add(user);
        user_line.put(user, new LinkedList<Line>());
        return true;
    }

    /**
     * @brief Remove um cliente de um quadro.
     * @param user
     */
    public void removeUser(User user) {
        if(!userList.contains(user))
            return;
        userList.remove(user);
        user_line.remove(user);
    }
    
    /**
     * @brief Insere uma linha num quadro, ou seja, toda lista de linhas para pooling menos do cliente insersor, receberão a linha.
     * @param user
     * @param line
     */
    public void insertLine(User user, Line line) {
        if(!userList.contains(user))
            return;
        LinkedList<User> users = new LinkedList<User>(userList);
        users.remove(user);
        ListIterator<User> it = users.listIterator();
        while(it.hasNext()) {
            User itUser = it.next();
            user_line.get(itUser).add(line);
        }
    }
     
    /**
     * @brief Da pooling nas linhas de um dado cliente.
     * @param user
     * @return LinkedList<Line> lista das linhas do pooling
     */
    public LinkedList<Line> getLines(User user) {
        if(!userList.contains(user))
            return null;
        LinkedList<Line> lines = new LinkedList<>(user_line.get(user));
        user_line.get(user).clear();
        return lines;
    }

    /**
     * @Retorna quantidade de clientes num quadro
     * @return int do numero de clientes no quadro
     */
    public int getUserSize() {
        return userList.size();
    }
}