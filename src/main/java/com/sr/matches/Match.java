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

  public Team getHome() {
    return home;
  }

  public Team getAway() {
    return away;
  }

  public int getScoreHome() {
    return scoreHome;
  }

  public void setScoreHome(int scoreHome) {
    this.scoreHome = scoreHome;
  }

  public int getScoreAway() {
    return scoreAway;
  }

  public void setScoreAway(int scoreAway) {
    this.scoreAway = scoreAway;
  }

  public int getScore(Team team) {
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    if (team == home) return scoreHome;
    if (team == away) return scoreAway;

    throw new IllegalArgumentException("Team %s not in this match".formatted(team));
  }
}
