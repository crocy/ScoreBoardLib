package com.sr.matches;

public class Match {

  private final Team home;
  private final Team away;
  private final int startOrder;

  private int scoreHome = 0;
  private int scoreAway = 0;

  public Match(Team home, Team away, int startOrder) {
    this.home = home;
    this.away = away;
    this.startOrder = startOrder;
  }

  public int getStartOrder() {
    return startOrder;
  }

  public Team getHomeTeam() {
    return home;
  }

  public Team getAwayTeam() {
    return away;
  }

  @Override
  public String toString() {
    return "Match { home=%s, away=%s, scoreHome=%s, scoreAway=%s, startOrder=%s }".formatted(home, away, scoreHome,
        scoreAway, startOrder);
  }

  public int getScore(Team team) {
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    else if (team == home) return scoreHome;
    else if (team == away) return scoreAway;
    else throw new IllegalArgumentException("Team %s not in this match".formatted(team));
  }

  /**
   * Updates, or better yet <b>sets</b> the score for a team in this match.
   *
   * @param team  the team to update the score for
   * @param score a score to update to (can't be negative)
   */
  public void updateScore(Team team, int score) {
    if (score < 0) throw new IllegalArgumentException("Score cannot be negative: %s".formatted(score));
    if (team == null) throw new IllegalArgumentException("Team cannot be null");
    else if (team == home) scoreHome = score;
    else if (team == away) scoreAway = score;
    else throw new IllegalArgumentException("Team %s not in this match".formatted(team));
  }

  public int getTotalScore() {
    return scoreHome + scoreAway;
  }

}
