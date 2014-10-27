package com.aghacks.dragoncave.android.menu.achievements;

/**
 * Created by Lukasz on 25/10/2014.
 */
public class AchievementsBean {

    private int id;
    private String title;
    private String description;
    private boolean scored;

    public AchievementsBean(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}
