package pong;

//import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.JPanel;

/**
 *
 * @author rafael
 */
public class Pong extends JPanel implements KeyListener  
{                  
	private boolean keys[];                         //usado para tratar as keys
        private Player player1, player2;                //de entrada para mover os paddles
        private  Timer t;
        private double controlX;                        //multiplicadores de velocidade
        private double controlY;
        private boolean changeDirection;                //variavel que muda a direcao
        private TimerTask ts;                           //em que a bola se move quando spawna 
        private int intersectsAgain;
        private MoveBall move;
                                                            
	public Pong()
	{
            setBackground(Color.BLACK);
            intersectsAgain = 0;
            controlX = 1.0;                                  
            controlY = 1.0;
            //enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
            //requestFocus();
            player1 = new Player(new Rectangle(50,250,20,200),0);   //inicializa os players
            player2 = new Player(new Rectangle(900,250,20,200),0);  //com seus paddles e 
            changeDirection = true;                                 //e scores
            
            move = new MoveBall(new Ellipse2D.Double(500,350,20,20),
                                new Point(-5,5));
            
            keys = new boolean[4];
            this.addKeyListener(this);
                
            //new Rectangle(50,250,20,200);//x,y,width,height
                
            if(JOptionPane.showConfirmDialog
            (this,"Wanna start to play?","Pong", YES_NO_OPTION, PLAIN_MESSAGE)
                    ==YES_OPTION)
            {
                keys[0] = false;
                keys[1] = false;
                keys[2] = false;
                keys[3] = false;
                
               ts = initializeTimerTask();
               /*
                ts = new TimerTask() 
                {                                       //Thread responsavel por definir
                    @Override                           //o que sera executado no timer
                    public void run() 
                    {  
                        try
                        {
                            doStuff();
                            repaint();
                            parsingKeys();
                            Thread.sleep(5);
                        } catch (InterruptedException ex) 
                        {}
                    }
                };//*/

		t = new Timer(true);
		t.schedule( ts, 9,9);}
            else System.exit(0);
        }

        @Override
	public void paintComponent( Graphics g )    //continuamente desenha na tela
	{                                           //a bola, os paddles e os scores,
                super.paintComponent(g);            //de acordo com seu movimento na tela
		Graphics2D g2 = (Graphics2D)g;
		Integer aux = player1.getScore();
		String scoreShow1 = aux.toString();
		Integer aux2 = player2.getScore();
		String scoreShow2 = aux2.toString();

		g2.setColor(Color.WHITE);
		g2.fill(move.getBall());
		
		g2.setColor(Color.WHITE);
		g2.fill(player1.getPaddle());

		g2.setColor(Color.WHITE);
		g2.fill(player2.getPaddle());

		g2.setFont(new Font("Monospaced",Font.BOLD,36));
		g2.setColor(Color.WHITE);
		g2.drawString(scoreShow1,400,30);
		g2.drawString(scoreShow2,600,30);
	}

	public void parsingKeys()               //trata a entrada do teclado a fim de
	{                                       //continuamente checar se os paddles sao/podem
            if(keys[0] && (player1.getPaddle().y) > 0)//ser movidos
		player1.getPaddle().y -= 10;
            else
            {
                if(keys[1] && (player1.getPaddle().y + player1.getPaddle().height) < 750)
		player1.getPaddle().y += 10;
            }
            if(keys[2] && (player2.getPaddle().y) > 0)
		player2.getPaddle().y -= 10;
            else
            {
                if(keys[3] && (player2.getPaddle().y + player2.getPaddle().height) < 750)
		player2.getPaddle().y += 10;
            }
	}

        @Override
	public void keyPressed(KeyEvent e)      //enquanto as determinadas teclas forem
	{                                       //pressionadas, pode realizar o movimento
		//if ( e.getID() == KeyEvent.KEY_PRESSED )
		//{
            if ( e.getKeyCode() == KeyEvent.VK_W )
            {
		keys[0] = true; 
            }
            
            if ( e.getKeyCode() == KeyEvent.VK_S )
            {
		keys[1] = true;
            }

            if ( e.getKeyCode() == KeyEvent.VK_UP )
            {
		keys[2] = true;
            }

            if ( e.getKeyCode() == KeyEvent.VK_DOWN )
            {
		keys[3] = true;
            }
	}

        @Override
	public void keyReleased(KeyEvent e)         //quando nao forem mais pressionadas,
	{                                           //para o movimento
            if ( e.getKeyCode() == KeyEvent.VK_W )
            {
                keys[0] = false; 
            }
            
            if ( e.getKeyCode() == KeyEvent.VK_S )
            {
		keys[1] = false;
            }

            if ( e.getKeyCode() == KeyEvent.VK_UP )
            {
		keys[2] = false;
            }
            
            if ( e.getKeyCode() == KeyEvent.VK_DOWN )
            {
                keys[3] = false;
            }
	}

