package Main;
import java.awt.*;
/**
 * Klasa za klizac
 */
public class Klizac {

    private double x;
    private int width, height, startHeight;
    private long timer;
    private boolean alterWidth;
    public final int yPos = IgraMain.HEIGHT - 100;
    private double targetX;
    /**
     * Konstruktor koji namjesta velicinu klizaca i mece ga u sredinu
     */
    public Klizac(){
        alterWidth = false;
        width = 100;
        height = 20;
        startHeight = height;
        x = IgraMain.WIDTH / 2  - width / 2;
    }

    /**
     * Metoda koja bolje upravlja klizacem
     */
    public void update(){
        if((System.nanoTime() - timer) / 1000 > 4000000 && alterWidth == true){
            width = 100;
            alterWidth = false;
        }
        x +=(targetX - x) * .3;

        int dif = (int)Math.abs(targetX - x) / 5;
        height = startHeight - dif;

        if(height < 2){
            height = 2;
        }
    }

    /**
     * metoda za crtanje klizaca
     * @param g crta sve živo
     */
    public void draw(Graphics2D g){
        int yDraw = yPos + (startHeight - height) / 2;
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)x, yDraw, width, height);
        if(alterWidth == true){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Shrinking in " + (4- (System.nanoTime() - timer)/1000000000), (int)x , yPos + 18 );
        }
    }

    /**
     * Metoda koja postavlja mis na polovicu klizaca, i ne dozvoljava klizacu da izade izvan ekrana
     * @param xPos  trenutna x pozicija misa
     */
    public void mouseMoved(int xPos){
        targetX = xPos - width / 2;
        if(targetX > IgraMain.WIDTH - width ){
            targetX = IgraMain.WIDTH - width;
        }
        if(targetX < 0){
            targetX =0;
        }
    }

    /**
     * Metoda za dohvacanje varijable width
     * @return width
     */
    public int getWidth(){
        return width;
    }

    /**
     * metoda za dohvacanje oblika
     * @return vraca rectangle sa određenim parametrima pozicija
     */
    public Rectangle getRect(){
        return new Rectangle((int) x, yPos, width, height);
    }

    /**
     * postavlja varijablu setWidth
     * @param newWidth nova velicin
     */
    public void setWidth(int newWidth){
        alterWidth = true;
        width = newWidth;
        setTimer();
    }

    /**
     * postavlja timer
     */
    public void setTimer(){
        timer = System.nanoTime();
    }
}
