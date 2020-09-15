package com.company;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.company.DataProcess.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("FOOTBALL MATCH RESULT PREDICTOR");
        printLine();
        List<List<String>> records = new ArrayList<>();
        Scanner scanner1 = new Scanner(System.in);
        String pathName = "C:\\Users\\User\\Desktop\\D1.csv";
        try (Scanner scanner = new Scanner(new File(pathName));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromRow(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {

        }

        System.out.println("Enter Home team name");
        String homeTeam = scanner1.nextLine();
        System.out.println("Enter Away team name");
        String awayTeam = scanner1.nextLine();

        int totalNumberOfHomeGames = records.size() - 1;
        int totalNumberOfAwayGames = totalNumberOfHomeGames;

        int leagueHomeGoals = 0;
        for (int i = 1; i < records.size(); i++) {
            leagueHomeGoals = leagueHomeGoals + Integer.parseInt(records.get(i).get(5));
        }

        int leagueAwayGoals = 0;
        for (int i = 1; i < records.size(); i++) {
            leagueAwayGoals = leagueAwayGoals + Integer.parseInt(records.get(i).get(6));
        }

        double homeTeamHomeScoredGoals = calculateHomeScoredGoals(homeTeam, records);

        double homeTeamHomeGames = calculateHomeGamesPlayed(homeTeam, records);

        double awayTeamAwayScoredGoals = calculateAwayScoredGoals(awayTeam, records);

        double awayTeamAwayGames = calculateAwayGamesPlayed(awayTeam, records);

        double awayTeamAwayConcededGoals = calculateAwayConcededGoals(awayTeam, records);

        double homeTeamHomeConcededGoals = calculateHomeConcededGoals(homeTeam, records);

        double averageScoredGoalsByHomeTeam = (double) leagueHomeGoals / (double) totalNumberOfHomeGames;
        double averageScoredGoalsByAwayTeam = (double) leagueAwayGoals / (double) totalNumberOfAwayGames;
        double averageConcededGoalsByHomeTeam = (double) leagueAwayGoals / (double) totalNumberOfHomeGames;
        double averageConcededGoalsByAwayTeam = (double) leagueHomeGoals / (double) totalNumberOfHomeGames;

        double homeTeamAttackStrength = (homeTeamHomeScoredGoals / homeTeamHomeGames) / averageScoredGoalsByHomeTeam;
        double awayTeamDefensiveStrength = (awayTeamAwayConcededGoals / awayTeamAwayGames) / averageConcededGoalsByAwayTeam;
        double awayTeamAttackStrength = (awayTeamAwayScoredGoals / awayTeamAwayGames) / averageScoredGoalsByAwayTeam;
        double homeTeamDefensiveStrength = (homeTeamHomeConcededGoals / homeTeamHomeGames) / averageConcededGoalsByHomeTeam;

        double homeTeamExpectedGoals = homeTeamAttackStrength * awayTeamDefensiveStrength * averageScoredGoalsByHomeTeam;
        double awayTeamExpectedGoals = awayTeamAttackStrength * homeTeamDefensiveStrength * averageScoredGoalsByAwayTeam;

        System.out.println(homeTeam + " score probability:");
        printLine();
        for (Map.Entry<Integer, String> entry : (calculateProbabilityOfResultForEachTeam(  homeTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }
        printLine();

        System.out.println(awayTeam + " score probability:");
        printLine();
        for (Map.Entry<Integer, String> entry : (calculateProbabilityOfResultForEachTeam(awayTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }

        printLine();
        printLine();
        System.out.println("Probability of match result for " + homeTeam + ":" + awayTeam);
        for (Map.Entry<String, String> entry : (calculateProbabilityOfMatchResult(homeTeamExpectedGoals, awayTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }

        for (Map.Entry<String, String> entry : (calculateProbabilityOfMatchResult(homeTeamExpectedGoals, awayTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }
        printLine();
        System.out.println("Most probable result for " + homeTeam + " vs " + awayTeam);
        Map.Entry<String, String> maxEntry = null;
        maxEntry = null;
        for (Map.Entry<String, String> entry :
                (calculateProbabilityOfMatchResult(homeTeamExpectedGoals, awayTeamExpectedGoals)).entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
                Set<String> max = new HashSet<>();
                max.add(String.valueOf(maxEntry));
            }
        }
        System.out.println(maxEntry);

    }

    private static List<String> getRecordFromRow(String row) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScan = new Scanner(row)) {
            rowScan.useDelimiter(",");
            while (rowScan.hasNext()) {
                values.add(rowScan.next());
            }
        }
        return values;
    }

    static void printLine() {
        System.out.println("--------------------------------------------------------");
    }
}