package com.sr.matches;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard {

  private final Map<Team, Match> matches = new HashMap<>();

  public Match startNewMatch(Team home, Team away) {
    if (matches.containsKey(home))
      throw new IllegalStateException("Home team %s is already in a match".formatted(home));
    if (matches.containsKey(away))
      throw new IllegalStateException("Away team %s is already in a match".formatted(away));

    Match match = new Match(home, away);
    matches.put(home, match);
    matches.put(away, match);

    return match;
  }

  public int getScore(Team team) {
    return matches.get(team).getScore(team);
  }

  public void updateScore(Team team, int score) {
    matches.get(team).updateScore(team, score);
  }

  public Match finishMatch(Team team1, Team team2) {
    if (team1 == null) throw new IllegalArgumentException("Team 1 cannot be null");
    if (team2 == null) throw new IllegalArgumentException("Team 2 cannot be null");
    if (team1 == team2) throw new IllegalArgumentException("Team 1 and 2 cannot be the same");

    Match match1 = matches.get(team1);
    if (match1 == null) throw new IllegalArgumentException("Team %s not in a match".formatted(team1));
    Match match2 = matches.get(team2);
    if (match2 == null) throw new IllegalArgumentException("Team %s not in a match".formatted(team2));

    if (match1 != match2)
      throw new IllegalArgumentException("Teams %s and %s not in the same match".formatted(team1, team2));

    matches.remove(team1);
    matches.remove(team2);

    return match1;
  }
}
