package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.HUD.getBodovi;
import static Main.HUD.getHighScore;

public class GamePanel extends JPanel implements Runnable, metode{

    private final int brRedova = 10;
    private final int brStupaca = 8;
    private boolean running;
    private BufferedImage image;
    private Graphics2D g;
    private MyMouseMotionListener theMouseListener;
    private int mouseX;
    private boolean screenShakeActive;
    private long screenShakeTimer;
    private long tezina;
    private int intHighScore;

    private JFrame zavrsetak;
    private JButton izadi;
    private JButton glavniMeni;
    private JFrame frame;

    private Lopta theLopta;
    private Klizac theKlizac;
    private Mapa theMapa;
    private HUD theHud;
    private ArrayList<SuperMoci> superMoci;
    private ArrayList<Eksplozija> eksplozijas;

    /**
     * Konstruktor
     * @param tezina tezina
     * @param highScore najveci score
     * @param frame okvir
     */
    public GamePanel(long tezina,String highScore, JFrame frame){
        intHighScore = Integer.parseInt(highScore);
        this.frame = frame;
        if(tezina == 1){
            this.tezina = 10;
        }
        else if(tezina == 2){
            this.tezina = 5;
        } else {
            this.tezina = 3;
        }
        mouseX = 0;
        theLopta = new Lopta();
        theKlizac = new Klizac();
        theMapa = new Mapa(brStupaca,brRedova);
        theHud = new HUD(intHighScore);
        screenShakeTimer = System.nanoTime();
        theMouseListener = new MyMouseMotionListener();
        superMoci = new ArrayList<SuperMoci>();
        eksplozijas = new ArrayList<Eksplozija>();
        addMouseMotionListener(theMouseListener);
        screenShakeActive = false;
        running = true;
        image = new BufferedImage(IgraMain.WIDTH, IgraMain.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    /**
     * Petlja igrice (game loop)
     */
    public void run(){
        /**
         * 1 sekunde prije nego sto igra pocne ekran se priprema za pocetak
         */
       try{
           Thread.sleep(1000);
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
        /**
         * Dok se petlja izvrsava zelimo renderati, prikazati i updejtati sliku (odnosno petlja igrice)
         */

        while (running) {
            update();
            draw();
            repaint();
            try {
                Thread.sleep(tezina);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * provjerava koliziju izmedu lopte i klizaca, te mape i sueprmoći, te dodaje efekte eksplozije
     */
    public void checkCollisions(){
        Rectangle ballRect = theLopta.getRect();
        Rectangle paddleRect = theKlizac.getRect();
        for(int i = 0; i < superMoci.size(); i++){
            Rectangle powerUpRect = superMoci.get(i).getRect();
            if(paddleRect.intersects(powerUpRect)){
                if(superMoci.get(i).getType() == SuperMoci.siriKlizac && !superMoci.get(i).getIskoristen()){
                    if(!(theKlizac.getWidth() >= 200)) {
                        theKlizac.setWidth(theKlizac.getWidth() * 2);
                        superMoci.get(i).setIskoristen(true);
                    }
                }
            }
        }
        if(ballRect.intersects(paddleRect)){
            theLopta.setY(theKlizac.yPos - theLopta.getSize());
            theLopta.setDY(-theLopta.getDY());
            if(theLopta.getX() + theLopta.getSize() < mouseX - theKlizac.getWidth() / 3){
                theLopta.setDX(theLopta.getDx() - .9);
            }
            if(theLopta.getX() >= mouseX + theKlizac.getWidth()  / 3){
                theLopta.setDX(theLopta.getDx() + .9);
            }
        }
       A: for(int row = 0; row < theMapa.getMapArray().length; row++ ){
            for(int col = 0; col < theMapa.getMapArray()[0].length; col++){
                if(theMapa.getMapArray()[row][col] > 0){
                    int brickx = col * theMapa.getKomadicWidth() + theMapa.HOR_PAD ;
                    int bricky = row * theMapa.getKlizacHeight() + theMapa.VERT_PAD ;
                    int brickWidth = theMapa.getKomadicWidth();
                    int brickHeight = theMapa.getKlizacHeight();
                    Rectangle brickRect = new Rectangle(brickx, bricky, brickWidth, brickHeight);

                    if(ballRect.intersects(brickRect)){
                        if(theMapa.getMapArray()[row][col] == 1){
                            eksplozijas.add(new Eksplozija(brickx,bricky, theMapa));
                            screenShakeActive = true;
                            screenShakeTimer = System.nanoTime();
                        }
                        if(theMapa.getMapArray()[row][col] > 3){
                            superMoci.add(new SuperMoci(brickx,bricky, theMapa.getMapArray()[row][col],brickWidth,brickHeight));
                            theMapa.setBrick(row,col,3);
                        }
                        else {
                            theMapa.hitBrick(row,col);
                        }
                        theMapa.hitBrick(row, col);
                        theLopta.setDY(-theLopta.getDY());
                        if(tezina == 10){
                            theHud.addScore(5);
                        }
                        else if(tezina == 5){
                            theHud.addScore(10);
                        } else {
                            theHud.addScore(20);
                        }
                        //jedan od nacina kako da se zaustavi petlja
                        break A;
                    }
                }
            }
        }
    }

    /**
     * Dohvaca brRedova
     * @return vraca broj redova
     */
    public int getBrRedova() {
        return brRedova;
    }

    /**
     * DOhvaca broj stupaca
      * @return brStupaca broj stuapaca
     */
    public int getBrStupaca() {
        return brStupaca;
    }

    /**
     * Metoda za update radi lakseg snalazenja
     */
    public void update(){
        for (SuperMoci power : superMoci){
            power.update();
        }
        theKlizac.update();

        checkCollisions();

        theLopta.update();

        for(int i = 0; i< eksplozijas.size(); i++ ){
            eksplozijas.get(i).update();
            if(!eksplozijas.get(i).getIsActive()){
                eksplozijas.remove(i);
            }
        }
        if((System.nanoTime() - screenShakeTimer) / 1000000> 300 && screenShakeActive){
            screenShakeActive = false;
        }
    }
    /**
     * Ispcrtava sve komponente u igri
     */
    public void draw(){
        g.setColor(new Color(121, 124, 125));
        g.fillRect(0,0,IgraMain.WIDTH, IgraMain.HEIGHT);
        theLopta.draw(g);
        theKlizac.draw(g);
        theMapa.draw(g);
        theHud.draw(g);
        drawPowerUps();

        if(theMapa.isThereAWin() == true){
            drawWin();
            if(getBodovi() > getHighScore()){
                Zapis zapis = new Zapis(Integer.toString(getBodovi()));
                zapis.Zapisi();
            }
            ZavrsniEkran("Pobjedili ste!");
            running = false;
        }

        if(theLopta.youLose() == true){
            drawLoser();
            if(getBodovi() > getHighScore()){
                Zapis zapis = new Zapis((Integer.toString(getBodovi())));
                zapis.Zapisi();
            }
            running = false;
            ZavrsniEkran("Izgubili ste!");
        }

        for(Eksplozija ex : eksplozijas){
            ex.draw(g);
        }
    }

    /**
     * Zavrsni ekran koji se pozvia kad igrica zavrsi s 2 buttona izadi i pokreni glavni meni
     * @param kraj
     */
    private void ZavrsniEkran(String kraj){
        zavrsetak = new JFrame(kraj);
        zavrsetak.setLocationRelativeTo(null);
        zavrsetak.setLayout(new FlowLayout());
        zavrsetak.setSize(250,70);
        zavrsetak.setResizable(false);
        zavrsetak.setVisible(true);

        izadi = new JButton("Izađi");
        izadi.setBackground(Color.DARK_GRAY);
        izadi.setForeground(Color.WHITE);
        izadi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        izadi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(frame);
            }
        });
        glavniMeni = new JButton("Pocetni meni");
        glavniMeni.setBackground(Color.DARK_GRAY);
        glavniMeni.setForeground(Color.WHITE);
        glavniMeni.setCursor(new Cursor(Cursor.HAND_CURSOR));
        glavniMeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(zavrsetak);
                frame.setVisible(false);
                GlavniEkran gl = new GlavniEkran();
            }
        });

        zavrsetak.add(izadi);
        zavrsetak.add(glavniMeni);
    }

