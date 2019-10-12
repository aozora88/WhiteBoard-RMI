package whiteboard.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

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

    public void insertUser(User user) {
        userList.add(user);
        user_line.put(user, new LinkedList<Line>());
    }

    public void removeUser(User user) {
        if(!userList.contains(user))
            return;
        userList.remove(user);
        user_line.remove(user);
    }
    
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
        
    public LinkedList<Line> getLines(User user) {
        if(!userList.contains(user))
            return null;
        return user_line.get(user);
    }
}