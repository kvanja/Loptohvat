package Main;
import java.awt.*;
/**
 * Klasa za supermoći (powerup-ove)
 */
public class SuperMoci {
    private int x,y,dy, type, width, height;
    private  boolean isOnScreen;
    private Color boja;
    public static final int siriKlizac = 4;
    public final static Color sirokiKlizacColor = new Color(42, 45, 105);
    private boolean iskoristen;

    public SuperMoci(int x, int y, int type, int width, int height){
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = width;
        this.height = height;
        this.dy = (int)(Math.random() * 6 +1);
        if(type < 4 ){ type = 4; }
        if(type == siriKlizac){
            boja = sirokiKlizacColor;
        }
        this.iskoristen = false;
    }

    /**
     *iscrtava supermoci
     * @param g  varijabla g
     */
    public void draw(Graphics2D g){
        g.setColor(boja);
        g.fillRect(x,y,width,height);
    }

    /**
     * metoda za update
     */
    public void update(){
        y += dy;
        if(y > IgraMain.HEIGHT){
            isOnScreen = false;
        }
    }

    /**
     * dohvaca x
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * metoda seta x
     * @param x  pozicija x
     */
    public void setX(int x) {
        this.x = x;
    }
    public int getY(){
        return y;
    }

    /**
     * metoda seta y
     * @param y pozicija y
     */
    public void setY(int y) {this.y = y;}

    /**
     * dohvaca getType()
     * @return type
     */
    public int getType(){return type;}

    /**
     * metoda set dy varijablu
     * @param dy smjer y
     */
    public void setDy(int dy) {this.dy = dy;}

    /**
     * Metoda dohvaca dy
     * @return smjer y
     */
    public int getDy(){return dy;}

    /**
     * metoda dohvaca varijablu getIsOnScreen
     * @return isOnScreen je li na ekranu
     */
    public boolean getIsOnScreen(){ return isOnScreen;}

    /**
     * metoda seta varijablu isOnScreen
     * @param onScreen da li je na screenu
     */
    public void setIsOnScreen(boolean onScreen) {this.isOnScreen = onScreen;}

    /**
     * metoda dohvaca varijablu iskoristen
     * @return iskoristen
     */
    public boolean getIskoristen(){
        return iskoristen;
    }

    /**
     * seta varijablu iskoristen
     * @param used je li iskoristen
     */
    public void setIskoristen(boolean used){
        iskoristen = used;
    }


    /**
     * dohvaca objekt
     * @return vraca sve pozicije objekata
     */
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
}
