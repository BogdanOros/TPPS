package io.boros;

public class CoffeeShop {

    private int x, y;

    public CoffeeShop(int x, int y) {
        if (x < 1 || x > CoffeeDistanceCalc.MAX_X) {
            throw new IllegalArgumentException("Incorrect point x, not in bounds");
        }
        if (y < 1 || y > CoffeeDistanceCalc.MAX_Y) {
            throw new IllegalArgumentException("Incorrect point y, not in bounds");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x - 1;
    }

    public int getY() {
        return y - 1;
    }
}
