// Creating an Interface 
public interface Control extends java.rmi.Remote { 
  
    // Declaring the method 
    public double[] share_coord(double x1, double y1, double x2, double y2) throws java.rmi.RemoteException;

} 