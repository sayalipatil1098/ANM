package remoteserver;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**

 * ClientScreenReciever is responsible for recieving client screenshot and displaying
 * it in the server. Each connected client has a separate object of this class
 */
class ClientScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    

    public ClientScreenReciever(ObjectInputStream ois, JPanel p) {
        cObjectInputStream = ois;
        cPanel = p;
        //start the thread and thus call the run method
        start();
        
    }
    
    public static boolean hasAlpha(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }
    
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
    
        image = new ImageIcon(image).getImage();
        boolean hasAlpha = hasAlpha(image);
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // no system screen 
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
    public void run(){
        
            try {
                
                int y=0;
				//Read screenshots of the client then draw them
                for (int i = 0; y < 10; i++) 
                {
				
                    //Recieve client screenshot and resize it to the current panel size
                    ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                    System.out.println("New image recieved");
                    Image image = imageIcon.getImage();
                    image = image.getScaledInstance(cPanel.getWidth(),cPanel.getHeight()
                                                        ,Image.SCALE_FAST);
                    //Draw the recieved screenshot
                    Graphics graphics = cPanel.getGraphics();
                    graphics.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                    
                    //Save the Image
                    
                    BufferedImage bi = new BufferedImage(cPanel.getWidth(),cPanel.getHeight(),BufferedImage.TYPE_INT_RGB);

                    Graphics2D g2 = bi.createGraphics();
                    g2.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                    g2.dispose();
                    String baseFileName = "CapturedImage";
                    
					String tempFileName = "D:/Captured Image/"+baseFileName+(i)+".jpg";
                    ImageIO.write(bi, "jpg", new File(tempFileName));
                    
                }
            } catch (IOException ex) {
                ex.printStackTrace();
          } catch(ClassNotFoundException ex){
              ex.printStackTrace();
          }
     }

	}
