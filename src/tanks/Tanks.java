/*
    Set 3 programming project
    5-18-16
    *****************Tanks***************
    https://github.com/jrtechs/Tanks/wiki
*/

package tanks;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tanks 
{
    //fields
    private JFrame frame;
    private JPanel panel;
    
    //game elements
    private ArrayList<Bullet> bullets;
    private Player p;
    private ArrayList<Enemy> enemy;
     Timer move;
    
    //constructor
    public Tanks()
    {
           
    
        frame=new JFrame("Tanks project");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KeyListener key=new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
            }
            @Override
            public void keyPressed(KeyEvent e)
            {
                p.updateDir(e, true);
                panel.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                p.updateDir(e, false);
                panel.repaint();
            } 
        };
        frame.addKeyListener(key);
    
        System.out.println("Mrs. Shaw test");
        panel = new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                //Paint Wave
                //wave.draw(g);
                for(Bullet b: bullets)
                {
                    b.draw(g);
                }
                
                for(Enemy en: enemy)
                {
                    en.draw(g);
                }
                
                p.draw(g);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
        
        //Timer
        ActionListener tic = new ActionListener()
        {
            //Override
            public void actionPerformed(ActionEvent e)
            {
                p.move();
                for(Bullet b: bullets)
                {
                    b.move();
                }
                
                for(Enemy en: enemy)
                {
                    en.move();
                }
                panel.repaint();
            }
        };
        
        move = new Timer(30, tic);        //Timer that moves things... Moves the player and enemies?
        move.start();
    }
    
    
    
    /*
        Sub-classes that require the fields in tanks
    */
    
    
    /*
         the player class which extends the living class
         a player has a turret and an int kills
         a player has a specialized move method
         a player has a update direction method which takes in a keyevent
    */
    private class Player extends Living
    {
        Turret t;
        boolean up,down,left,right;
        
        void move()
        {
            if(up==true)
            {
                super.move(-1);
            }
            else if(down==true)
            {
                super.move(1);
            }
            else if(left==true)
            {
                super.direction +=5;
            }
            else if(right==true)
            {
                super.direction-=5;
            }
        }
        void updateDir(KeyEvent e, boolean pressed)
        {
            int id=e.getKeyCode();
            if(id== KeyEvent.VK_UP)
            {
                up=pressed;
                move();
            }
            else if(id==KeyEvent.VK_DOWN)
            {
                down=pressed;
                move();
            }
            else if(id==KeyEvent.VK_LEFT)
            {
                left=pressed;
                move();
            }
            else if(id==KeyEvent.VK_RIGHT)
            {
                right=pressed;
                move();
            }
            else if(id==KeyEvent.VK_SPACE)
            {
                shoot();
            }
            
        }
        void shoot()
        {
            bullets.add(new Bullet (this));
        }
        
    }
    
    
    /*
        A zombie extends Enemy
        a zombie has a specialized move method which 
        moves it twards the player
        
        the move method checks to see if it collides with the player
        if so it removes itself from the arraylist in tanks and deducts damage
    */
    private class Zombie extends Enemy
    {
        //constructor instanciated fields
        public Zombie()
        {
            super();
            this.spawn(frame);
            width = 30;
            height = 30;
            this.setShape();
            health = 10;
            isAlive=true;
        }
        //uses super to move player if collision then removes zombie and player
        //takes damage
        public void move()
        {
            direction = angleToPlayer(p);
            super.move(-1);
            if(this.checkCollision(p))
            {
                enemy.remove(this);
                p.takeDamage();
            }
        }
    }
    
    
    
    
    /*
        a bullet moves at a specified angle
        if the bullet collides with a enemy it gives damage
        if the bullet goes off the screen it removes itself from the arraylist
    */
    private class Bullet extends RotationalElement
    {
        
        public boolean enemyBullet; // whether it is an enemy or player bullet
        
        public Bullet(RotationalElement e)
        {
            width = 25;
            height = 25;
            x = e.x;
            y = e.y;
            direction = e.direction;
            speed = 10;
        }

        //Moving the bullet
        public void move()
        {
            super.move(-1);
            
            //Checks if the bullet goes off screen, if so... it get removed
            if(x < 0 || x > frame.getHeight())
            {
                bullets.remove(this);
            }
            if (y < 0 || y > frame.getWidth())
            {
                bullets.remove(this);
            }
            
            //Checks for collision with enemies, enemy takes damage and byllet is removed
            for(int i = 0; i < enemy.size(); i++)
            {
                if(enemyBullet == false)
                {
                    boolean collided = this.checkCollision(bullets.get(i));
                    if(collided)
                    {
                        enemy.get(i).takeDamage();
                        bullets.remove(i);
                    }
                }
            }
            
            //Checking enemy bullet collision with the player, player takes damage and bullet is removed
            if(enemyBullet)
            {
                boolean collided = this.checkCollision(p);
                if(collided)
                {
                    p.takeDamage();
                    bullets.remove(this);
                }
            }
        }
          
    }
    
    /*
        a turret is drawn ontop of the tank
        a turret can rotate and shoot bullets
    */
    private class Turret extends RotationalElement
    {
        
    }
    
    /*
    
    */
    private class Tank extends Enemy
    {

        @Override
        public void move() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

     private class Wave extends DrawableElement
    {
        /*fields time is continous while playing, gameMode(1=playing, 
        2=paused, 3=menu. spawntime keeps a countdown until next spawn,
        kills keeps track of kills duh.
        */
        int time, kills, gameMode, waveNum;
        Timer spawn;
        
        //constuctor
        public Wave()
        {
            time=0;
            kills=0;
            gameMode=1;
            waveNum=1;
            
            ActionListener s = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    spawn();
                }
                
            };
            spawn = new Timer(2000,s);
            spawn.start();
            
        }
        
        
        //spawn method checks if spawn timer ==0 and if so then spawns an 
        //enemy
        public void spawn()
        {

                Enemy temp = new Enemy();
                temp.spawn(frame);
                enemy.add(temp);
            
        }
        
        
        //timer
    }
}
