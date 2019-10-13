package whiteboard.util;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 2992711897620589615L;
    private int ID;
    private String nickname;
    private double[] color;
    
    public User(int ID, String nickname) {
        super();
        this.ID = ID;
        this.nickname = nickname;
    }

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

    /**
     * @return the iD
     */
    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        // null check
		if (o == null) {
			return false;
		}
 
		// this instance check
		if (this == o) {
			return true;
		}
 
		// instanceof Check
		if (!(o instanceof User)) {
			return false;
        }
        
        User user = (User) o;
        if(ID == user.getID() && nickname.equals(user.getNickname())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) ((nickname.hashCode() / ID) * 53);
    }
}