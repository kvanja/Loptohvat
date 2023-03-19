package Main;
import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa eksplozija kad je kockica unistena
 */
public class Eksplozija {
    private ArrayList<Komadici> pieces;
    private int x,y;
    private Mapa theMapa;
    private boolean isActive;
    private long startTime;

    /**
     * Konstruktor explosion
     * @param x pozicija x
     * @param y pozicija y
     * @param theMapa mapa
     */
    public Eksplozija(int x, int y, Mapa theMapa){
        this.x = x;
        this.y = y;
        this.theMapa = theMapa;
        isActive = true;
        startTime = System.nanoTime();
        pieces = new ArrayList<Komadici>();
        init();

    }

    /**
     * Inicijalizacija rand number. poanta je da eksplozije ne izgledaju isto
     *
     */

    public void init(){
        int randNum = (int)(Math.random() *  20 + 5);
        for (int i = 0; i<randNum; i++){
            pieces.add(new Komadici(x,y, theMapa)) ;
        }
    }

    /**
     * Metoda update
     */
    public void update(){
        for(Komadici piece : pieces){
            piece.update();
        }
        if((System.nanoTime() - startTime) / 1000000 > 2000  && isActive){
            isActive = false;
        }
    }

    /**
     * Metoda koja iscrtava
     * @param g varijabla g
     */
    public void draw(Graphics2D g){
        for(Komadici piece : pieces){
            piece.draw(g);
        }
    }

    /**
     * Metoda koja dohvaca isActive varijablu
     * @return isActice je li aktivan
     */
    public boolean getIsActive(){
        return isActive;
    }
}

