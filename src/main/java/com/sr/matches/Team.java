package com.sr.matches;

public class Team {

  private final String name;

  public Team(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
