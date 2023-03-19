package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

/**
 * pocetni menu sa slikom, na pritisak gumba igraj ulazi se u sljedeci frame gdje se izabire tezina igrice
 */
public class GlavniEkran extends JFrame{
    private final JPanel gornji;
    private final JPanel dolnji;
    private final Font font;
    private final JButton igraj;
    private JButton btnHighScore;
    private JFrame odabirTezine;
    private MyThread thread;
    private String sTezina;
    private long tezina;
    private JFrame jFUpozorenje;
    private JButton btnUpozorenje;
    private JLabel lblUpozorenje;
    private Zapis zapis;
    private JLabel lblHighScore;
    private JPanel doljnjiTopScore;
    /**
     * konstruktor klase
     */
    public GlavniEkran(){
        setTitle("Loptohvat");
        setSize(609,578);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        zapis = new Zapis();
        font = new Font("Arial", Font.ITALIC,20);

        igraj = new JButton("Igraj");
        igraj.setBackground(Color.DARK_GRAY);
        igraj.setForeground(Color.WHITE);
        igraj.setFont(font);
        igraj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        igraj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odaberiTezinuIgrice();
                close();
            }
        });

        dolnji = new JPanel();
        dolnji.add(igraj);

        btnHighScore = new JButton("Highest score");
        btnHighScore.setBackground(Color.DARK_GRAY);
        btnHighScore.setForeground(Color.WHITE);
        btnHighScore.setFocusPainted(false);
        btnHighScore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighestScore();
            }
        });

        gornji = new JPanel();
        gornji.add(btnHighScore);

        add(gornji,BorderLayout.NORTH);
        add(dolnji,BorderLayout.SOUTH);
    }
    /**
     * Metoda koja otvara novi frame gdje se odabire tezina igrice
     * */
    public void odaberiTezinuIgrice(){
        odabirTezine = new JFrame("Odaberite težinu");
        odabirTezine.setLocationRelativeTo(null);
        odabirTezine.setResizable(false);
        odabirTezine.setSize(300,130);
        odabirTezine.setLayout(new GridLayout(2,2));
        odabirTezine.setVisible(true);

        JLabel lblOdaberiteTezinu = new JLabel("                 Odaberite težinu 1,2 ili 3 :");
        lblOdaberiteTezinu.setForeground(Color.WHITE);
        lblOdaberiteTezinu.setFont(new Font("Arial",Font.BOLD,15));
        JTextField txtaTezina = new JTextField();

        JPanel gornji = new JPanel(new GridLayout(2,1));
        gornji.setBackground(Color.BLACK);
        gornji.add(lblOdaberiteTezinu);
        gornji.add(txtaTezina);

        odabirTezine.add(gornji);

        jFUpozorenje = new JFrame("Greška");
        jFUpozorenje.setSize(300,200);
        jFUpozorenje.setLayout(new GridLayout(2,1));
        jFUpozorenje.setResizable(false);
        jFUpozorenje.setLocationRelativeTo(null);

        lblUpozorenje = new JLabel("Molimo unesite broj između 1 i 3");
        jFUpozorenje.setForeground(Color.RED);

        btnUpozorenje = new JButton("Uredu");
        btnUpozorenje.setBackground(new Color(121, 124, 125));
        btnUpozorenje.setForeground(Color.WHITE);
        btnUpozorenje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(jFUpozorenje);
            }
        });

        jFUpozorenje.add(lblUpozorenje);
        jFUpozorenje.add(btnUpozorenje);

        JButton igraj = new JButton("Igraj!");
        igraj.setFont(font);
        igraj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        igraj.setBackground(new Color(121, 124, 125));
        igraj.setForeground(new Color(0, 0, 0));
        igraj.setBounds(0,0,100,20);
        igraj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(txtaTezina.getText().equals("1" )|| txtaTezina.getText().equals("2")|| txtaTezina.getText().equals("3"))){
                    jFUpozorenje.setVisible(true);
                } else {
                    sTezina = txtaTezina.getText();
                    tezina = Long.parseLong(sTezina);
                    if(tezina > 0  && tezina < 4) {
                        close(odabirTezine);
                        thread = new MyThread(tezina);
                        thread.start();
                    } else {
                        jFUpozorenje.setVisible(true);
                    }
                }
            }
        });

        JPanel dolnji = new JPanel();
        dolnji.setLayout(null);
        igraj.setBounds(0,0,300,50);
        dolnji.add(igraj);
        odabirTezine.add(dolnji);
    }

    /**
     * Metoda otvara novi frame za highscore koji ucitava iz datoteke
     */
    public void HighestScore(){
        JFrame highestScore = new JFrame("Top score");
        highestScore.setSize(250,100);
        highestScore.setLocationRelativeTo(null);
        highestScore.setLayout(new GridLayout(2,1));
        highestScore.setVisible(true);

        lblHighScore = new JLabel("Top score is: " + zapis.dohvatiHighScore());
        lblHighScore.setFont(font);
        lblHighScore.setBackground(Color.BLACK);

        JPanel highScoreGornji = new JPanel();
        highScoreGornji.add(lblHighScore);

        JButton ok = new JButton("Uredu");
        ok.setBackground(new Color(121, 124, 125));
        ok.setForeground(Color.WHITE);
        ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(highestScore);
            }
        });

        doljnjiTopScore = new JPanel();
        doljnjiTopScore.add(ok);

        highestScore.add(highScoreGornji);
        highestScore.add(doljnjiTopScore);
    }

    /**
     * Metoda za zatvaranje trenutnog framea
     */
    public void close(){
        WindowEvent eventZaZatvaranje = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(eventZaZatvaranje);
    }

    /**
     * Metoda za zatvaranje odredenog framea
     * @param imeFramea naziv framea
     */
    public void close(JFrame imeFramea){
        WindowEvent eventZaZatvaranje = new WindowEvent(imeFramea,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(eventZaZatvaranje);
    }

    /**
     * Metoda za crtanje pozadine
     * @param g varijabla g
     */
    public void paint(Graphics g){
        ImageIcon pozadina = new ImageIcon("C:\\Java Projekt\\group-05\\Igrica\\src\\Main\\Loptohvat.png");
        g.drawImage(pozadina.getImage(),0,10,null);
    }

    /**
     * Klasa za thread koja pokrece igricu
     */
    public class MyThread extends Thread
    {
        private long tezina;
        private JFrame theFrame;

        /**
         * Konstruktor
         * @param tezina tezina igrice
         */
        public MyThread(long tezina) {
            this.tezina = tezina;
        }

        /**
         * Metoda run od threada
         */
        @Override
        public void run() {
            theFrame = new JFrame("Igra");
            GamePanel thePanel = new GamePanel(tezina,zapis.dohvatiHighScore(),theFrame);
            theFrame.setResizable(false);
            theFrame.setSize(IgraMain.WIDTH, IgraMain.HEIGHT);
            theFrame.setLocationRelativeTo(null);
            theFrame.add(thePanel);
            theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            theFrame.setVisible(true);
            thePanel.run();
        }
    }
}