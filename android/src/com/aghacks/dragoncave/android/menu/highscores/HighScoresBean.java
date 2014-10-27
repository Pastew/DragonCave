package com.aghacks.dragoncave.android.menu.highscores;

public class HighScoresBean {

    private String name;
    private int score;

    public HighScoresBean(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}