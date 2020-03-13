
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {
    private int x,y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}


public class Oyun extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(5,this);
    private int gecenSure = 0;
    private int harcananAtes = 0;
    private BufferedImage image;   // uzay gemisi resmi
    private ArrayList<Ates> atesler = new ArrayList<Ates>(); // ates üretme (birden fazla oldugu icin arraylist)
    
    private int atesdirY = 10;   // ateş ettiğimiz yön Y ekseninde oldugu icin
    private int topX = 0;   // topun x eksenindeki hareketi
    private int topdirX  = 4;   // sürekli topX e eklicez hareket etmis olucak
    private int uzayGemisiX = 0;  // uzay gemisinin nerede başlıycagı
    private int dirUzayX = 40;   //  uzayGemisiX e eklenerek hareket sağlanacak.

    
    public boolean kontrolEt(){

        for(Ates ates : atesler){
            if(new Rectangle(ates.getX(),ates.getY(),5,7).intersects(new Rectangle(topX,0,20,20))){   // intersects = çarpışmayı kontrol eder
                return true;
            }
        }
        return false;
    }
    
    
    public Oyun() {
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png"))); // image okuma
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();  // ActionPerformed her 5 sn yede bir çalışmış olucak ,hareket olaylarında Timer kullanılır. ******************

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        gecenSure += 5;  // paint methodu her 5 saniyede bir çalıştıgı için 5 ekledik
        
        
        g.setColor(Color.red);
        g.fillOval(topX,0, 20, 20);  // 0 olmasının sebebi top y ekseninde hareket etmicek, 20 ler top büyüklüğü
        g.drawImage(image, uzayGemisiX, 490, image.getWidth()/10,image.getHeight()/10,this); // uzay gemisi y ekseninde sabit olucak altta
        
        
        for(Ates ates : atesler){
            if(ates.getY() < 0){    // ates JFrameden cıktıgında siliyoruz ki program yavaşlamasın
                atesler.remove(ates);
            }
        }
        g.setColor(Color.BLUE);
        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(), 5, 7);
        }
        
        
        if(kontrolEt()){
            timer.stop();   // çarpışma olduysa timer ı durdur
            String message="Kazandınız Tebrikler ! \n" + "Harcanan Ateş : "+ harcananAtes + "\nGeçen Süre: " + gecenSure / 1000.0;
            JOptionPane.showMessageDialog(this, message);   // ekrana mesaj yayınlama
            System.exit(0);
        }
        
     }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public void keyTyped(KeyEvent e) {

        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){   // sol tuşuna basma
            if(uzayGemisiX <= 0){    // uzay gemisi eğer en soldaysa
                uzayGemisiX = 0;    // sola gidemezsin demiş olduk
            }
            else{
                uzayGemisiX -= dirUzayX; 
            }
                
        }
        else if(c == KeyEvent.VK_RIGHT){
            if(uzayGemisiX >= 740){    // uzay gemisi eğer en sağdaysa
                uzayGemisiX = 740;    // sağa gidemezsin demiş olduk
            }
            else{
                uzayGemisiX += dirUzayX; 
            }
        }
        else if(c == KeyEvent.VK_SPACE){     // ateş etme tuşu
            atesler.add(new Ates(uzayGemisiX+15, 490));   // x i uzay mekigi neredeyse orada olsun diye tam ucuna denk gelsin diye 15 ekledik
                                                          // y si yukardaki 490 ile aynı çünkü y de sabit
            harcananAtes ++;
        
        }                                                 
        
        
        
        
        
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {   // her actionPerformed calısıtıgında rePaint calıstırcaz , repaintte otomatik olarak painti calıstırcak
        
        for(Ates ates: atesler){
            ates.setY(ates.getY()-atesdirY);
            
        }
        
        
        topX += topdirX;                           // böylelikle topX de sürekli güncellendigi için hareket edicek
        if(topX >= 770){
            topdirX = -topdirX;
        }
        if(topX<= 0){             // 0.cı nokta en sol   800 en son
            topdirX = -topdirX;
        }
        repaint();
    }
    
    
}
