package com.sr.matches;

import java.util.*;

public class ScoreBoard {

  // match order ID
  private static int matchOrder = 0;

  private final List<Match> matches = new LinkedList<>();
  private final Map<Team, Match> teamMatchMap = new HashMap<>();

  public Match startNewMatch(Team home, Team away) {
    if (home == null) throw new IllegalArgumentException("Home team cannot be null");
    if (away == null) throw new IllegalArgumentException("Away team cannot be null");
    if (home == away) throw new IllegalArgumentException("Home and Away teams cannot be the same");

    if (teamMatchMap.containsKey(home)) {
      throw new IllegalStateException("Home team %s is already in a match".formatted(home));
    }
    if (teamMatchMap.containsKey(away)) {
      throw new IllegalStateException("Away team %s is already in a match".formatted(away));
    }

    Match match = new Match(home, away, matchOrder++);
    matches.add(match);
    teamMatchMap.put(home, match);
    teamMatchMap.put(away, match);

    return match;
  }

  public int getScore(Team team) {
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    if (!teamMatchMap.containsKey(team)) throw new IllegalArgumentException("Team %s not in a match".formatted(team));

    return teamMatchMap.get(team).getScore(team);
  }

  public void updateScore(Team team, int score) {
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    if (!teamMatchMap.containsKey(team)) throw new IllegalArgumentException("Team %s not in a match".formatted(team));

    teamMatchMap.get(team).updateScore(team, score);
  }

  public Match finishMatch(Team team1, Team team2) {
    if (team1 == null) throw new IllegalArgumentException("Team 1 cannot be null");
    if (team2 == null) throw new IllegalArgumentException("Team 2 cannot be null");
    if (team1 == team2) throw new IllegalArgumentException("Team 1 and 2 cannot be the same");

    Match match1 = teamMatchMap.get(team1);
    if (match1 == null) throw new IllegalArgumentException("Team %s not in a match".formatted(team1));
    Match match2 = teamMatchMap.get(team2);
    if (match2 == null) throw new IllegalArgumentException("Team %s not in a match".formatted(team2));

    if (match1 != match2) {
      throw new IllegalArgumentException("Teams %s and %s not in the same match".formatted(team1, team2));
    }

    if (!matches.remove(match1)) throw new IllegalStateException("%s not found".formatted(match1));

    teamMatchMap.remove(team1);
    teamMatchMap.remove(team2);

    return match1;
  }

  /**
   * Sort matches list based on the matches' total score and return a copy of the sorted list.
   *
   * @return a copy of the sorted matches array
   */
  public Match[] getMatchesSummary() {
    if (matches.isEmpty()) throw new IllegalStateException("No ongoing matches");

    // The matches list is reversed (with `reversed()`) so that the most recent matches are at the beginning of the list.
    // This does not affect the actual matches positions in the list (they aren't actually reversed, just shown as such).
    return matches.reversed().stream().sorted(Comparator.comparingInt(Match::getTotalScore).reversed())
        .toArray(Match[]::new);
  }

  // helper method
  public void printMatches() {
    System.out.println("Matches:");
    matches.forEach(System.out::println);
  }
}
