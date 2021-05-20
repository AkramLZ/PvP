package net.skatemc.pvp.data.impl;

import java.sql.Connection;

public abstract class SQLConnection {

    public abstract Connection getConnection();

    public abstract boolean connect();

}
