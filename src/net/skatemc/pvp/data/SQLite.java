package net.skatemc.pvp.data;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.data.impl.SQLConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQLConnection {

    private Connection con;
    private String path;

    public SQLite(String path) { this.path = path; }

    @Override
    public boolean connect() {
        File dataFolder = new File(path);
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                Main.getInstance().getLogger().severe("Failed to create stats.db: " + e.getMessage());
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            this.con = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            Main.getInstance().getLogger().info("SQLite connected.");
            return true;
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Failed to connect with SQLite: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            Main.getInstance().getLogger().severe("SQLite library not found!");
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return con;
    }

}
