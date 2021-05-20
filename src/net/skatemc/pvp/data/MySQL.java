package net.skatemc.pvp.data;

import net.skatemc.pvp.data.impl.SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQLConnection {

    private Connection conn;
    private String host, username, password, database;
    private int port;

    public MySQL(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public boolean connect() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                + this.database, this.username, this.password);
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    @Override public Connection getConnection() {
        return this.conn;
    }

}
