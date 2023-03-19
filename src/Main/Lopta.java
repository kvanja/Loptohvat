package Main;
import java.awt.*;
/**
 * Klasa lopta
 */
public class Lopta {

    private double x, y, dx, dy;
    private int ballSize = 10;
    /**
     * Konstruktor za postavljanje vrijednosti varijabli
     */
    public Lopta(){
        this.x=IgraMain.WIDTH / 2;
        this.y=IgraMain.HEIGHT / 2.3;
        this.dx =1;
        this.dy = 3;
    }

    /**
     * Metoda kreirana radi smislenijeg poziva nije potrebna
      */
    public void update(){
        setPosition();
    }

    /**
     * nacini kretanja lopte  (metoda postavljanja pozicije na ekranu)
     */
    public void setPosition(){
        x += dx;
        y += dy;
        if(x < 0){
            dx = -dx;
        }
        if(y < 0){
            dy = -dy;
        }
        if(x > IgraMain.WIDTH - ballSize *2 -5){
            dx = -dx;
        }
        if(y > IgraMain.HEIGHT - ballSize){
            dy = -dy;
        }
    }

    /**
     * Metoda koja crta loptu
     * @param g iscrtava
     */
    public void draw(Graphics2D g){
        g.setColor(Color.DARK_GRAY);
        g.fillOval((int)x, (int)y, 10, 10);
        g.setStroke(new BasicStroke(4));
        g.drawOval((int)x,(int)y,ballSize, ballSize);
    }
    /**
     *Dohvaca objekt
     * @return vraca rectangle
     */
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, ballSize, ballSize);
    }

    /**
     * Setter metoda za dy varijablu
     * @param DY postavlja poziciju DY
     */
    public void setDY(double DY){
        this.dy = DY;
    }
    /**
     * getter metoda za dohvacanje varijable dy
     * @return DY
     */
    public double getDY(){return dy;}
    /**
     * Metoda za setanje dx varijable van klase
     * @param dx postavlja poziciju dx
     */
    public void setDX(double dx){
        this.dx = dx;
    }

    /**
     * Metoda za dohvacanje dx van klase
     * @return dx
     */
    public double getDx(){
        return dx;
    }

    /**
     * Metdoa za dohvacanje x varijable van klase
     * @return x
     */
    public double getX(){return this.x;}

    /**
     * Metoda provjere da li je loptica ispod klizaca što rezultira izgubljenom igrom
     * @return loser
     */
    public boolean youLose(){
        boolean loser = false;
        if(y >IgraMain.HEIGHT - ballSize * 2){
            loser = true;
        }
        return loser;
    }

    /**
     * Metoda dohvaca varijablu size
     * @return velicinu loptice
     */
    public int getSize(){ return ballSize;}

    /**
     * Metoda seta y varijablu
     * @param y postavlja kordinatu y
     */
    public void setY(double y){
        this.y = y;
    }
}
