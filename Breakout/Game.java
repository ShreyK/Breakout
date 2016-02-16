package Breakout;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Game {
    private Player player;
    private ArrayList<Ball> balls;
    private ArrayList<Block> blocks;

    long lastTimeBallAdded;

    public static boolean won = false;
    public static boolean dead = false;
    public static boolean paused = false;
    public static boolean start = true;
    private int score;
    private int fps;

    public static final int NUM_BLOCKS = 30;
    public static int lives = 3;

    private static final String PAUSED = "PAUSED";
    private static final String PAUSED_TEXT = "PRESS Q to QUIT\n" +
            "PRESS ANY OTHER KEY TO PLAY";
    private static final String ADD_BALL = "PRESS SPACE TO ADD BALL";
    private static final String DEAD = "YOU DIED";
    private static final String WON = "YOU WON!";
    private static final String RESTART = "Press ANY KEY to Restart!";
    private static final String BREAKOUT = "BREAKOUT";
    private static final String SHREY_KHOSLA = "by Shrey Khosla";
    private static final String ID = "s2khosla";
    private static final String INFO =
            "Mouse: Move Player\n" +
                    "A - D: Move Player\n" +
                    "ESC: Pause Game\n" +
                    "SPACE: Add Ball\n" +
                    "You have 3 lives\n";
    private static final String START = "Press ANY KEY to Start!";

    private Font defaultFont;

    public Game() {
        start = true;
        init();
    }

    private void init() {
        player = new Player(new Position(Main.WIDTH / 2, Main.HEIGHT - 100));
        blocks = new ArrayList<Block>();
        balls = new ArrayList<Ball>();
        initBlocks();
        lastTimeBallAdded = System.currentTimeMillis();


        won = false;
        dead = false;
        paused = false;
        score = 0;
        lives = 3;
        fps = Main.FPS;
        defaultFont = new Font("TimesRoman", Font.PLAIN, 14);
    }

    public void resize(int WIDTH, int HEIGHT) {
        paused = true;
        int xOffset = 0;
        float newBlockWidth = WIDTH / 6;
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            xOffset++;
            if (i % 6 == 0) {
                xOffset = 0;
            }
            b.getDim().set(newBlockWidth, b.getDim().getY());
            b.getPos().set(xOffset * newBlockWidth, b.getPos().getY());
            b.updateRect();
        }

        player.getPos().set(player.getPos().getX(), HEIGHT - 100f);
        player.updateRect();
    }

    private void addBall() {
        if (System.currentTimeMillis() - lastTimeBallAdded >= 1000) {
            lastTimeBallAdded = System.currentTimeMillis();
            balls.add(new Ball(new Position(player.getPos().getX(), player.getPos().getY() - 50)));
        }
    }

    private void initBlocks() {
        Position origin = new Position(0, 100);
        Color c = Color.RED;
        int yOffset = 0;
        int xOffset = 0;
        for (int i = 0; i < NUM_BLOCKS; i++) {
            if (i >= 0 && i <= 5) {
                c = Color.RED;
            } else if (i >= 6 && i <= 11) {
                c = Color.ORANGE;
            } else if (i >= 12 && i <= 17) {
                c = Color.YELLOW;
            } else if (i >= 18 && i <= 23) {
                c = Color.GREEN;
            } else if (i >= 24 && i <= 29) {
                c = Color.BLUE;
            }
            xOffset++;
            if (i % 6 == 0) {
                yOffset++;
                xOffset = 0;
            }
            blocks.add(new Block(new Position(origin.getX() + xOffset * Main.WIDTH / 6, origin.getY() + yOffset * Block.MIN_HEIGHT), c));
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (!paused && !start) {
            player.mouseMoved(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (start) {
            start = false;
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                paused = true;
            } else if (paused) {
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                } else {
                    paused = !paused;
                }
            } else if (!won && !dead) {
                player.keyPressed(e);
            } else {
                init();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!paused && !won && !dead && !start && e.getKeyCode() == KeyEvent.VK_SPACE) {
            addBall();
        }
    }

    //R1 should always be ball

    private boolean checkCollision(Ball ball, Rectangle2D r2) {
        Rectangle2D r1 = ball.mRect.getBounds2D();
        if (r1.getCenterY() >= r2.getY() && r1.getCenterY() <= r2.getMaxY()) {
//            if(r1.getX() <= r2.getX()){
            //hitting from left side
            ball.getVel().set(-1 * ball.getVel().getX(), ball.getVel().getY());
//            } else if(r1.getX() >= r2.getMaxX()){
            //hitting from right side
//                newV.set(-1*newV.getX(), newV.getY());
//            }
            return true;
        }

        return false;
    }

    public void updateFPSCounter(int fps) {
        this.fps = fps;
    }

    public void update(double delta) {
        if (start) {
            //Waiting to start game
        } else if (!dead && !won && !paused) {
            //game is running

            player.update(delta);

            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                if (ball.getPos().getY() + ball.getDim().getY() >= Main.HEIGHT) {
                    Game.lives--;
                    balls.remove(i);
                }
            }

            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                ball.update(delta);

                if (ball.mRect.intersects(player.mRect.getFrame())) {
                    if (!checkCollision(ball, player.mRect.getBounds2D())) {
                        ball.bouncePaddle();
                    }
                }

                for (int j = 0; j < blocks.size(); j++) {
                    Block block = blocks.get(j);
                    block.update(delta);
                    if (ball.mRect.intersects(block.mRect.getFrame())) {
                        if (!checkCollision(ball, block.mRect.getBounds2D())) {
                            ball.bouncePaddle();
                        }
                        blocks.remove(j);
                        score++;
                    }
                }
            }

            if (blocks.size() == 0) {
                //WON!
                won = true;
            }

            if (lives <= 0) {
                won = false;
                dead = true;
            }

        } else if (won) {

        } else if (paused) {
            //show dialog or pause screen of sorts

        }
    }

    private void drawStringMultiLine(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x - g.getFontMetrics().stringWidth(line) / 2, y += g.getFontMetrics().getHeight());
    }

    public void draw(Graphics2D g) {
        if (start) {

            g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
            g.setColor(Color.BLUE);
            g.drawString(BREAKOUT, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(BREAKOUT) / 2, Main.HEIGHT / 4);

            g.setFont(defaultFont);
            g.setColor(Color.YELLOW);
            g.drawString(SHREY_KHOSLA, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(SHREY_KHOSLA) / 2, Main.HEIGHT / 3);
            g.drawString(ID, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(ID) / 2, Main.HEIGHT / 3 + 20);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
            g.setColor(Color.WHITE);
            drawStringMultiLine(g, INFO, Main.WIDTH / 2, Main.HEIGHT / 2);

            g.setColor(Color.GREEN);
            g.drawString(START, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(START) / 2, Main.HEIGHT - 100);
        } else {

            player.draw(g);
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).draw(g);
            }
            for (int i = 0; i < blocks.size(); i++) {
                blocks.get(i).draw(g);
            }
            g.setFont(defaultFont);
            g.setColor(Color.YELLOW);
            g.drawString("FPS: " + fps, 20, 20);
            g.drawString("Score: " + score, 20, 35);
            g.drawString("Lives: " + lives, 20, 50);
            if (dead || won || paused) {
                g.setColor(new Color(0, 0, 0, 200));
                g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
                g.setColor(Color.GREEN);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
            }
            if (dead) {
                g.drawString(DEAD, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(DEAD) / 2, Main.HEIGHT / 2);
                g.drawString(RESTART, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(RESTART) / 2, Main.HEIGHT - 100);
            } else if (won) {
                g.drawString(WON, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(WON) / 2, Main.HEIGHT / 2);
                g.drawString(RESTART, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(RESTART) / 2, Main.HEIGHT - 100);
            } else if (paused) {
                g.drawString(PAUSED, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(PAUSED) / 2, Main.HEIGHT / 2);
                drawStringMultiLine(g, PAUSED_TEXT, Main.WIDTH / 2, Main.HEIGHT - 200);
            }

            if (!dead && !won && !paused) {
                if (balls.size() == 0) {
                    g.drawString(ADD_BALL, Main.WIDTH / 2 - g.getFontMetrics().stringWidth(ADD_BALL) / 2, Main.HEIGHT - 100);
                }
            }
        }
    }

}
