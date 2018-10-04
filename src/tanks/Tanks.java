/*
    Set 3 programming project
    5-18-16
    *****************Tanks***************
    https://github.com/jrtechs/Tanks/wiki
*/

package tanks;

import java.awt.Color;
import java.awt.Font;
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
    private int fheight=650;
    private int fwidth=900;
    
    private Timer move;
    private KeyListener key;
    
    //game elements
    private ArrayList<Bullet> bullets;
    private Player p;
    private ArrayList<Enemy> enemy;
    private Wave wave;
    
    //constructor
    public Tanks()
    {
        
    
        frame=new JFrame("Tanks project");
        frame.setSize(fwidth,fheight + 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        newGame();
        
        key=new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
            }
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    if(wave.gameMode == 1)
                    {
                        wave.setGameMode(3);
                    }
                    else if (wave.gameMode == 3)
                    {
                        wave.setGameMode(1);
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if(wave.gameMode == 0 || wave.gameMode == 2)
                    {
                        newGame();
                        wave.gameMode = 1;
                    }
                }
                p.updateDir(e, true);
            }
            @Override
            public void keyReleased(KeyEvent e) 
            {
                p.updateDir(e, false);
            } 
        };
        frame.addKeyListener(key);
    
        panel = new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                //Paint Wave
                wave.draw(g);
                for(Bullet b: bullets)
                {
                    b.draw(g);
                }
                
                for(Enemy en: enemy)
                {
                    en.draw(g);
                }
                g.setColor(Color.BLACK);
                g.fillRect(0, fheight, fwidth, 150);    
                p.draw(g);
                
                //Game Info Section
                //Wave
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial" , 1, 25));
                g.drawString("Wave: " + wave.waveNum + "     Ammo: "+ p.ammo + "     Kills: " + wave.kills +"     Health: " + p.health + "     Time: " + wave.timeCount, 50, fheight + 50);
           
                //Pausing the game
                if(wave.gameMode == 3)
                {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial" , 1, 40));
                    g.drawString("PAUSED - PRESS ESC TO CONTINUE" , 75, fheight/2);
                }
                
                if(wave.gameMode == 2)
                {
                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial" , 1, 40));
                    g.drawString("You Died" , fwidth/2 - 100, fheight/2);
                    
                    
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial" , 1, 25));
                    g.drawString("Press enter to play", 50, fheight + 100);
                }
                else if(wave.gameMode ==0)
                {
                    g.drawString("Press enter to play", 50, fheight + 100);
                }
                
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
                if(wave.gameMode == 1)
                {
                    p.move();
                    for(int i = 0; i < bullets.size(); i ++)
                    {
                        try
                        {
                             bullets.get(i).move();
                        }
                        catch(Exception ex)
                        {

                        }

                    }

                    for(int i = 0; i < enemy.size(); i++)
                    {
                        try
                        {
                            enemy.get(i).move();
                        }
                        catch(Exception ex)
                        {

                        }
                    }
                }
                wave.waveCheck();
                panel.repaint();
            }
        };
        
        move = new Timer(30, tic);        //Timer that moves things... Moves the player and enemies?
        move.start();
    }
    
    void newGame()
    {
        bullets = new ArrayList<Bullet>();
        enemy = new ArrayList<Enemy>();
        p = new Player();
        wave = new Wave();
    }
    
    public static void main(String[] arguments)
    {
        Tanks game = new Tanks();
        
    }
    
    
    /*
        Sub-classes that require the fields in tanks
    */
    
    
    /**
     * the player class which extends the living class
     * a player has a turret and an int kills
     * a player has a specialized move method
     * a player has a update direction method which takes in a keyevent
    */
    private class Player extends Living
    {
        private Turret t;
        private boolean up,down,left,right, rleft, rRight;
        private int ammo=10;
        private boolean shoot;
            ActionListener coolDown = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(shoot==false)
                    {
                        shoot=true;
                    }
                }
                
            };
            Timer time=new Timer(300,coolDown);
        
        
        public Player()
        {
            x = frame.getWidth()/2;
            y = frame.getHeight()/2;
            time.start();
            t=new Turret(this);
            
            speed = 5;
            
            
            health = 100;
            this.imageLocation = "player.png";
            super.loadImage();
            width = 50;
            height = 50;
        }
        
         void move()
        {
            if(rleft)
            {
                t.rotate(-1);
            }
            if(rRight)
            {
                t.rotate(1);
            }
            if(up==true)
            {
                
               super.move(-1);
               t.move(-1);
            }
            if(down==true)
            {
                t.move(1);
                super.move(1);
            }
            if(left==true)
            {
                super.direction -=5;
                t.rotate(-1);
            }
            if(right==true)
            {
                super.direction+=5;
                t.rotate(1);
            }
            if(y<=0)
            { 
               y+=speed;
            }
            else if(x<=0)
            { 
                x+=speed;
            }
            else if(x>=fwidth-width)
            {
                x-=speed;
            }
            else if(y>=fheight-height)
            {
                y-=speed;
            }
            t.x = x;
            t.y = y;

        }
        void updateDir(KeyEvent e, boolean pressed)
        {
            int id=e.getKeyCode();
            if(id== KeyEvent.VK_UP)
            {
                up=pressed;
            }
            else if(id==KeyEvent.VK_DOWN)
            {
                down=pressed;
            }
            else if(id==KeyEvent.VK_LEFT)
            {
                left=pressed;
            }
            else if(id==KeyEvent.VK_RIGHT)
            {
                right=pressed;
            }
            else if(id==KeyEvent.VK_A)
            {
                rleft = pressed;
            }
            else if(id==KeyEvent.VK_D)
            {
                rRight = pressed;
            }
            else if(id==KeyEvent.VK_R && wave.gameMode ==1)
            {
                ammo=10;
            }
            else if(id==KeyEvent.VK_SPACE)
            {
                if(pressed)
                {
                    if (shoot)
                    {
                        shoot();
                        shoot=false;
                    }
                    else
                    {
                      
                    }


                }
            }
            
        }
        void shoot()
        {
                
                
                if(ammo<=0)
                {
                }
                else
                {
                    bullets.add(new Bullet (t));
                    ammo--;
                }
               
        }
       public void draw (Graphics g)
        {
            super.draw(g);
            t.draw(g);
        }
        
    }
    
    
    /**
     * A zombie extends Enemy
     * a zombie has a specialized move method which
     * moves it twards the player
     *
     * the move method checks to see if it collides with the player
     * if so it removes itself from the arraylist in tanks and deducts damage
    */
    private class Zombie extends Enemy
    {
        //constructor instanciated fields
        public Zombie()
        {
            super();
            spawn(fwidth, fheight);
            width = 30;
            height = 30;
            health = 10;
            isAlive=true;
            speed = 3;
            imageLocation = "zombie.png";
            super.loadImage();
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
                if(!p.isAlive)
                {
                    wave.setGameMode(2);
                    wave.kills++;
                }
            }
        }
    }
    
    
    
    
    /**
     * a bullet moves at a specified angle
     * if the bullet collides with a enemy it gives damage
     * if the bullet goes off the screen it removes itself from the arraylist
    */
    private class Bullet extends RotationalElement
    {
        
        public boolean enemyBullet; // whether it is an enemy or player bullet
        
        public Bullet(RotationalElement e)
        {
            width = 25;
            height = 25;
            x = e.x + 12;
            y = e.y + 12;
            direction = e.direction;
            speed = 10;
            imageLocation = "bullet.png";
            super.loadImage();
        }

        //Moving the bullet
        public void move()
        {
            super.move(-1);
            
            //Checks if the bullet goes off screen, if so... it get removed
            if(x < 0 || x > fwidth)
            {
                bullets.remove(this);
            }
            if (y < 0 || y > fheight)
            {
                bullets.remove(this);
            }
            
            //Checks for collision with enemies, enemy takes damage and byllet is removed
            for(int i = 0; i < enemy.size(); i++)
            {
                if(enemyBullet == false)
                {
                    boolean collided = this.checkCollision(enemy.get(i));
                    if(collided)
                    {
                        enemy.get(i).takeDamage();
                        bullets.remove(this);
                        if(!enemy.get(i).isAlive)
                        {
                            enemy.remove(enemy.get(i));
                            wave.kills++;
                        }
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
                    if(!p.isAlive)
                    {
                        wave.setGameMode(2);
                    }
                }
            }
        }
          
    }
    
    /**
     * a turret is drawn ontop of the tank
     * a turret can rotate and shoot bullets,
     * if rotate is called, put in -1 or 1, if -1, it will
     * turn left, 1 will cause
     * it to turn right
    */
    private class Turret extends RotationalElement
    {
        public Turret(RotationalElement e)
        {
            width = 50;
            height = 50;
            x = e.x;
            y = e.y;
            direction = e.direction;
            speed = 10;
            imageLocation = "turret.png";
            super.loadImage();
        }
        public void shoot()
        {
            //bullets.add(new Bullet (this));
        }

        public void rotate(int e)
        {
            super.direction = super.direction + 5*e;
        }
    }
    /*
    
    */
     private class Tank extends Enemy
    {

        Bullet b;
        Turret t;
        Timer tim;
        ActionListener al;
        public Tank()
        {
            super();
            enemy.add(this);
            this.spawn(fwidth, fheight);
            width = 50;
            height = 50;
            health = 20;
            isAlive=true;
            speed = 2;
            imageLocation = "enemyTank.png";
            super.loadImage();
            t = new Turret(this);
            
            t.imageLocation = "enemyTurret.png";
            t.loadImage();
            
            al = new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                   if(getAlive())
                   { 
                       b = new Bullet(t);
                       b.enemyBullet=true;
                       bullets.add(b);
                   }
                  
                }
                
            };
            tim = new Timer(3500,al);
            tim.start();
            
            
        }
        public void draw (Graphics g)
        {
            super.draw(g);
            t.draw(g);
            
        }
        public void move() 
        {
            if(this.distToPlayer(p)>250)
            {
                direction = angleToPlayer(p);
                super.move(-1);
                if(this.checkCollision(p))
                {
                    p.takeDamage();
                    this.takeDamage();
                    if(isAlive==false)
                    {
                        enemy.remove(this);
                    }
                    if(!p.isAlive)
                    {
                        wave.setGameMode(2);
                    }
                }
            }
            t.x=this.x;
            t.y=this.y;
            t.direction=this.angleToPlayer(p);
        }
        
    }

     private class Wave extends DrawableElement
    {
        /**fields time is continous while playing, gameMode(1=playing,
         * 2=dead, 3=paused. spawntime keeps a countdown until next spawn,
         * kills keeps track of kills duh.
         * 4 win
         * 1 alive and playing
         * 0 main
        */
        int timeCount, kills, gameMode, waveNum, spawnedTanks, spawnedZombies;
        Timer spawnTank, spawnZombie, time;
        
        //constuctor
        public Wave()
        {
            timeCount=0;
            kills=0;
            gameMode=0;
            waveNum=1;
            
            imageLocation = "wave.jpg";
            super.loadImage();
            
            //actionlistener calls spawn every 2 seconds
            ActionListener z = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if(gameMode == 1)
                    {
                        spawnZombie();
                    }
                    
                }
                
            };
            
            ActionListener ta = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if(gameMode ==1)
                       spawnTank();
                }
                
            };
            
            //action listener increments time
            spawnTank = new Timer(2000,ta);
            spawnZombie = new Timer(2000, z);
            spawnZombie.start();
            ActionListener t = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if(gameMode==1)
                    {
                        timeCount++;
                    }
                }
                
            };
            time = new Timer(1000,t);
            time.start();
        }
        public void setGameMode(int newGameMode)
        {
            gameMode = newGameMode;
            if(gameMode == 2)
            {
                //Player has died, ending the game
                time.stop();
                //move.stop();
                spawnZombie.stop();
                spawnTank.stop();
            }
            else if(gameMode == 4)
            {
                //Player has won
                time.stop();
                //move.stop();
                spawnZombie.stop();
                spawnTank.stop();
            }
        }
        
        
        //spawn method checks if spawn timer ==0 and if so then spawns an 
        //enemy
        public void spawnTank()
        {
           enemy.add(new Tank());
           spawnedTanks++;
        }
        public void spawnZombie()
        {
            enemy.add(new Zombie());
            spawnedZombies++;
        }
        public void waveCheck()
        {
            if(gameMode == 1)
            {
                switch(waveNum)
                {
                    case 1:
                        if(kills == 15)
                        {
                            changeWave();
                        }
                        break;
                    case 2:
                        if(kills == 20)
                        {
                            changeWave();
                        }
                        break;
                    case 3:
                        if(kills == 26)
                        {
                            spawnTank.start();
                            changeWave();
                        }
                        break;
                    case 4:
                        if(kills == 32)
                        {
                            changeWave();
                        }
                        break;
                    case 5:
                        if(kills == 33)
                        {
                            changeWave();
                        }
                        break;
                    case 6:
                        if(kills == 35)
                        {
                            changeWave();
                        }
                        break;   
                    case 7:
                        if(kills == 37)
                        {
                            changeWave();
                        }
                        break;   
                     case 8:
                        if(kills == 40)
                        {
                            changeWave();
                        }
                        break;   
                    case 9:
                        if(kills == 49)
                        {
                            changeWave();
                        }
                        break;
                    case 10:
                        if(kills == 60)
                        {
                            //Game over
                            
                        }
                        break;
                }
            }
        }
        public void changeWave()
        {
            waveNum++;
            spawnedZombies = 0;
            spawnedTanks = 0;
            kills = 0;
            spawnTank.setDelay(4000);
        }
            
        
        
            
    }
}

