/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Rectangle;

/**
 *
 * @author rafael
 */
public class Player                             //classe responsavel por agrupar
	{                                       //as informacoes/atributos dos players
		private Rectangle paddle;
                private int score;

		public Player(Rectangle paddle, int score)
		{
                    this.paddle = paddle;
                    this.score = score;
		}

		public Rectangle getPaddle()
		{
                    return paddle;
		}

		public int getScore()
		{
                    return score;
		}

		public void setScore(int score)
		{
                    this.score = score;
		}
	}
