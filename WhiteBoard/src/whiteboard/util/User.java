package whiteboard.util;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 2992711897620589615L;
    private int ID;
    private String nickname;
    private double[] color;

    /**
     * @return the color
     */
    public double[] getColor() {
        return color;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    public User(int ID, String nickname) {
        super();
        this.ID = ID;
        this.nickname = nickname;
    }

    /**
     * @return the iD
     */
    public int getID() {
        return ID;
    }
}