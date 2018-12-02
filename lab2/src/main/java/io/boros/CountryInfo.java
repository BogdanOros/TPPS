package io.boros;

import java.util.Objects;

class CountryInfo implements Comparable<CountryInfo> {

    static final int MAX_X = 10;
    static final int MAX_Y = 10;
    private static final int INITIAL_COINS = 1_000_000;

    private static final int PER_DAY = 1_000;

    private int[][] currentMatrix;
    boolean[][] matrixOfEUCountries;

    boolean isComplete;
    int numberOfDays = 0;

    String name;
    int xl, yl, xh, yh;


    CountryInfo(String name, int xl, int yl, int xh, int yh) {

        Objects.requireNonNull(name, "Name cannot be null");

        if (name.length() > 25) {
            throw new IllegalArgumentException("Name cannot be longer then 25");
        }
        if (xl >= MAX_X || xl < 1) {
            throw new IllegalArgumentException("xl in incorrect bounds");
        }

        if (yl >= MAX_Y || yl < 1) {
            throw new IllegalArgumentException("yl in incorrect bounds");
        }

        if (xh >= MAX_X || xh < 1) {
            throw new IllegalArgumentException("xh in incorrect bounds");
        }

        if (yh >= MAX_Y || yh < 1) {
            throw new IllegalArgumentException("yh in incorrect bounds");
        }

        this.name = name;
        this.xh = xh;
        this.xl = xl;
        this.yh = yh;
        this.yl = yl;

        currentMatrix = initMatrix();

    }

    private int[][] initMatrix() {
        int[][] result = new int[MAX_X][MAX_Y];

        for (int x = xl; x <= xh; x++) {
            for (int y = yl; y <= yh; y++) {
                result[x][y] = INITIAL_COINS;
            }
        }

        return result;
    }

    void nextDay() {
        int[][] nextDayMatrix = new int[MAX_X][MAX_Y];

        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                int amountToTransport = currentMatrix[x][y] / PER_DAY;
                int numberOfSuccessfulTransportation =
                        transportToNeighbors(nextDayMatrix, x, y, amountToTransport);
                nextDayMatrix[x][y] += currentMatrix[x][y] - numberOfSuccessfulTransportation * amountToTransport;
            }
        }

        if (!isComplete) {
            numberOfDays++;
        }
        currentMatrix = nextDayMatrix;
    }

    private int transportToNeighbors(int[][] matrix, int x, int y, int amountToTransport) {
        int numberOfSuccessfulTransportation = 0;

        if (checkIsCityAvailable(x - 1, y) && amountToTransport > 0) {
            matrix[x - 1][y] += amountToTransport;
            numberOfSuccessfulTransportation++;
        }
        if (checkIsCityAvailable(x, y - 1) && amountToTransport > 0) {
            matrix[x][y - 1] += amountToTransport;
            numberOfSuccessfulTransportation++;
        }
        if (checkIsCityAvailable(x + 1, y) && amountToTransport > 0) {
            matrix[x + 1][y] += amountToTransport;
            numberOfSuccessfulTransportation++;
        }
        if (checkIsCityAvailable(x, y + 1) && amountToTransport > 0) {
            matrix[x][y + 1] += amountToTransport;
            numberOfSuccessfulTransportation++;
        }
        return numberOfSuccessfulTransportation;
    }

    private boolean checkIsCityAvailable(int x, int y) {
        if (x < 0 || y < 0 || x >= MAX_X || y >= MAX_Y) {
            return false;
        }
        return matrixOfEUCountries[x][y];
    }

    boolean hasCityCoins(int x, int y) {
        return currentMatrix[x][y] != 0;
    }

    @Override
    public int compareTo(CountryInfo country) {

        if (this.numberOfDays > country.numberOfDays)
            return 1;
        if (this.numberOfDays < country.numberOfDays)
            return -1;

        return (this.name.compareTo(country.name));
    }


}
