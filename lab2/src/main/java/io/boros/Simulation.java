package io.boros;

import java.util.Arrays;
import java.util.Objects;

class Simulation {
    private CountryInfo[] countries;

    Simulation(CountryInfo[] countries) {
        Objects.requireNonNull(countries, "Countries not provided");

        if (countries.length > 20) {
            throw new IllegalArgumentException("Max count of counties is 20");
        }
        this.countries = countries;

        boolean[][] matrixOfEUCountries = fillMatrixOfEUCountries();

        for (CountryInfo countryInfo : countries) {
            countryInfo.matrixOfEUCountries = matrixOfEUCountries;
        }
    }

    private boolean[][] fillMatrixOfEUCountries() {
        boolean[][] matrixOfEUCountries = new boolean[CountryInfo.MAX_X][CountryInfo.MAX_Y];
        for (CountryInfo country : countries) {
            for (int x = country.xl; x <= country.xh; x++) {
                for (int y = country.yl; y <= country.yh; y++) {
                    matrixOfEUCountries[x][y] = true;
                }
            }
        }
        return matrixOfEUCountries;
    }

    SimulationResults simulate() {
        while (!isEnd()) {
            for (CountryInfo country : countries) {
                country.nextDay();
            }
        }
        return new SimulationResults(getResults(), countries);
    }

    private boolean isEnd() {
        boolean result = true;
        for (CountryInfo country : countries) {
            if (!country.isComplete)
                result &= checkCountryComplete(country);
        }
        return result;
    }

    private boolean checkCountryComplete(CountryInfo country) {
        for (int x = country.xl; x <= country.xh; x++) {
            for (int y = country.yl; y <= country.yh; y++) {
                for (CountryInfo c : countries) {
                    if (!c.hasCityCoins(x, y)) {
                        return false;
                    }
                }
            }
        }
        country.isComplete = true;
        return true;
    }

    private String getResults() {
        Arrays.sort(countries);
        StringBuilder stringBuilder = new StringBuilder();
        for (CountryInfo country : countries) {
            stringBuilder.append(country.name)
                    .append(" ")
                    .append(country.numberOfDays)
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    static class SimulationResults {

        final String result;
        final CountryInfo[] countries;

        public SimulationResults(String result, CountryInfo[] countries) {
            this.result = result;
            this.countries = countries;
        }
    }

}
