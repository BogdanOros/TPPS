package io.boros;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = App.class.getClassLoader().getResource("in.txt");
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        }
        Scanner scanner = new Scanner(Paths.get(resource.toURI()));
        int caseNumber = 1;

        while (scanner.hasNext()) {
            int dx = safeNextInt(scanner);
            int dy = safeNextInt(scanner);

            int countOfShops = safeNextInt(scanner);
            if (countOfShops < 0 || countOfShops > 5) {
                throw new IllegalArgumentException("Shops count of bound");
            }
            int queries = safeNextInt(scanner);
            if (queries < 0 || queries > 120) {
                throw new IllegalArgumentException("queries count of bound");
            }

            CoffeeShop[] coffeeShops = IntStream.range(0, countOfShops)
                    .mapToObj(i -> new CoffeeShop(safeNextInt(scanner), safeNextInt(scanner)))
                    .toArray(CoffeeShop[]::new);

            CoffeeDistanceCalc distanceCalc = new CoffeeDistanceCalc(coffeeShops, dx, dy);

            System.out.println(String.format("Case %d:", caseNumber));
            IntStream.generate(() -> safeNextInt(scanner))
                    .limit(queries)
                    .mapToObj(distanceCalc::calculate)
                    .forEach(result -> System.out.println(String.format("%d {%d;%d}", result.count, result.x, result.y)));
            scanner.nextLine();
            if (!scanner.nextLine().replace(" ", "").equals("0000")) {
                throw new IllegalArgumentException("Incorrect end of case");
            }
            caseNumber++;
        }
    }

    private static int safeNextInt(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (Exception ex) {
            throw new IllegalArgumentException("File content is invalid");
        }
    }

}
