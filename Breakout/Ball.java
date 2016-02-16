package Breakout;

import java.awt.Graphics2D;
import java.util.Random;

public class Ball extends Entity {

    public static final int DEFAULT_SIZE = 18;
    private int mRadius;

    private Random rand;

    public Ball(Position p) {
        super(p, new Position(DEFAULT_SIZE, DEFAULT_SIZE));
        init();
    }

    private void init() {
        rand = new Random();
        mRadius = DEFAULT_SIZE + 2;
        boolean dir = rand.nextBoolean();
        int xVel;
        if (dir) {
            xVel = 1;
        } else {
            xVel = -1;
        }
        mVel = new Position(xVel, -1);
    }

    public void bouncePaddle() {
        // float offset = rand.nextFloat() * .2f;
        // boolean neg = rand.nextBoolean();
        // if (neg) {
            // offset *= -1;
        // }
        mVel.set(mVel.getX(), -1 * mVel.getY());
    }

    private void checkPos() {
        if (mPos.getX() <= 0 || mPos.getX() + mDim.getX() >= Main.WIDTH) {
            mVel.set(-1 * mVel.getX(), mVel.getY());
        }

        if (mPos.getY() <= 0) {
            mVel.set(mVel.getX(), -1 * mVel.getY());
        }

        if (mPos.getY() >= Main.WIDTH - 100) {
            mVel.set(mVel.getX(), -1 * mVel.getY());
        }


    }

    @Override
    public void update(double delta) {
        mPos.update(mVel, Main.BALL_SPEED/Main.FPS*delta);

        updateRect();
        checkPos();
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(mColor);
        g2d.fillOval(mPos.getRoundX(), mPos.getRoundY(), mRadius, mRadius);
    }
}
