import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {

        new Main();

    }
    public Main () {
        this.setSize(700,600);
        this.setResizable(false);
        this.setBackground(Color.cyan);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        OpenWindow openWindow = new OpenWindow();
        add(openWindow);
    }



}
