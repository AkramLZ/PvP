package net.skatemc.pvp.data;

import net.skatemc.pvp.Main;
import net.skatemc.pvp.data.impl.SQLConnection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class KitSave {

    private SQLConnection con;

    public KitSave(SQLConnection con) {
        this.con = con;
        try {
            PreparedStatement st = con.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pvp_inventories "
                    + "(UUID VARCHAR(100), Sword INT(100), Rod INT(100), Bow INT(100), Arrow INT(100))");
            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
            Main.getInstance().getLogger().severe("Cannot create inventory table: " + exception.getMessage());
        }
    }

    public boolean saved(UUID uuid) {
        boolean saved = false;
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("SELECT * FROM pvp_inventories WHERE UUID='" + uuid + "'");
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (result.getString("UUID") != null) {
                    saved = true;
                }
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saved;
    }

    public void unsave(UUID uuid) {
        try {
            PreparedStatement st = con.getConnection().prepareStatement("DELETE FROM pvp_inventories WHERE UUID='" + uuid + "'");
            st.executeUpdate();
            st.close();
        } catch (SQLException exception) {
        }
    }

    public void save(Player player) {
        Inventory inv = player.getInventory();
        int sword = 0, rod = 0, bow = 0, arrows = 0;
        for (int slot = 0; slot < inv.getSize(); slot++) {
            if (inv.getItem(slot) != null) {
                if (inv.getItem(slot).getType() == Material.STONE_SWORD) {
                    sword = slot;
                }
                if (inv.getItem(slot).getType() == Material.FISHING_ROD) {
                    rod = slot;
                }
                if (inv.getItem(slot).getType() == Material.BOW) {
                    bow = slot;
                }
                if (inv.getItem(slot).getType() == Material.ARROW) {
                    arrows = slot;
                }
            }
        }
        try {
            PreparedStatement st = con.getConnection().prepareStatement(
                    "INSERT INTO pvp_inventories (UUID, Sword, Rod, Bow, Arrow) " +
                    "VALUES ('" + player.getUniqueId() + "', " + sword + ", " + rod + ", " + bow + ", " + arrows + ")");
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSword(player.getUniqueId(), sword);
        setRod(player.getUniqueId(), rod);
        setBow(player.getUniqueId(), bow);
        setArrow(player.getUniqueId(), arrows);
    }

    protected void setSword(UUID uuid, int x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_inventories SET Sword=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void setRod(UUID uuid, int x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_inventories SET Rod=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void setBow(UUID uuid, int x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_inventories SET Bow=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void setArrow(UUID uuid, int x) {
        try {
            PreparedStatement statement = con.getConnection()
                    .prepareStatement("UPDATE pvp_inventories SET Arrow=" + x + " WHERE UUID='" + uuid + "'");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getSword(UUID uuid) {
        int slot = 0;
        try {
            PreparedStatement statement = con.getConnection().prepareStatement("SELECT * FROM pvp_inventories WHERE UUID='"
                + uuid + "'");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                slot = resultSet.getInt("Sword");
                resultSet.close();
                statement.close();
            }
        } catch (SQLException exception) {
        }
        return slot;
    }

    public int getRod(UUID uuid) {
        int slot = 2;
        try {
            PreparedStatement statement = con.getConnection().prepareStatement("SELECT * FROM pvp_inventories WHERE UUID='"
                    + uuid + "'");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                slot = resultSet.getInt("Rod");
                resultSet.close();
                statement.close();
            }
        } catch (SQLException exception) {
        }
        return slot;
    }

    public int getBow(UUID uuid) {
        int slot = 1;
        try {
            PreparedStatement statement = con.getConnection().prepareStatement("SELECT * FROM pvp_inventories WHERE UUID='"
                    + uuid + "'");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                slot = resultSet.getInt("Bow");
                resultSet.close();
                statement.close();
            }
        } catch (SQLException exception) {
        }
        return slot;
    }

    public int getArrow(UUID uuid) {
        int slot = 8;
        try {
            PreparedStatement statement = con.getConnection().prepareStatement("SELECT * FROM pvp_inventories WHERE UUID='"
                    + uuid + "'");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                slot = resultSet.getInt("Arrow");
                resultSet.close();
                statement.close();
            }
        } catch (SQLException exception) {
        }
        return slot;
    }

}
