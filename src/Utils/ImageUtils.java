package Utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageUtils {
    
    public static String selectImage(JLabel label) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp"));
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            
            // Display image in label
            displayImage(label, path, 150, 150);
            
            return path;
        }
        return null;
    }
    
    public static void displayImage(JLabel label, String imagePath, int width, int height) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                BufferedImage img = ImageIO.read(new File(imagePath));
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImg));
                label.setText("");
            }
        } catch (Exception e) {
            System.err.println("Error displaying image: " + e.getMessage());
            label.setIcon(null);
            label.setText("No Image");
        }
    }
    
    public static ImageIcon getScaledIcon(String imagePath, int width, int height) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                BufferedImage img = ImageIO.read(new File(imagePath));
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (Exception e) {
            System.err.println("Error creating scaled icon: " + e.getMessage());
        }
        return null;
    }
}