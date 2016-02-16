package Breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main extends JFrame {

    // DIMENESIONS of the game window
    public static int FPS = 60;
    public static int BALL_SPEED = 200;
    public static int MIN_WIDTH = 1280;
    public static int MIN_HEIGHT = MIN_WIDTH / 16 * 9;
    public static int MAX_WIDTH = 1920;
    public static int MAX_HEIGHT = MAX_WIDTH / 16 * 9;
    public static int WIDTH = MIN_WIDTH;
    public static int HEIGHT = MIN_HEIGHT;
    public static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
    public static final Dimension MIN_DIM = new Dimension(MIN_WIDTH, MIN_HEIGHT);

    // Name of the game (will appear at the top "menu bar"
    public static String NAME = "Breakout";

    public Engine engine;

    public Main() {
        setName(NAME);
        setMaximumSize(DIMENSIONS);
        setMinimumSize(MIN_DIM);
        setPreferredSize(MIN_DIM);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        engine = new Engine();
        add(engine, BorderLayout.CENTER);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocusInWindow();
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                WIDTH = getWidth();
                HEIGHT = getHeight();
                engine.hasResized(WIDTH,HEIGHT);
            }
        });

        engine.run();
    }

    public static void main(String[] args) {
      if(args.length == 2){
        FPS = Integer.parseInt(args[0]);
        BALL_SPEED = Integer.parseInt(args[1]);
      }
      System.out.println("Running Program with: FPS =" + FPS + ", BALL SPEED= " + BALL_SPEED);
      new Main();
    }
}
