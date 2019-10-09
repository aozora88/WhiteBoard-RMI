
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;



public class MouseTracker extends JFrame 
implements MouseListener, MouseMotionListener
{
    JLabel mousePosition;
    public static double[] cord;
    Graphics g;
    Graphics2D g2d;
    int i;
    //Client funcs;

    public void comp()
    {
        double[] resp = Client.send_coord(cord[0], cord[1], cord[2], cord[3]);
        
        g2d.draw(new Line2D.Double(resp[0], resp[1], resp[2], resp[3]));
    }

    public void desenhar(){
        g2d.draw(new Line2D.Double(cord[0], cord[1], cord[2], cord[3]));
        comp();
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
        //mousePosition.setText("Mouse clicado na coordenada : ["+e.getX()+","+e.getY()+"]");
        cord[i] = e.getX();
        i++;
        cord[i] = e.getY();
        if( i == 3 ){
            desenhar();
            i = 0;
        }else{
            i++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //mousePosition.setText("Coordenada atual do mouse : ["+e.getX()+","+e.getY()+"]");
    }
  
    @Override
    public void mouseEntered(MouseEvent e) {
        //mousePosition.setText("Coordenada atual do mouse : ["+e.getX()+","+e.getY()+"]");    
    }
  
    @Override
    public void mouseExited(MouseEvent e) {
        //mousePosition.setText("O Mouse está fora da janela de acesso");
    }
  
    @Override
    public void mousePressed(MouseEvent e) {
        //mousePosition.setText("Mouse pressionado nas coordenadas : ["+e.getX()+","+e.getY()+"]");
        
    }
  
    @Override
    public void mouseDragged(MouseEvent e) {
        //mousePosition.setText("Mouse arrastado nas coordenadas : ["+e.getX()+","+e.getY()+"]");
        
    }
  
    @Override
    public void mouseMoved(MouseEvent e) {
        //mousePosition.setText("Mouse movido para as coordenadas : ["+e.getX()+","+e.getY()+"]");
          
    }

    public void start()
    {
        //ControlImpl funcs = con;
        //funcs = new Client();

        cord = new double[4];
        i = 0;

        mousePosition=new JLabel();
         addMouseListener( this );        // listens for own mouse and
          addMouseMotionListener( this );  // mouse-motion events
          setLayout(null);
          add(mousePosition);
          Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
          size.setSize(500, 500);
          setSize(size );
          setUndecorated(false);
          setVisible( true );
          setDefaultCloseOperation(EXIT_ON_CLOSE);

          g = this.getGraphics();
          g2d = (Graphics2D) g; 
          g2d.setPaint(Color.orange); 
          g2d.setStroke(new BasicStroke (5.0f)); 


    }

}