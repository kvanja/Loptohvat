package Main;
import java.awt.*;
/**
 * Komadici kocaka
 */
public class Komadici {
    private int x, y;
    private double dx, dy, gravitacija;
    private Color color;
    private Mapa theMapa;
    private int size;

    /**
     * Konstruktor komadica kockica
     * @param komadiciX komadiciaX
     * @param komadiciY komadiciY
     * @param theMapa mapa komadica
     */
    public Komadici(int komadiciX, int komadiciY, Mapa theMapa){
        this.theMapa = theMapa;
        this.x = komadiciX + theMapa.getKomadicWidth() / 2;
        this.y = komadiciY + theMapa.getKlizacHeight() / 2;
        this.dx = (Math.random() * 30 - 15);
        this.dy = (Math.random() * 30 - 15);
        this.size = (int)(Math.random() * 15 + 2);
        color = new Color(200, 200, 200);
        this.gravitacija = .8;
    }

    /**
     * metoda update
     */
    public void update(){
        x += dx;
        y += dy;
        dy += gravitacija;
    }

    /**
     * metoda za iscrtavanje
     * @param g iscrtava grafiku
     */
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }
}
