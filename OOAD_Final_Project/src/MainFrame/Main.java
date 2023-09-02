package MainFrame;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private MenuBar menuBar;
    private ToolBar toolBar;
    private Canvas canvas;

    public Main() {
        getContentPane().setLayout(new BorderLayout());
        menuBar = new MenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);
        toolBar = new ToolBar();
        getContentPane().add(toolBar, BorderLayout.WEST);
        canvas = Canvas.getInstance();
        getContentPane().add(canvas, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        Main application = new Main();
        application.setSize(1200, 700);
        application.setTitle("OOAD_UML");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
    }
}