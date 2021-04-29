package net.skatemc.pvp.data;

import net.skatemc.pvp.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class KitSave {

    private SQLite con;

    public KitSave(SQLite con) {
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
        InventoryView inv = player.getOpenInventory();
        if (inv == null)
            return;
        if (!inv.getTitle().equalsIgnoreCase("Kit Saver"))
            return;
        int sword = 0, rod = 0, bow = 0, arrows = 0;
        for (int slot = 0; slot < 9; slot++) {
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

}
