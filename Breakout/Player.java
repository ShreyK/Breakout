package Breakout;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends Entity {

    private final static float WIDTH = 100;
    private final static float HEIGHT = 20;
    private final static float SPEED = 10;

    public Player(Position pos) {
        super(pos, new Position(WIDTH, HEIGHT));
        mDim.set(WIDTH, HEIGHT);
    }

    public void update(double delta) {
        checkVelocity();
        updateRect();
        if (mPos.getX() < 0) {
            mPos.set(0, mPos.getY());
        } else if (mPos.getX() + mDim.getX() > Main.WIDTH) {
            mPos.set(Main.WIDTH - mDim.getX(), mPos.getY());
        }
//        float offset = .5f;
//        float xDiff = mPos.getX() - mTarget;
//        if (!(Math.abs(xDiff) <= offset)) {
//            if (xDiff < 0) {
//                mVel.set(1, 0);
//            } else {
//                mVel.set(-1, 0);
//            }
//        }
//        mPos.update(mVel, SPEED*(float)delta);
    }

    private void checkVelocity() {
        if (mVel.getX() > 5f) {
            mVel.set(5f, 0);
        } else if (mVel.getX() < -5f) {
            mVel.set(-5f, 0);
        }
    }

    public void mouseMoved(MouseEvent e) {
        mPos.set(e.getX(), mPos.getY());
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            mVel.set(-1, 0);
        } else if (key == KeyEvent.VK_D) {
            mVel.set(1, 0);
        }
        mPos.update(mVel, SPEED);
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(mColor);
        g2d.fillRect(mPos.getRoundX(), mPos.getRoundY(), mDim.getRoundX(), mDim.getRoundY());
    }
}
