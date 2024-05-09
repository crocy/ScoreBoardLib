package com.sr;

import com.sr.matches.Match;
import com.sr.matches.ScoreBoard;
import com.sr.matches.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

  @Test
  public void startNewMatch_updateScore_finishMatch() {
    Team home = new Team("Home");
    Team away = new Team("Away");
    ScoreBoard scoreBoard = new ScoreBoard();
    Match match = scoreBoard.startNewMatch(home, away);

    Assertions.assertNotNull(match);
    Assertions.assertEquals(0, scoreBoard.getScore(home));
    Assertions.assertEquals(0, scoreBoard.getScore(away));

    scoreBoard.updateScore(home, 1);
    scoreBoard.updateScore(away, 2);

    Assertions.assertEquals(1, scoreBoard.getScore(home));
    Assertions.assertEquals(2, scoreBoard.getScore(away));

    Match finishedMatch = scoreBoard.finishMatch(home, away);
    Assertions.assertNotNull(finishedMatch);
    Assertions.assertEquals(match, finishedMatch);
  }

}
