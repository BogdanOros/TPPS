package io.boros;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) throws IOException, URISyntaxException {

        URL resource = Application.class.getClassLoader().getResource("in.txt");
        if (resource == null) {
            throw new IllegalAccessError("Input not found");
        }
        Scanner scanner = new Scanner(Paths.get(resource.toURI()));
        List<CountryInfo[]> cases = new ArrayList<>();
        while (scanner.hasNext()) {
            int numberOfCountries = safeNextInt(scanner);

            if (numberOfCountries == 0) {
                break;
            }
            CountryInfo[] countries = IntStream.range(0, numberOfCountries)
                    .filter(value -> scanner.hasNext())
                    .mapToObj(i -> new CountryInfo(
                            scanner.next(),
                            safeNextInt(scanner),
                            safeNextInt(scanner),
                            safeNextInt(scanner),
                            safeNextInt(scanner)
                    ))
                    .toArray(CountryInfo[]::new);
            cases.add(countries);
        }

        List<Simulation.SimulationResults> results = cases.stream()
                .map(Simulation::new)
                .map(Simulation::simulate)
                .parallel()
                .collect(Collectors.toList());

        try (PrintWriter out = new PrintWriter("out.txt")) {
            out.println(buildResult(results));
        }

    }

    private static int safeNextInt(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        throw new IllegalArgumentException("Incorrect numeric value");
    }

    private static String buildResult(List<Simulation.SimulationResults> simulations) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < simulations.size(); i++) {
            Simulation.SimulationResults simulation = simulations.get(i);
            if (simulation.countries.length == 0)
                continue;
            stringBuilder.append("Case Number")
                    .append(" ")
                    .append(i)
                    .append("\n")
                    .append(simulation.result);
        }

        return stringBuilder.toString();
    }


}