        @Override
	public void keyTyped(KeyEvent e)
	{}

	
	public void doStuff()
	{ 
            move.getBall().x += move.getDelta().x * controlX;           //move a bola, aumentadando sua
            move.getBall().y += move.getDelta().y * controlY;           //velocidade a cada colisao

            // and bounce if we hit a wall
            if ( move.getBall().y < 0)                        //se a bola chegou ao topo,inverte seu y
            {
                controlY = controlY + 0.05;
                move.getDelta().y = -(move.getDelta().y);
            }
            
            else if ( move.getBall().y+20 > 735 )                  //se a bola chegou no final da tela, 
            {                                       //inverte seu y
                controlY = controlY + 0.05;
                move.getDelta().y = -(move.getDelta().y);
            }
            
            // check if the ball is hitting the paddle
            else if ( move.getBall().intersects(player1.getPaddle()))  //se colide com o paddle,
            {                                                          //inverte seu x
                if(intersectsAgain<1)
                {
                controlX = controlX + 0.05;
                move.getDelta().x = -(move.getDelta().x);
                intersectsAgain++;
                }
            }

            else if ( move.getBall().intersects(player2.getPaddle()))
            {	
                if(intersectsAgain>=0)
                {
                controlX = controlX + 0.05;
                move.getDelta().x = -(move.getDelta().x);
                intersectsAgain--;
                }
            }
			
            // check for scoring
            else if ( move.getBall().x > player2.getPaddle().x + player2.getPaddle().width )			//player1 pontua
            {
                changeDirection = !changeDirection;     //direcao de movimento inicial
                player1.setScore(player1.getScore()+1); //eh alterada
                if(player1.getScore() == 10)            //chegando a 10, acaba o jogo
                {                                       //e cancela a Thread/TimerTask
                    ts.cancel();                        //responsavel pelo jogo em si
                    JOptionPane.showMessageDialog           
                    (this, "Player 1  wins!", "Game Over", PLAIN_MESSAGE);
                    if(JOptionPane.showConfirmDialog
                    (this,"Wanna play again?","Pong", YES_NO_OPTION, PLAIN_MESSAGE)
                        ==YES_OPTION)                   //checa se quer jogar novamente
                    {   
                        keys[0] = false;
                        keys[1] = false;
                        keys[2] = false;
                        keys[3] = false;
                        ts = initializeTimerTask();     //aloca novamente e inicia a 
                                                        //TimerTask do jogo

                        t.schedule( ts, 9,9);       //passa TimerTask para o Timer
                        player1.setScore(0);
                        player2.setScore(0);        //reseta os scores dos players para 0
                    }
                    else
                        System.exit(0);             //sai do programa se nao quer jogar 
                }                                   //novamente
                move.getBall().x = 500;             //se nao tiver atingido a pontuacao
                move.getBall().y = 350;             //maxima, spawna a bola aproximadamente
                if(changeDirection)                 //no meio da tela
                    move.setDelta(new Point(-5,5));
                else
                    move.setDelta(new Point(5,5));
                controlX = 1.0;                     //reseta sua velocidade
                controlY = 1.0;
                intersectsAgain = 0;
            }

            else if(move.getBall().x < player1.getPaddle().x - player1.getPaddle().width) //player2 pontua
            {
                changeDirection = !changeDirection;     //faz o mesmo tratamento para o
                player2.setScore(player2.getScore()+1); //player 2
                if(player2.getScore() == 10)
                { 
                    ts.cancel();
                    JOptionPane.showMessageDialog
                    (this, "Player 2  wins!", "Game Over", PLAIN_MESSAGE);
                    if(JOptionPane.showConfirmDialog
                    (this,"Wanna play again?","Pong", YES_NO_OPTION, PLAIN_MESSAGE)
                        ==YES_OPTION)
                    {
                        keys[0] = false;
                        keys[1] = false;
                        keys[2] = false;
                        keys[3] = false;
                        ts = initializeTimerTask();
                        t.schedule( ts, 9,9);
                        player1.setScore(0);
                        player2.setScore(0);
                    }
                    else
                        System.exit(0);
                }
                 
                move.getBall().x = 500;
                move.getBall().y = 350;
                if(changeDirection)
                    move.setDelta(new Point(-5,5));
                else
                    move.setDelta(new Point(5,5));
                controlX = 1.0;//-5;
                controlY = 1.0;//5;
                intersectsAgain = 0;
            }	
	}
	
        @Override
	public boolean isFocusable() { return true;}
        
        TimerTask initializeTimerTask()
        {
           return new TimerTask() 
                        {
                            @Override
                            public void run() 
                            {  
                                try
                                {
                                    doStuff();
                                    repaint();
                                    parsingKeys();
                                    Thread.sleep(5);
                                } catch (InterruptedException ex) 
                                {}
                            }
                        };
        }

 
}
