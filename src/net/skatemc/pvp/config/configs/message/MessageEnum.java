package net.skatemc.pvp.config.configs.message;

public enum MessageEnum {

    PRESTIGE_UP("messages.prestige-up"), PRESTIGE_DOWN("messages.prestige-down"),
    JOIN_MESSAGE("messages.join-message"), QUIT_MESSAGE("messages.quit-message");

    private String path;

    private MessageEnum(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

}
