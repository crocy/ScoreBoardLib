package com.sr;

import com.sr.matches.Match;
import com.sr.matches.ScoreBoard;
import com.sr.matches.Team;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

  @Test
  public void startNewMatch_updateScore_finishMatch() {
    Team home = new Team("Home");
    Team away = new Team("Away");
    ScoreBoard scoreBoard = new ScoreBoard();
    Match match = scoreBoard.startNewMatch(home, away);

    assertNotNull(match);
    assertEquals(0, scoreBoard.getScore(home));
    assertEquals(0, scoreBoard.getScore(away));

    // set score 1 : 2
    scoreBoard.updateScore(home, 1);
    scoreBoard.updateScore(away, 2);
    assertEquals(1, scoreBoard.getScore(home));
    assertEquals(2, scoreBoard.getScore(away));

    // set score 3 : 0
    scoreBoard.updateScore(home, 3);
    scoreBoard.updateScore(away, 0);
    assertEquals(3, scoreBoard.getScore(home));
    assertEquals(0, scoreBoard.getScore(away));

    // shouldn't be possible to update score with null or negative value
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.updateScore(null, 1));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.updateScore(home, -1));

    Match finishedMatch = scoreBoard.finishMatch(home, away);
    assertNotNull(finishedMatch);
    assertEquals(match, finishedMatch);

    // shouldn't be possible to update score after match is finished
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.updateScore(home, 2));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.updateScore(away, 1));

    // shouldn't be possible to get score after match is finished
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(home));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(away));

    // shouldn't be possible to finish the same match twice
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(home, away));
  }

  @Test
  public void startNewMatchWithBadTeamData() {
    Team home = new Team("Home");
    Team away = new Team("Away");
    ScoreBoard scoreBoard = new ScoreBoard();

    // start new match with bad data: home or away team is null or home and away team are same
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(home, null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(null, away));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(home, home));

    // get score with bad data: team is null or not in a match
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(home));

    // finish match with bad data: home or away team is null or home and away team are same
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(home, null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(null, away));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(home, home));

    // test starting a match with a team that is already in a match
    Match match = scoreBoard.startNewMatch(home, away);
    assertNotNull(match);
    assertThrowsExactly(IllegalStateException.class, () -> scoreBoard.startNewMatch(away, home));

    // shouldn't be possible to start a match with a team that is already in a match
    Team other = new Team("Other");
    assertThrowsExactly(IllegalStateException.class, () -> scoreBoard.startNewMatch(other, away));

    assertAll("shouldn't be possible to get or update score with a team that is not in this match (or is null)",
        () -> assertThrowsExactly(IllegalArgumentException.class, () -> match.updateScore(other, 1)),
        () -> assertThrowsExactly(IllegalArgumentException.class, () -> match.updateScore(null, 1)),
        () -> assertThrowsExactly(IllegalArgumentException.class, () -> match.getScore(other)),
        () -> assertThrowsExactly(IllegalArgumentException.class, () -> match.getScore(null)));
  }

  @Test
  public void getMatchesSummary() {
    int numOfMatches = 10;
    Match[] matches = new Match[numOfMatches];
    ScoreBoard scoreBoard = new ScoreBoard();

    assertTrue(numOfMatches >= 10, "numOfMatches must be at least 10 for this test to be properly set and valid");

    // generate/start numOfMatches matches
    for (int i = 0; i < numOfMatches; i++) {
      Team home = new Team("Home-" + (i + 1));
      Team away = new Team("Away-" + (i + 1));
      matches[i] = scoreBoard.startNewMatch(home, away);

      // update score for each match in a way that ensures some teams will have the same (total) score (to test proper
      // ordering later on)
      scoreBoard.updateScore(home, i % 3);
      scoreBoard.updateScore(away, i % 3);
    }

    scoreBoard.printMatches();
    Match[] matchesSummary = scoreBoard.getMatchesSummary();

    // check that all matches are in the summary
    for (Match match : matches) {
      assertTrue(Arrays.stream(matchesSummary).anyMatch(m -> m.equals(match)));
    }

    System.out.println("Sorted matches:");
    Arrays.stream(matchesSummary).forEach(System.out::println);

    for (int i = 0; i < matchesSummary.length - 1; i++) {
      if (matchesSummary[i].getTotalScore() == matchesSummary[i + 1].getTotalScore()) {
        // check that the teams are sorted by name in descending order, meaning that the most recently started matches will be first
        if (matchesSummary[i].getStartOrder() < matchesSummary[i + 1].getStartOrder()) {
          fail(
              "Matches are not sorted by descending start order (most recent matches first)\nMatch\n%s\nshouldn't be before match\n%s".formatted(
                  matchesSummary[i], matchesSummary[i + 1]));
        }
      } else if (matchesSummary[i].getTotalScore() < matchesSummary[i + 1].getTotalScore()) {
        fail(
            "Matches are not sorted by total score in descending order\nMatch\n%s\nshouldn't be before match\n%s".formatted(
                matchesSummary[i], matchesSummary[i + 1]));
      }
    }

    // shouldn't be possible to finish a match where both teams are not in the same match
    assertThrowsExactly(IllegalArgumentException.class,
        () -> scoreBoard.finishMatch(matches[0].getHomeTeam(), matches[numOfMatches - 1].getAwayTeam()));

    // finish all matches
    for (Match match : matchesSummary) {
      assertNotNull(scoreBoard.finishMatch(match.getHomeTeam(), match.getAwayTeam()));
    }

    // shouldn't be possible to get a matches summary if there are no active matches
    assertThrowsExactly(IllegalStateException.class, scoreBoard::getMatchesSummary);
  }
}
