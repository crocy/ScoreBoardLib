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

# Assumptions

Some assumptions were made when writing this example library.

## No upper score limit

We don't set or check for upper score limit. Some reasonable assumptions could be made, as in prevent updating the score
beyond say 100 points, but that should be discussed with a domain expert first.

Negative score limits were implemented since those make sense in all cases.

## No concurrency issues

We assumed this library wasn't a subject of multithreaded access so no concurrency controls were implemented. If
such issues were to be addressed, we could make the `ScoreBoard.startNewMatch()` and `ScoreBoard.finishMatch()` a
`synchronized` method, or use a `synchronized(matches) { ... }` block inside those methods to prevent concurrent
modification of the `matches` object.

`Collections.synchronizedMap()` or `ConcurrentHashMap` would be another solution but the map data would need to be
structured differently.

## "Outside" data comes in a form of a Team (and maybe a score)

We assume that whoever is going to interact with this lib will have access to all the Teams objects and will pass those
instead of some team IDs (like integers or string IDs). That means that when you want to update the team's score, you
just pass that team and its score to the `ScoreBoard.updateScore()` method and it finds the right match and updates the
appropriate (home or away team's) score.

## Score not part of a Team (class/object)

Score isn't part of the Team object since it depends on the current match and isn't really a characteristic of a team.
That means we have to link the team's and its current match score in some other way.

## Update/set score

Update score was treated as a score to update or set to, not to add to the previous score. Meaning calling
`updateScore(team, 3)` will set that team's score to 3 for the current match. If you later call `updateScore(team, 2)`
method (for the same team and match), the score won't be updated to `previousScore + 2`, but rather to just 2 (actually
deducting 1 score from the previous score).

# Possible improvements

## Logging

More extensive logging with a proper logging lib (like Log4J) could be used.