package Breakout;

import java.lang.Math;

public class Position {
    private float x;
    private float y;

    public Position() {
        x = y = 0;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        x = p.getX();
        y = p.getY();
    }

    public void set(Position pos) {
        x = pos.getX();
        y = pos.getY();
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(Position pos) {
        x += pos.getX();
        y += pos.getY();
    }

    public void update(Position pos, double scale) {
        x += pos.getX() * scale;
        y += pos.getY() * scale;
    }

    public void update(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getRoundX() {
        return Math.round(x);
    }

    public int getRoundY() {
        return Math.round(y);
    }

    @Override
    public String toString() {
        return getX() + ", " + getY();
    }
}
