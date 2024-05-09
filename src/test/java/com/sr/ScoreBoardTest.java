package com.sr;

import com.sr.matches.ScoreBoard;
import com.sr.matches.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {
  @Test
  public void startNewMatch() {
    Team home = new Team("Home");
    Team away = new Team("Away");

    ScoreBoard scoreBoard = new ScoreBoard();
    scoreBoard.startNewMatch(home, away);

    Assertions.assertEquals(0, scoreBoard.getScore(home));
    Assertions.assertEquals(0, scoreBoard.getScore(away));
  }

}
