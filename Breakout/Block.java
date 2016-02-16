package Breakout;

import java.awt.*;

public class Block extends Entity {

    public static final float MIN_WIDTH = 100;
    public static final float MIN_HEIGHT = 50;

    public Block(Position pos, Color c) {
        super(pos, new Position(Main.WIDTH / 6, MIN_HEIGHT));
        mColor = c;
    }

    public void update(double delta) {
        //animate? move them if theres only a few
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(mColor);
        g2d.fillRect(mPos.getRoundX(), mPos.getRoundY(), mDim.getRoundX(), mDim.getRoundY());
        g2d.setColor(mColor.darker());
        g2d.drawRect(mPos.getRoundX(), mPos.getRoundY(), mDim.getRoundX(), mDim.getRoundY());
    }
}
