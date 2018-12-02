package io.boros;

public class CoffeeDistanceCalc {

    public static final int MAX_X = 1000;
    public static final int MAX_Y = 1000;

    private int dx, dy;

    private CoffeeShop[] coffeeShops;

    public CoffeeDistanceCalc(CoffeeShop[] coffeeShops, int dx, int dy) {
        this.coffeeShops = coffeeShops;
        if (dx < 1 || dx > MAX_X) {
            throw new IllegalArgumentException("dx out of bound");
        }
        if (dy < 1 || dy > MAX_Y) {
            throw new IllegalArgumentException("dy out of bound");
        }
        this.dx = dx;
        this.dy = dy;
    }

    public CoffeePoint calculate(int distance) {
        int[][] initial = initializeCityMatrix();
        for (CoffeeShop shop : coffeeShops) {
            int[][] distancesForShop = calculate(distance, shop);
            initial = merge(initial, distancesForShop);
        }
        return maxPoint(initial);
    }

    public int[][] calculate(int distance, CoffeeShop shop) {
        int[][] city = initializeCityMatrix();
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                boolean isFree = city[i][j] != -1;
                boolean isWithinDistance = Math.abs(shop.getX() - i) + Math.abs(shop.getY() - j) <= distance;
                if (isFree && isWithinDistance) {
                    city[i][j] = 1;
                }
            }
        }
        return city;
    }

    private int[][] merge(int[][] a, int[][] b) {
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                a[i][j] += b[i][j];
            }
        }
        return a;
    }

    private int[][] initializeCityMatrix() {
        int[][] city = new int[dx][dy];
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                city[i][j] = 0;
            }
        }

        for (CoffeeShop shop : coffeeShops) {
            city[shop.getX()][shop.getY()] = -1;
        }

        return city;
    }

    private void printMatrix(int[][] m) {
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                System.out.print(" " + m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private CoffeePoint maxPoint(int[][] m) {
        int xMax = 0;
        int yMax = 0;
        int max = m[xMax][yMax];
        for (int i = 0; i < dx; i++) {
            for (int j = 0; j < dy; j++) {
                if (m[i][j] > max || (m[i][j] == max && j < yMax)) {
                    max = m[i][j];
                    xMax = i;
                    yMax = j;
                }
            }
        }
        return new CoffeePoint(xMax, yMax, max);
    }

    static class CoffeePoint {
        int x, y;
        int count;

        public CoffeePoint(int x, int y, int count) {
            this.x = x + 1;
            this.y = y + 1;
            this.count = count;
        }

    }

}
