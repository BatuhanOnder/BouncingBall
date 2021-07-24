package bouncingball;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author batuh
 */

public class Board extends Applet implements Runnable, MouseListener, MouseMotionListener {

    Color color;//Renk Degiskeni Atandi
    Font text;//Yazi icin Font Degiskeni Atandi
    Font text2;//İkincil Yazi İcin Font Degiskeni Atandi
    Thread thread;//Thread Degiskeni Atandi
    Runnable refreshFrame;
    int refreshTime = 50;//Topun Hizlanmasini kontrol etmek icimn gereken ilk hiz degiskeni

    //Oyun devamdegskeni
    int isContuine;//Oyunun Score 

    //Board Degiskenleri
    int BoardX;
    int boardY;
    int oran = 300;

    //Top islemleri
    int BallX;
    int BallY;
    int BallSize = 20;
    int BallSpeed;
    int dx = 11, dy = 7;

    //Score
    int Score = 0;

    @Override
    public void init() {
        isContuine = 1;
        BoardX = 225;
        boardY = 550;

        BallX = 250;
        BallY = 400;

        dy = -dy;

        setSize(600, 600);        //Pencere  boyu atama
        setBackground(Color.BLACK); //Arkaplan rengi atama

        color = new Color(54, 209, 26);
        text = new Font("", Font.BOLD, 24);
        text2 = new Font("Comic Sans MS", Font.BOLD, 24);
        repaint();
    }

    @Override
    public void start() {
        thread = new Thread(this);
        thread.start();
        addMouseListener(this);
        addMouseMotionListener(this);
        refreshFrame = new Runnable() {
            @Override
            public void run() {
                while (isContuine == 1) {
                    try {
                        thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    refreshScreen();
                }

            }
        };
        new Thread(refreshFrame).start();
    }

    int i = 0;

    @Override
    public void paint(Graphics g1) {

        Graphics2D g = (Graphics2D) g1; // graphics degiskenini graphics2d classina cast ettik (Dönüstürdük)

        if (isContuine == 1) {
            g.setColor(color);  // Cizgi rengi atadik
            g.drawLine(0, 50, 600, 50);  //Cizgiyi x0 y0 dan x1 y1 e cektik

            g.setColor(color);   //renk atadik yaziya
            g.setFont(text);    //font atadik yaziya
            g.drawString("Score" + ": ", 240, 35); //Score yazisini x ve y koordinatina yazdirdik
            g.drawString(Score + "", 350, 35);  //Puan yazdirma

            g.setColor(color);
            g.setStroke(new BasicStroke(5));//Hareket Eden Tugla Kalinligi Ayarlandi
            g.drawLine(BoardX, 550, BoardX + oran, 550);//Altta hareket eden  tuglamizi olusturduk

            g1.fillOval(BallX, BallY, BallSize * 2, BallSize * 2);//Sekmesi Gereken Top olusturuldu
        } else if (Score == 1500) {
            g.setFont(text);
            g.setColor(color);
            g.drawString("WİNNER", 250, 250);
        } else {
            g.setFont(text2);
            g.setColor(color);
            g.drawString("GAME OVER", 230, 270);
            g.drawString("Score:" + Score, 230, 300);
        }
        if (Score == 1500) {
            g.setFont(text);
            g.setColor(color);
            g.drawString("WİNNER", 250, 250);
        }
    }

    void refreshScreen() {
        this.repaint();
    }

    @Override
    public void run() {
        while (true) {

            //SOL DUVAR
            if (BallX + BallSize + dx < 0) {
                dx = -dx;
            }

            //SAG DUVAR
            if (BallX + BallSize + dx > bounds().width) {
                dx = -dx;
            }

            //UST DUVAR
            if (BallY - (BallSize - 15) < 50) {
                dy = -dy;
                Score += 100;
                if (Score >= 800) {

                    refreshTime -= 1;

                } else {
                    oran -= 25;
                }
                if (Score > 1800) {
                    oran = 100;
                    BallSize -= 1;
                    refreshTime = 10;

                }
                if (Score > 3000) {
                    BallSize = 10;
                    oran = 100;

                }

            }

            //ALT DUVAR
            if (BallY + BallSize + dy > boardY - 15 && BallX + BallSize + dx > BoardX && BallX + BallSize + dx < BoardX + oran) {
                dy = -dy;
            }

            if (BallY + BallSize + dy > 590) {
                isContuine = 3;
                thread.stop();
            }

            BallX += dx;
            BallY += dy;
            if (Score == 1500) {
                thread.stop();
            }
            try {
                thread.sleep(refreshTime);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() + oran < bounds().width) {
            BoardX = e.getX();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() + oran < bounds().width) {
            BoardX = e.getX();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

}
