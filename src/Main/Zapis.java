package Main;
import java.io.*;
import java.util.Scanner;

/**
 * Klasa za zapis i citanje file-ova
 */
public class Zapis {
    private String hightScore;
    Zapis(String hs){
        this.hightScore = hs;
    }

    /**
     * Defaultni konstruktor
     */
    public Zapis(){}

    /**
     * metoda za zapis
     */
    public void Zapisi(){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:\\Java Projekt\\group-05\\Igrica\\src\\Main\\HighScore.txt"), "utf-8"))) {
            writer.write(hightScore);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda za ucitavanje
     */
    public void procitaj(){
        File file = new File("C:\\Java Projekt\\group-05\\Igrica\\src\\Main\\HighScore.txt");
        try {
            Scanner sc = new Scanner(file);
            hightScore = sc.next();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * metoda za vracanje najveceg scora
     * @return vraca najveci score
     */
    public String dohvatiHighScore(){
        procitaj();
        return hightScore;
    }
}
