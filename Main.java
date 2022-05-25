import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<HashMap<String, String>> matchesList = convertCSVToMap(
                "/home/nishant_soni/JAVA CODES/Data Processing/src/assets/matches.csv");
        ArrayList<HashMap<String, String>> deliveriesList = convertCSVToMap(
                "/home/nishant_soni/JAVA CODES/Data Processing/src/assets/deliveries.csv");

        matchesPlayedPerYearOfAllYear(matchesList);

        matchesWonOfAllTeamsOverAllYear(matchesList);

        extraRunConcededPerTeam(deliveriesList);

        // 4. For the year 2015 get the top economical bowlers.

        matchesPlayedInEachCities(matchesList);
    }

    private static void matchesPlayedPerYearOfAllYear(ArrayList<HashMap<String, String>> matchesList) {
        System.out.println("1. Number of matches played per year of all the years in IPL.");
        List<String> distinctYear = matchesList.stream()
                .filter(distinctByKey(p -> p.get("season")))
                .map(p -> p.get("season")).toList();

        HashMap<String, Long> q1 = new HashMap<>();
        for (String year : distinctYear) {
            long count = matchesList.stream().filter(p -> p.get("season").equals(year)).count();
            q1.put(year, count);
        }
        System.out.println(q1);
    }

    private static void matchesWonOfAllTeamsOverAllYear(ArrayList<HashMap<String, String>> matchesList) {
        System.out.println("2. Number of matches won of all teams over all the years of IPL.");
        List<String> distinctTeams = matchesList.stream()
                .filter(distinctByKey(p -> p.get("winner")))
                .map(p -> p.get("winner")).toList();

        HashMap<String, Long> q1 = new HashMap<>();
        for (String team : distinctTeams) {
            if (!team.equals("")) {
                long count = matchesList.stream().filter(p -> p.get("winner").equals(team)).count();
                q1.put(team, count);
            }
        }
        System.out.println(q1);
    }

    private static void extraRunConcededPerTeam(ArrayList<HashMap<String, String>> deliveriesList) {
        System.out.println("3. For the year 2016 get the extra runs conceded per team.");
        long extras = deliveriesList
                .stream().filter(p -> Integer.parseInt(p.get("match_id")) >= 577
                        && Integer.parseInt(p.get("match_id")) <= 636 && Integer.parseInt(p.get("extra_runs")) > 0)
                .map(p -> p.get("extra_runs")).count();
        System.out.println(extras);
    }

    private static void matchesPlayedInEachCities(ArrayList<HashMap<String, String>> matchesList) {
        System.out.println("5. Create your own scenario (Number of matches played in each city)");
        List<String> distinctCities = matchesList.stream()
                .filter(distinctByKey(p -> p.get("city")))
                .map(p -> p.get("city")).toList();

        HashMap<String, Long> ans = new HashMap<>();
        for (String city : distinctCities) {
            if (!city.equals("")) {
                long count = matchesList.stream().filter(p -> p.get("city").equals(city)).count();
                ans.put(city, count);
            }
        }
        System.out.println(ans);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static ArrayList<HashMap<String, String>> convertCSVToMap(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        sc.useDelimiter(",");
        String headerLine = sc.nextLine();
        String[] header = headerLine.split(",");

        ArrayList<HashMap<String, String>> matchesList = new ArrayList<>();
        HashMap<String, String> matchMap;
        while (sc.hasNextLine()) {
            matchMap = new HashMap<>();
            String line = sc.nextLine();
            String[] matches = line.split(",", -1);
            for (int i = 0; i < header.length; i++) {

                matchMap.put(header[i], matches[i]);
            }
            matchesList.add(matchMap);
        }
        sc.close();
        return matchesList;
    }
}
