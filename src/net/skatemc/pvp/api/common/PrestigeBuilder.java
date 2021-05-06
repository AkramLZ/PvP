package net.skatemc.pvp.api.common;

import net.skatemc.pvp.api.player.Prestige;

public class PrestigeBuilder {

    private int requiredPoints = 0;
    private String name, displayName;

    public PrestigeBuilder(String name) {
        this.name = name;
    }

    public PrestigeBuilder setRequiredPoints(int points) {
        requiredPoints = points;
        return this;
    }

    public PrestigeBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }


    public Prestige build() {
        if (name == null || displayName == null) {
            throw new NullPointerException("Some variables are not initialized");
        }
        return new Prestige() {

            @Override
            public int getRequiredPoints() {
                return requiredPoints;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDisplayName() {
                return displayName;
            }
        };
    }

}