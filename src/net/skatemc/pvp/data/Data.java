package net.skatemc.pvp.data;

import net.skatemc.pvp.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Data {

    private SQLite con;

    public Data(SQLite con) {
        this.con = con;
        try {
            PreparedStatement st = con.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pvp_data "
                    + "(UUID VARCHAR(100), Kills BIGINT(100), Deaths BIGINT(100), Points BIGINT(100), Souls BIGINT(100))");
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Cannot create data table: " + e.getMessage());
        }
    }

    public boolean registered(UUID uuid) {
        boolean registered = false;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_data WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (result.getString("UUID") != null) {
                    registered = true;
                }
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registered;
    }

    public void register(UUID uuid) {
        try {
            PreparedStatement statement = con.getConnection().prepareStatement(
                    "INSERT INTO pvp_data (UUID, Kills, Deaths, Points, Souls) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, uuid.toString());
            statement.setLong(2, 0L);
            statement.setLong(3, 0L);
            statement.setLong(4, 1000L);
            statement.setLong(5, 100L);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getKills(UUID uuid) {
        long x = -1L;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_data WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next())
                x = result.getLong("Kills");
            result.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return x;
    }

    public long getDeaths(UUID uuid) {
        long x = -1L;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_data WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next())
                x = result.getLong("Deaths");
            result.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return x;
    }

    public long getPoints(UUID uuid) {
        long x = -1L;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_data WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next())
                x = result.getLong("Points");
            result.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return x;
    }

    public long getSouls(UUID uuid) {
        long x = -1L;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_data WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next())
                x = result.getLong("Souls");
            result.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return x;
    }

    public void setKills(UUID uuid, long x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_data SET Kills=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setDeaths(UUID uuid, long x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_data SET Deaths=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setPoints(UUID uuid, long x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_data SET Points=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setSouls(UUID uuid, long x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_data SET Souls=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
