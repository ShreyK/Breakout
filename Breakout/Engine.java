package Breakout;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Engine extends Canvas implements Runnable {

    public Thread thread;
    public static Game game;

    public boolean running = false;

    public Engine() {
        init();
    }

    // Initialize the variables
    public void init() {
        game = new Game();
        running = true;
        requestFocusInWindow();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                game.mouseMoved(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.keyPressed(e);
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!game.paused || !game.start) {
                    game.keyReleased(e);
                }
            }
        });
        thread = new Thread(this, Main.NAME + "_main");
    }

    public void hasResized(int WIDTH, int HEIGHT) {
        game.resize(WIDTH, HEIGHT);
    }

    // Using runnable, controls the thread to run at 60fps
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / Main.FPS;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                update(delta);
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                game.updateFPSCounter(ticks);
//                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    // Method used to update your game
    public void update(double delta) {
        game.update(delta);
    }

    // Method used to render your game
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render your game here
        game.draw(g2d);
        // Dispose
        g.dispose();
        bs.show();
    }

}
