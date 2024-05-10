package com.sr;

import com.sr.matches.Match;
import com.sr.matches.ScoreBoard;
import com.sr.matches.Team;
import org.junit.jupiter.api.Test;

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

    scoreBoard.updateScore(home, 1);
    scoreBoard.updateScore(away, 2);

    assertEquals(1, scoreBoard.getScore(home));
    assertEquals(2, scoreBoard.getScore(away));

    // shouldn't be possible to update score with negative value
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
  }

  @Test
  public void startNewMatchWithBadTeamData() {
    Team team = new Team("Home");
    ScoreBoard scoreBoard = new ScoreBoard();

    // start new match with bad data: home or away team is null or home and away team are same
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(null, team));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(team, null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.startNewMatch(team, team));

    // get score with bad data: team is null or not in a match
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.getScore(team));

    // finish match with bad data: home or away team is null or home and away team are same
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(null, team));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(team, null));
    assertThrowsExactly(IllegalArgumentException.class, () -> scoreBoard.finishMatch(team, team));
  }

}
