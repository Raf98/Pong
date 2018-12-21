/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javax.swing.JFrame;

/**
 *
 * @author rafael
 */
public class PongMain {
    
    public static void main( String[] args )
	{            
            JFrame win = new JFrame("Pong");                    //adiciona o JPanel do Pong
            win.setSize(1010,735);                              //ao JFrame
            win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            Pong initGame = new Pong();
            win.add( initGame );                                
            win.setVisible(true);                                   
        }
    
}
