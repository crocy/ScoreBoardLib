package com.sr.matches;

public class Match {

  private final Team home;
  private final Team away;

  private int scoreHome = 0;
  private int scoreAway = 0;

  public Match(Team home, Team away) {
    this.home = home;
    this.away = away;
  }

  public int getScore(Team team) {
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    else if (team == home) return scoreHome;
    else if (team == away) return scoreAway;
    else throw new IllegalArgumentException("Team %s not in this match".formatted(team));
  }

  public void updateScore(Team team, int score) {
    if (score < 0) throw new IllegalArgumentException("Score cannot be negative: %s".formatted(score));
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    else if (team == home) scoreHome = score;
    else if (team == away) scoreAway = score;
    else throw new IllegalArgumentException("Team %s not in this match".formatted(team));
  }
}
