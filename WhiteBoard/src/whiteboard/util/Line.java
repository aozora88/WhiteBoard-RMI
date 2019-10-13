package whiteboard.util;

import java.io.Serializable;

public class Line implements Serializable{
    
    private static final long serialVersionUID = -5942674186815854884L;
    private double[] point1;
    private double[] point2;
    
    private int[] color;
    
    public Line(double[] p1, double[] p2, int[] color) {
        super();
        this.point1 = new double[2];
        this.point2 = new double[2];
        this.color = new int[3];
        
        this.point1[0] = p1[0];
        this.point1[1] = p1[1];
        this.point2[0] = p2[0];
        this.point2[1] = p2[1];
        
        this.color[0] = color[0];
        this.color[1] = color[1];
        this.color[2] = color[2];
    }
    
    /**
     * @return the point1
     */
    public double[] getPoint1() {
        return point1;
    }
    
    /**
     * @return the point2
     */
    public double[] getPoint2() {
        return point2;
    }
    
    /**
     * @return the color
     */
    public int[] getColor() {
        return color;
    }
    
    /**
     * @param point1 the point1 to set
     */
    public void setPoint1(double[] point1) {
        this.point1 = point1;
    }
    
    /**
     * @param point2 the point2 to set
     */
    public void setPoint2(double[] point2) {
        this.point2 = point2;
    }
    
    /**
     * @param color the color to set
     */
    public void setColor(int[] color) {
        this.color = color;
    }
}