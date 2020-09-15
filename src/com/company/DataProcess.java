package com.company;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcess {
    static double calculateHomeGamesPlayed(String team, List<List<String>> leagueData) {
        int n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n++;
            }
        }
        return n;
    }

    static double calculateAwayGamesPlayed(String team, List<List<String>> leagueData) {
        int n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n++;
            }
        }
        return n;
    }


    static double calculateAwayScoredGoals(String team, List<List<String>> leagueData) {
        double n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(6));
            }

        }
        return n;
    }

    static double calculateAwayConcededGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(5));
            }
        }
        return n;
    }

    static double calculateHomeScoredGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(5));
            }

        }
        return n;
    }

    static double calculateHomeConcededGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(6));
            }
        }
        return n;
    }

    static String showPercentages(Double probability) {
        int n = (int) (probability * 10000);
        double n1 = n / 100D;
        return Double.toString(n1) + "%";

    }

    static Map<Integer, String> calculateProbabilityOfResultForEachTeam(double teamExpectedGoals) {
        PoissonDistribution distribution = new PoissonDistribution(teamExpectedGoals);

        Map<Integer, String> probabilityInPercentages = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            probabilityInPercentages.put(i, showPercentages(distribution.probability(i)));
        }
        return probabilityInPercentages;
    }

    static Map<String, String> calculateProbabilityOfMatchResult(double homeTeamExpectedGoals, double awayTeamExpectedGoals) {
        PoissonDistribution homeTeamDistribution = new PoissonDistribution(homeTeamExpectedGoals);
        PoissonDistribution awayTeamDistribution = new PoissonDistribution(awayTeamExpectedGoals);
        Map<Integer, Double> homeTeamProbability = new HashMap<>();
        Map<Integer, Double> awayTeamProbability = new HashMap<>();


        for (int i = 0; i < 6; i++) {
            homeTeamProbability.put(i, homeTeamDistribution.probability(i));
            awayTeamProbability.put(i, awayTeamDistribution.probability(i));

        }
        Map<String, String> resultsPercentage = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String goals = i + "-" + j;
                resultsPercentage.put(goals, showPercentages(homeTeamProbability.get(i) * awayTeamProbability.get(j)));
            }

        }
        return resultsPercentage;

    }
}
