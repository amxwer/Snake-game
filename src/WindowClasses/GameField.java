package WindowClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener  {

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleXPosition;
    private int appleYPosition;
    private int[] xPosition = new int[ALL_DOTS];
    private int[] yPosition = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right = true;
    private boolean up;
    private boolean down;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        //method which connect keyboard with game field
        setFocusable(true);

    }
    public void initGame(){
        dots =3;
        for (int i = 0; i <dots ; i++) {
            xPosition[i] =48 - i*DOT_SIZE;
            yPosition[i] =48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }
    public void createApple(){
        appleXPosition = new Random().nextInt(20)*DOT_SIZE;
        appleYPosition = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){

        ImageIcon appleIcon =new ImageIcon("pictures/apple.png");
        apple =appleIcon.getImage();
        ImageIcon dotIcon = new ImageIcon("pictures/dot.png");
        dot =dotIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
            g.drawImage(apple, appleXPosition, appleYPosition, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, xPosition[i], yPosition[i], this);
            }
        }
            else{
                String str = "Game Over";
                String score ="Your score:" + dots;
                g.setColor(Color.WHITE);
                g.setColor(Color.WHITE);
                g.drawString(str,125,SIZE/2);
                g.drawString(score,125, (int) (SIZE/1.7));

            }
        }


    public void move() {
        for (int i = dots; i > 0; i--) {
            xPosition[i] = xPosition[i - 1];
            yPosition[i] = yPosition[i - 1];
        }
        if (left) {
            xPosition[0] -= DOT_SIZE;
        }
        if (right) {
            xPosition[0] += DOT_SIZE;
        }
        if (up) {
            yPosition[0] -= DOT_SIZE;
        }
        if (down) {
            yPosition[0] += DOT_SIZE;
        }
    }
    public void checkApple(){
        if(xPosition[0]==appleXPosition && yPosition[0]==appleYPosition){
            dots++;
            createApple();
        }
    }
    public void checkBorders(){
        for (int i =dots; i >0 ; i--) {
            if(i>4 && xPosition[0]==xPosition[i] && yPosition[0]==yPosition[i]){
                inGame = false;

            }
        }
        if(xPosition[0]>SIZE){inGame =false;}
        if(xPosition[0]<0){inGame =false;}
        if(yPosition[0]>SIZE){inGame =false;}
        if(yPosition[0]<0){inGame =false;}

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkBorders();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key  = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left =true;
                up=false;
                down=false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right =true;
                up=false;
                down=false;
            }
            if(key == KeyEvent.VK_UP && !down){
                right =false;
                up=true;
                left=false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right =false;
                down=true;
                left=false;
            }
        }
    }
}
