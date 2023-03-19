package Main;
import java.awt.*;
/**
 * Klasa za bodovi
 */
public class HUD extends Zapis {
    private static int bodovi;
    static int highScore;

    /**
     * u konstruktoru se prima highscore koji je ucitan iz datoteke u te se uspoređuje sa trenutnim rezultatom
     * @param highScore postavlja highscore
     */
    public HUD(int highScore){
        this.highScore = highScore;
        bodovi = 0;
    }

    /**
     * Metoda za iscrtavanje bodovi-a u igrici
     * @param g iscrtava
     */
    public void draw(Graphics2D g){
        if(bodovi > highScore) {
            g.setColor(new Color(206, 6, 34));
            g.setFont(new Font("Courier New", Font.PLAIN, 35));
            g.drawString("New Top Score: " + bodovi + "!", 20 ,35);
        } else {
            g.setColor(new Color(255, 250, 254));
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Score: " + bodovi, 20 ,35);
        }
    }

    /**
     * metoda koja dohvaca bodove
     * @return vraca bodove
     */
    public static int getBodovi(){
        return bodovi;
    }

    /**
     * Metoda koja omogucuje modificiranje varijable bodovi izvan klase
     * @param score dodaje score
     */
    public void addScore(int score){
        this.bodovi += score;
    }


    /**
     * Metoda koja dohvaca highscore varijablu
     * @return vraca najveci score
     */
    public static int getHighScore(){
        return highScore;
    }
}
