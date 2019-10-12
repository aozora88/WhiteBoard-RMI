import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Line2D;

public class MouseTracker extends JFrame 
implements MouseListener, MouseMotionListener
{
    public static JLabel mousePosition;
    public static double[] cord;
    public static Graphics g;
    public static Graphics2D g2d;
    public static JFrame frame;
    int i;

    /**
     * Computa Coordenadas do desenho
     */
    public void comp()
    {
        double[] resp = Client.send_coord(cord[0], cord[1], cord[2], cord[3]);
        
        g2d.draw(new Line2D.Double(resp[0], resp[1], resp[2], resp[3]));
    }
    /**
    * @fn public void comp()
    * @brief envia o desenho ao servidor, recebe as atualizações do quadro e reproduz
    * @param null
    * @return null
    */

    /**
     * Desenhar function
     */
    public void desenhar(){
        g2d.draw(new Line2D.Double(cord[0], cord[1], cord[2], cord[3]));
        comp();
    }
    /**
    * @fn public void desenhar()
    * @brief desenha uma linha entre as coordenadas passadas e chama a função comp
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
     * habilita janela de desenho
     */
    public void open_draw()
    {
        frame.add(mousePosition);
        frame.addMouseListener(this);        // listens for own mouse and
        frame.addMouseMotionListener(this);  // mouse-motion events

        g = frame.getGraphics();
        g2d = (Graphics2D) g; 
        g2d.setPaint(Color.orange); 
        g2d.setStroke(new BasicStroke (5.0f)); 
    }
    /**
    * @fn void open_draw()
    * @brief habilita janela de desenho
    * @param null
    * @return null
    */

    /**
     * DISCUSS
     */
    public void sairQuadro_form()
    {
        JFrame f= new JFrame("Form sair quadro");  
        JTextField tf1=new JTextField("nome do quadro");  
        tf1.setBounds(80,50,300,20);
        JTextField tf2=new JTextField("nome de usuario");  
        tf2.setBounds(80,80,300,20);  
        JButton b1=new JButton("Submit");  
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resp = Client.call_sairQuadro(tf1.getText(), tf2.getText());
                JOptionPane.showMessageDialog(null, resp,"Message", JOptionPane.INFORMATION_MESSAGE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        b1.setBounds(180,200,100,30);      
        f.add(tf1);f.add(b1);f.add(tf2);
        f.setSize(500,500);  
        f.setLayout(null);  
        f.setVisible(true);  
    }

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
    public void entrarQuadro_form()
    {

        JFrame f= new JFrame("Form entrar quadro");  
        JTextField tf1=new JTextField("nome do quadro");  
        tf1.setBounds(80,50,300,20);
        JTextField tf2=new JTextField("nome de usuario");  
        tf2.setBounds(80,80,300,20);  
        JButton b1=new JButton("Submit");  
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resp = Client.call_entrarQuadro(tf1.getText(), tf2.getText());
                JOptionPane.showMessageDialog(null, resp,"Message", JOptionPane.INFORMATION_MESSAGE);
                open_draw();
            }
        });
        b1.setBounds(180,200,100,30);      
        f.add(tf1);f.add(b1);f.add(tf2);
        f.setSize(500,500);  
        f.setLayout(null);  
        f.setVisible(true);  
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
    public void criarQuadro_form()
    {
        JFrame f= new JFrame("Form criar quadro");  
        JTextField tf1=new JTextField("nome do quadro");  
        tf1.setBounds(80,50,300,20);
        JTextField tf2=new JTextField("nome de usuario");  
        tf2.setBounds(80,80,300,20);  
        JButton b1=new JButton("Submit");  
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String resp = Client.call_criarQuadro(tf1.getText(), tf2.getText());
                JOptionPane.showMessageDialog(null, resp,"Message", JOptionPane.INFORMATION_MESSAGE);
                open_draw();
            }
        });
        b1.setBounds(180,200,100,30);      
        f.add(tf1);f.add(b1);f.add(tf2);
        f.setSize(500,500);  
        f.setLayout(null);  
        f.setVisible(true);  
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

        frame = new JFrame("Project Whiteboard");
        JToolBar toolbar = new JToolBar();
        mousePosition=new JLabel();    

        JButton btm_criar = new JButton("Criar Quadro");
        JButton btm_entrar = new JButton("Entrar Quadro");
        btm_criar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btm_criar.setEnabled(false);
                btm_entrar.setEnabled(false);
                criarQuadro_form();
            }
        });
        toolbar.addSeparator();

        
        btm_entrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btm_criar.setEnabled(false);
                btm_entrar.setEnabled(false);
                entrarQuadro_form();
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