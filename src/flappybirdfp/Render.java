/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybirdfp;

/**
 *
 * @author dimas
 */
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Render {
    public int x;
    public int y;
    public Image image;
    public AffineTransform transform;
    
    //Overloading
    public Render() {
        Toolkit.getDefaultToolkit().sync();
    }

    public Render(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = Util.loadImage(imagePath);
    }
}
