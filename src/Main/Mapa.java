package Main;
import java.awt.*;
/**
 * Klasa za kreiranje kvadrata u igrici koje cemo razbijati
 */
public class Mapa extends HUD {
    private int[][] theMapa;
    private int komadicHeight, komadicWidth;
    public final int HOR_PAD = 80, VERT_PAD = 50;
    public Mapa(int row, int col){
        super(0);
        initMap(row, col);
        komadicWidth = (IgraMain.WIDTH - 2 * HOR_PAD) /col;
        komadicHeight = (IgraMain.HEIGHT / 2  - VERT_PAD * 2) / row;
    }

    /**
     * Metoda inicijalizacije mape
     * @param row red
     * @param col stupac
     */
    public void initMap(int row, int col){
        theMapa = new int[row][col];
        for(int i = 0; i< theMapa.length; i++){
            for(int j = 0; j< theMapa[0].length; j++){
                int r = (int)(Math.random() *4 +1);
                theMapa[i][j] = r;
            }
        }
    }

    /**
     * metoda za crtanje kockica i njihovih boja
     * @param g g
     */
    public void draw(Graphics2D g){
        g.setColor(Color.DARK_GRAY);

        for(int row = 0; row < theMapa.length; row++){
            for(int col = 0; col< theMapa[0].length; col++){
                if(theMapa[row][col]> 0){
                    if(theMapa[row][col] == 1){
                        g.setColor(new Color(200, 200, 200));
                    }
                    if(theMapa[row][col] == 2){
                        g.setColor(new Color(0, 255, 255));
                    }
                    if(theMapa[row][col] == 3){
                        g.setColor(new Color(0, 0, 255));
                    }
                    if(theMapa[row][col] == SuperMoci.siriKlizac){
                        g.setColor(SuperMoci.sirokiKlizacColor);
                    }
                    g.fillRect(col* komadicWidth + HOR_PAD, row* komadicHeight + VERT_PAD, komadicWidth, komadicHeight);
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.WHITE);
                    g.drawRect(col* komadicWidth + HOR_PAD, row* komadicHeight + VERT_PAD, komadicWidth, komadicHeight);
                }
            }
        }
    }
    /**
     * Metoda za provjeru pobjede
     * @return thereIsAWin
     */
    public boolean isThereAWin(){
        boolean thereIsAWin = false;
        int bricksRemaining = 0;
        for(int row = 0; row< theMapa.length; row++){
            for(int col = 0; col < theMapa[0].length; col++){
                bricksRemaining += theMapa[row][col];
            }
        }
        if(bricksRemaining == 0){
            thereIsAWin = true;
        }
        return thereIsAWin;
    }
    /**
     * Getter metoda za dohvatiti varijablu
     * @return theMapa
     */
    public int [][] getMapArray(){
        return theMapa;
    }
    /**
     *
     * @param row red
     * @param col stupac
     * @param value vrijednost
     */
    public void setBrick(int row, int col, int value){
        theMapa[row][col] = value;
    }
    /**
     * Dohvaca širinu kvadrata
     * @return komadicWidth
     */
    public int getKomadicWidth(){
        return komadicWidth;
    }
    /**
     * Dohvaca visinu kvadrata
     * @return komadiceHeight
     */
    public int getKlizacHeight(){
        return komadicHeight;
    }

    /**
     * Metoda za odrediti razbijenu kockicu
     * @param row red
     * @param col stupac
     */
    public void hitBrick(int row, int col){
        theMapa[row][col] -=1;
        if(theMapa[row][col] < 0){
            theMapa[row][col] = 0;
        }
    }
}
