package com.sr.matches;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard {

  private Map<Team, Match> matches = new HashMap();

  public void startNewMatch(Team home, Team away) {
    if (matches.containsKey(home))
      throw new IllegalStateException("Home team %s is already in a match".formatted(home));
    if (matches.containsKey(away))
      throw new IllegalStateException("Away team %s is already in a match".formatted(away));

    Match match = new Match(home, away);
    matches.put(home, match);
    matches.put(away, match);
  }

  public int getScore(Team team) {
    return matches.get(team).getScore(team);
  }
}