    public void close(JFrame okvir){
        WindowEvent eventZaZatvaranje = new WindowEvent(okvir,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(eventZaZatvaranje);
    }

    /**
     * Metoda za pozicioniranje, boju, font i velicinu teksta za izgubljenu igru
     */
    public void drawLoser() {
        g.setColor(new Color(144, 9, 0));
        g.setFont(new Font("Courier New", Font.BOLD, 50));
        g.drawString("You lost!!", 200, 400);
    }


    /**
     * Metoda za pozicioniranje, boju, font i velicinu teksta za pobjednika
     */
    public void drawWin() {
        g.setColor(Color.RED);
        g.setFont(new Font("Courier New", Font.BOLD, 50));
        g.drawString("You Win!!", 200, 200);
    }
    public void drawPowerUps(){
        for(SuperMoci power : superMoci){
            power.draw(g);
        }
    }

    /**
     * Metoda za crtanje i brisanje grafike
     */
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int x = 0;
        int y = 0;

        if(screenShakeActive == true){
            x = (int)(Math.random() * 10 -5);
            y = (int)(Math.random() * 10 -5);
        }
        g2.drawImage(image, x,y, IgraMain.WIDTH, IgraMain.HEIGHT, null);
        g2.dispose();
    }

    /**
     * Metode za mouselistener potrebne za dohvatiti poziciju misa u igrici
     */
    private class MyMouseMotionListener implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {
        }
        /**
         * metoda misa za upravljanje klizaca
         * @param e
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            theKlizac.mouseMoved(e.getX());
        }
    }
}

/**
 * Potrebno za ishod
 */
    interface metode{
         void draw();
        void update();
    }
