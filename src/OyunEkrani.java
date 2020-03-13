
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    public static void main(String[] Args){
        
        OyunEkrani ekran = new OyunEkrani("Space Game");
        ekran.setResizable(false);
        ekran.setFocusable(false);
        
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun = new Oyun();
        oyun.requestFocus();  // klavye işlemlerini anlaması
        oyun.addKeyListener(oyun);  // klavyeden işlemlerin alınması
        oyun.setFocusable(true);  // odak JPanel de
        oyun.setFocusTraversalKeysEnabled(false); // klavye işlemleri gerçekleşmesi
        
        ekran.add(oyun);   // Jpanel Jframe e eklendi.
        ekran.setVisible(true);  
        
        
        
    } 
}
