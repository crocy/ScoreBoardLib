# Live Football World Cup Score Board library

A library that shows all the ongoing matches and their scores.

# About

The scoreboard supports the following operations:

1. Start a new match, assuming initial score 0 – 0 and adding it the scoreboard. This should capture following
   parameters:
    1. Home team
    2. Away team
2. Update score. This should receive a pair of absolute scores: home team score and away team score.
3. Finish match currently in progress. This removes a match from the scoreboard.
4. Get a summary of matches in progress ordered by their total score. The matches with the same total score will be
   returned ordered by the most recently started match in the scoreboard.