/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author rafael
 */
public class MoveBall {
    
    private Ellipse2D.Double ball;
    private Point delta;

    public MoveBall(Ellipse2D.Double ball, Point delta) 
    {
        this.ball = ball;
        this.delta = delta;
    }

    public Ellipse2D.Double getBall() 
    {
        return ball;
    }

    public void setBall(Ellipse2D.Double ball) 
    {
        this.ball = ball;
    }

    public Point getDelta() 
    {
        return delta;
    }

    public void setDelta(Point delta) 
    {
        this.delta = delta;
    }
    
    
}
