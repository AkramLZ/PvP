package net.skatemc.pvp.kits;

import net.skatemc.lib.utils.ItemStackBuilder;
import net.skatemc.pvp.api.PvAPI;
import net.skatemc.pvp.api.common.IKit;
import net.skatemc.pvp.api.player.PVPlayer;
import net.skatemc.pvp.data.save.PVSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

    public static final IKit MEMBER = new a.MEMBER();
    public static final IKit YOUTUBER = new a.YOUTUBER();
    public static final IKit GOLD = new a.GOLD();
    public static final IKit DIAMOND = new a.DIAMOND();
    public static final IKit EMERALD = new a.EMERALD();

    private static class a {
        public static class MEMBER implements IKit {

            @Override
            public ItemStack[] getContents(Player player) {
                ItemStack[] arrayStack = new ItemStack[9];
                PVPlayer pvp = PvAPI.get().getPlayerManager().getPlayer(player);
                int swordSlot = pvp.getSlot(PVSlot.SWORD), rodSlot = pvp.getSlot(PVSlot.ROD),
                        bowSlot = pvp.getSlot(PVSlot.BOW), arrowSlot = pvp.getSlot(PVSlot.ARROW);
                arrayStack[swordSlot] = new ItemStackBuilder(Material.STONE_SWORD).infinite().build();
                arrayStack[bowSlot] = new ItemStackBuilder(Material.BOW).infinite().build();
                arrayStack[rodSlot] = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
                arrayStack[arrowSlot] = new ItemStackBuilder(Material.ARROW).setAmount(8).build();
                return arrayStack;
            }

            @Override
            public ItemStack[] getArmors(Player player) {
                return new ItemStack[]{new ItemStackBuilder(Material.CHAINMAIL_BOOTS).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_LEGGINGS).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_CHESTPLATE).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_HELMET).infinite().build()};
            }

            @Override
            public String getName() {
                return "YouTuber";
            }

        }

        public static class YOUTUBER implements IKit {

            @Override
            public ItemStack[] getContents(Player player) {
                ItemStack[] arrayStack = new ItemStack[9];
                PVPlayer pvp = PvAPI.get().getPlayerManager().getPlayer(player);
                int swordSlot = pvp.getSlot(PVSlot.SWORD), rodSlot = pvp.getSlot(PVSlot.ROD),
                        bowSlot = pvp.getSlot(PVSlot.BOW), arrowSlot = pvp.getSlot(PVSlot.ARROW);
                arrayStack[swordSlot] = new ItemStackBuilder(Material.STONE_SWORD).infinite().build();
                arrayStack[bowSlot] = new ItemStackBuilder(Material.BOW).infinite().build();
                arrayStack[rodSlot] = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
                arrayStack[arrowSlot] = new ItemStackBuilder(Material.ARROW).setAmount(10).build();
                return arrayStack;
            }

            @Override
            public ItemStack[] getArmors(Player player) {
                return new ItemStack[]{new ItemStackBuilder(Material.IRON_BOOTS).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_LEGGINGS).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_CHESTPLATE).infinite().build(),
                        new ItemStackBuilder(Material.IRON_HELMET).infinite().build()};
            }

            @Override
            public String getName() {
                return "Member";
            }

        }

        public static class GOLD implements IKit {

            @Override
            public ItemStack[] getContents(Player player) {
                ItemStack[] arrayStack = new ItemStack[9];
                PVPlayer pvp = PvAPI.get().getPlayerManager().getPlayer(player);
                int swordSlot = pvp.getSlot(PVSlot.SWORD), rodSlot = pvp.getSlot(PVSlot.ROD),
                        bowSlot = pvp.getSlot(PVSlot.BOW), arrowSlot = pvp.getSlot(PVSlot.ARROW);
                arrayStack[swordSlot] = new ItemStackBuilder(Material.STONE_SWORD).infinite().build();
                arrayStack[bowSlot] = new ItemStackBuilder(Material.BOW).infinite().build();
                arrayStack[rodSlot] = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
                arrayStack[arrowSlot] = new ItemStackBuilder(Material.ARROW).setAmount(12).build();
                return arrayStack;
            }

            @Override
            public ItemStack[] getArmors(Player player) {
                return new ItemStack[]{new ItemStackBuilder(Material.CHAINMAIL_BOOTS).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_LEGGINGS).infinite().build(),
                        new ItemStackBuilder(Material.IRON_CHESTPLATE).infinite().build(),
                        new ItemStackBuilder(Material.IRON_HELMET).infinite().build()};
            }

            @Override
            public String getName() {
                return "Gold";
            }

        }

        public static class DIAMOND implements IKit {

            @Override
            public ItemStack[] getContents(Player player) {
                ItemStack[] arrayStack = new ItemStack[9];
                PVPlayer pvp = PvAPI.get().getPlayerManager().getPlayer(player);
                int swordSlot = pvp.getSlot(PVSlot.SWORD), rodSlot = pvp.getSlot(PVSlot.ROD),
                        bowSlot = pvp.getSlot(PVSlot.BOW), arrowSlot = pvp.getSlot(PVSlot.ARROW);
                arrayStack[swordSlot] = new ItemStackBuilder(Material.STONE_SWORD).infinite().build();
                arrayStack[bowSlot] = new ItemStackBuilder(Material.BOW).infinite().build();
                arrayStack[rodSlot] = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
                arrayStack[arrowSlot] = new ItemStackBuilder(Material.ARROW).setAmount(15).build();
                return arrayStack;
            }

            @Override
            public ItemStack[] getArmors(Player player) {
                return new ItemStack[]{new ItemStackBuilder(Material.CHAINMAIL_BOOTS).infinite().build(),
                        new ItemStackBuilder(Material.IRON_LEGGINGS).infinite().build(),
                        new ItemStackBuilder(Material.IRON_CHESTPLATE).infinite().build(),
                        new ItemStackBuilder(Material.CHAINMAIL_HELMET).infinite().build()};
            }

            @Override
            public String getName() {
                return "Diamond";
            }

        }

        public static class EMERALD implements IKit {

            @Override
            public ItemStack[] getContents(Player player) {
                ItemStack[] arrayStack = new ItemStack[9];
                PVPlayer pvp = PvAPI.get().getPlayerManager().getPlayer(player);
                int swordSlot = pvp.getSlot(PVSlot.SWORD), rodSlot = pvp.getSlot(PVSlot.ROD),
                        bowSlot = pvp.getSlot(PVSlot.BOW), arrowSlot = pvp.getSlot(PVSlot.ARROW);
                arrayStack[swordSlot] = new ItemStackBuilder(Material.STONE_SWORD).infinite().build();
                arrayStack[bowSlot] = new ItemStackBuilder(Material.BOW).infinite().build();
                arrayStack[rodSlot] = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
                arrayStack[arrowSlot] = new ItemStackBuilder(Material.ARROW).setAmount(22).build();
                return arrayStack;
            }

            @Override
            public ItemStack[] getArmors(Player player) {
                return new ItemStack[]{new ItemStackBuilder(Material.IRON_BOOTS).infinite().build(),
                        new ItemStackBuilder(Material.IRON_LEGGINGS).infinite().build(),
                        new ItemStackBuilder(Material.IRON_CHESTPLATE).infinite().build(),
                        new ItemStackBuilder(Material.IRON_HELMET).infinite().build()};
            }

            @Override
            public String getName() {
                return "Emerald";
            }

        }
    }

}
