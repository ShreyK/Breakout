package Breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public abstract class Entity {

    protected Position mPos;
    protected Position mVel;
    protected Position mDim;
    protected Color mColor;
    protected RoundRectangle2D.Float mRect;

    public static final float DEFAULT_WIDTH = 10;
    public static final float DEFAULT_HEIGHT = 10;

    public Entity(Position p, Position d) {
        mPos = new Position(p);
        mVel = new Position();
        mDim = new Position(d);
        mColor = Color.WHITE;
        mRect = new RoundRectangle2D.Float(p.getX(), p.getY(), d.getX(), d.getY(), 0, 0);
    }


    protected void updateRect() {
        mRect.setFrame(mPos.getX(), mPos.getY(), mDim.getX(), mDim.getY());
    }

    public Position getDim(){
        return mDim;
    }


    public Position getPos() {
        return mPos;
    }

    public void setPos(Position pos) {
        mPos = pos;
    }

    public void updatePos(Position pos) {
        mPos.update(pos);
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        mColor = color;
    }

    public RoundRectangle2D.Float getRect() {
        return mRect;
    }

    public void setRect(float x, float y, float w, float h, float arcw, float arch) {
        mRect = new RoundRectangle2D.Float(x, y, w, h, arcw, arch);
    }

    public abstract void update(double delta);

    public abstract void draw(Graphics2D g2d);

    public Position getVel() {
        return mVel;
    }

    public void updateVel(float x, float y) {
        mVel.update(x, y);
    }
}
