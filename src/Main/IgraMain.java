package Main;
/**
 * Izradili Kristijan Vanja, Igor Pažur, Robert Hunjet
 * Izrađeno u svrhu projekta iz JAVA-e.
 */


/**
 * Main koji pokrece igricu (program)
 */
public class IgraMain {
    public static final int WIDTH = 640, HEIGHT =620;

    public IgraMain(){
        GlavniEkran glavniEkran = new  GlavniEkran();
    }
    public static void main(String[] args) {
        IgraMain igrica = new IgraMain();
    }
}
