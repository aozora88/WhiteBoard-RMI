
public class ControlImpl extends java.rmi.server.UnicastRemoteObject implements Control
{
    public static double[] receive;
    public static double[] returni;

    // Constructor Declaration 
    public ControlImpl() throws java.rmi.RemoteException 
    {  
        super();
        receive = new double[4];
        returni = new double[4];

        returni[0] = 0;
        returni[1] = 0;
        returni[2] = 0;
        returni[3] = 0;

        receive[0] = 0;
        receive[1] = 0;
        receive[2] = 0;
        receive[3] = 0; 
    } 

    public double[] share_coord(double x1, double y1, double x2, double y2) throws java.rmi.RemoteException
    {
        /* ADICIONAR CONTROLE */

        returni[0] = receive[0];
        returni[1] = receive[1];
        returni[2] = receive[2];
        returni[3] = receive[3];

        receive[0] = x1;
        receive[1] = y1;
        receive[2] = x2;
        receive[3] = y2;

        return returni;
    }
}