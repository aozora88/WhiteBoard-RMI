package whiteboard.util;
import java.awt.*;
import javax.swing.*;
import whiteboard.client.Client;
import whiteboard.util.NotificationWrapper;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import whiteboard.util.Board;
import whiteboard.util.User;
import whiteboard.util.Line;
import java.time.*; 
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

/**
 * @file  MouseTracker.java
 * @brief Arquivo com as funções relacionadas a interface
*/

public class MouseTracker extends JFrame 
implements MouseListener, MouseMotionListener
{
    /*******************************************************************
*   GLOBAL VARIABLES
*******************************************************************/
    private static final long serialVersionUID = 1L;
    public static JLabel mousePosition;
    public static double[] cord;
    public static Graphics g;
    public static Graphics2D g2d;
    public static JFrame frame;
    public static User userLogged;
    boolean status;
    int i;

/*******************************************************************
*   IMPLEMENTATION
*******************************************************************/

    /**
     * Desenhar function
     */
    public void desenhar(){
        int[] cores = userLogged.getColor();
        g2d.setColor(new Color( cores[0], cores[1], cores[2]));
        g2d.draw(new Line2D.Double(cord[0], cord[1], cord[2], cord[3]));
        Client.send_coord(userLogged, cord[0], cord[1], cord[2], cord[3]);
    }
    /**
    * @fn public void desenhar()
    * @brief desenha uma linha entre as coordenadas passadas e envia ao servidor
    * @param null
    * @return null
    */
   
    /**
     * get event mouse clicked
     */
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
    /**
    * @fn public void mouseClicked(MouseEvent e)
    * @brief chamada a cada click de mouse na janela, pega as coordenas do click, armazena, e quando o buffer estiver cheio chama o desenhar
    * @param MouseEvent e - evento do mouse
    * @return null
    */

    /**
     * A seguir a declaração de outros eventos pegos do mouse mas que não são utilizados nessa implementação
     */
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

    /**
     * Thread atualiza quadro
     */
    private static Runnable t1 = new Runnable() {
        public void run() {
            try{
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    NotificationWrapper<LinkedList<Line>> lines =  Client.atualizaBoard(userLogged);
                    if(lines.isResult()){
                        for (Line infos : lines.getData()) {
                            double[] points1 = infos.getPoint1();
                            double[] points2 = infos.getPoint2();
                            int[] cores = infos.getColor();
                            g2d.setColor(new Color( cores[0], cores[1], cores[2]));
                            g2d.draw(new Line2D.Double(points1[0], points1[1], points2[0], points2[1]));
                        }
                    }else{
                        String msg = lines.getMessage();
                        if((msg.charAt(0) >= '0') && (msg.charAt(0) <= '9')){
                            Client.changeIP(msg);
                            run();
                        }
                    }
                }
                }, 1, 1);
            } catch (Exception e){
                System.out.println("exception thread");
            }
 
        }
    };
    /**
    * @fn public void mouseClicked(MouseEvent e)
    * @brief chamada a cada click de mouse na janela, pega as coordenas do click, armazena, e quando o buffer estiver cheio chama o desenhar
    * @param MouseEvent e - evento do mouse
    * @return null
    */

    /**
     * habilita janela de desenho
     */
    public void open_draw()
    {
        frame.add(mousePosition);
        frame.addMouseListener(this);        // listens for own mouse and
        frame.addMouseMotionListener(this);  // mouse-motion events

        g = frame.getGraphics();
        g2d = (Graphics2D) g; 
        g2d.setStroke(new BasicStroke (5.0f)); 

        new Thread(t1).start();
    }
    /**
    * @fn void open_draw()
    * @brief habilita janela de desenho
    * @param null
    * @return null
    */

    /**
     * função call sair quadro
     */
    public void sairQuadro_form()
    {
        NotificationWrapper<Void> resp = Client.call_sairQuadro(userLogged);
                JOptionPane.showMessageDialog(null, resp.getMessage(),"Message", JOptionPane.INFORMATION_MESSAGE);
                if(resp.isResult()){
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
    }
    /**
    * @fn public void sairQuadro_form()
    * @brief sai do quadro e fecha janela
    * @param null
    * @return null
    */

    /**
     * Trigger da função listar quadros ativos
     */
    public void listarQuadro()
    {
       
        String resp = Client.call_listarQuadro();
        JOptionPane.showMessageDialog(null, resp,"Lista de quadros ativos", JOptionPane.INFORMATION_MESSAGE);

    }
    /**
    * @fn public void listarQuadro()
    * @brief retorna uma lista de quadros validos no server
    * @param null
    * @return null
    */

    /**
     * Formulario de signin de novo usuario no quadro
     */
    public boolean entrarQuadro_form()
    {
        JFrame f= new JFrame("Form entrar quadro");  
        JTextField tf1=new JTextField("nome do quadro");  
        tf1.setBounds(80,50,300,20);
        JTextField tf2=new JTextField("nome de usuario");  
        tf2.setBounds(80,80,300,20);  
        JButton b1=new JButton("Submit");  
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NotificationWrapper<User> resp = Client.call_entrarQuadro(tf1.getText(), tf2.getText());
                JOptionPane.showMessageDialog(null, resp.getMessage(),"Message", JOptionPane.INFORMATION_MESSAGE);
                if(resp.isResult()){
                    open_draw();
                    userLogged = resp.getData();
                    Random gerador = new Random();
                    userLogged.setColor(gerador.nextInt(256), gerador.nextInt(256), gerador.nextInt(256));
                }
                status = resp.isResult();
            }
        });
        b1.setBounds(180,200,100,30);      
        f.add(tf1);f.add(b1);f.add(tf2);
        f.setSize(500,500);  
        f.setLayout(null);  
        f.setVisible(true); 
        return status; 
    }
    /**
    * @fn public void entrarQuadro_form()
    * @brief inicia o cadastro um novo usuario no quadro
    * @param null
    * @return null
    */

    /**
     * Formulario de criação de um novo quadro
     */
    public boolean criarQuadro_form()
    {
        JFrame f= new JFrame("Form criar quadro");  
        JTextField tf1=new JTextField("nome do quadro");  
        tf1.setBounds(80,50,300,20);
        JTextField tf2=new JTextField("nome de usuario");  
        tf2.setBounds(80,80,300,20);  
        JButton b1=new JButton("Submit");  
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NotificationWrapper<User> resp = Client.call_criarQuadro(tf1.getText(), tf2.getText());
                JOptionPane.showMessageDialog(null, resp.getMessage(),"Message", JOptionPane.INFORMATION_MESSAGE);
                if(resp.isResult()){
                    open_draw();
                    userLogged = resp.getData();
                    Random gerador = new Random();
                    userLogged.setColor(gerador.nextInt(256), gerador.nextInt(256), gerador.nextInt(256));
                }
                status = resp.isResult();
            }
        });
        b1.setBounds(180,200,100,30);      
        f.add(tf1);f.add(b1);f.add(tf2);
        f.setSize(500,500);  
        f.setLayout(null);  
        f.setVisible(true);  
        return status;
    }
    /**
    * @fn public void criarQuadro_form()
    * @brief pega os dados e inicia o processo de criação de um novo quadro
    * @param null
    * @return null
    */

    /**
     * Função start da classe
     */
    public void start()
    {
        cord = new double[4];
        i = 0;
        status = false;

        frame = new JFrame("Project Whiteboard");
        JToolBar toolbar = new JToolBar();
        mousePosition=new JLabel();    

        JButton btm_criar = new JButton("Criar Quadro");
        JButton btm_entrar = new JButton("Entrar Quadro");
        btm_criar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(criarQuadro_form()){
                    btm_criar.setEnabled(false);
                    btm_entrar.setEnabled(false);
                }
            }
        });
        toolbar.addSeparator();

        
        btm_entrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(entrarQuadro_form()){
                    btm_criar.setEnabled(false);
                    btm_entrar.setEnabled(false);
                }
            }
        });

        JButton btm_listarQ = new JButton("Listar Quadros Ativos");
        btm_listarQ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarQuadro();
            }
        });
        JButton btm_sair = new JButton("Sair Quadro");
        btm_sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sairQuadro_form();
            }
        });

        toolbar.add(btm_criar);
        toolbar.add(btm_entrar);
        toolbar.add(btm_listarQ);
        toolbar.add(btm_sair);

        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
         
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        size.setSize(500, 500);
        frame.setSize(size );
        frame.setUndecorated(false);
        frame.setVisible( true );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    /**
    * @fn public void start()
    * @brief cria a tela inicial
    * @param null
    * @return null
    */

}