package com.example.votingsystem;

public class Candidate {
    private final String name;
    private final String city;
    private final String achievements;
    private final String manifesto;

    public Candidate(String name, String city, String achievements, String manifesto) {
        this.name = name;
        this.city = city;
        this.achievements = achievements;
        this.manifesto = manifesto;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAchievements() {
        return achievements;
    }

    public String getManifesto() {
        return manifesto;
    }
}
