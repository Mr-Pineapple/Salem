package club.mcmodding.salem.blocks;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldron;
import club.mcmodding.salem.items.SalemItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SalemBlocks {

    public static final Block SPELL_CAULDRON = registerBlockGeneral(new SpellCauldron(), "spell_cauldron");

    private static Block registerBlockGeneral(Block block, String name) {
        Registry.register(Registry.ITEM, new Identifier(Salem.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(SalemItems.SALEM_GROUP)));
        return Registry.register(Registry.BLOCK, new Identifier(Salem.MOD_ID, name), block);
    }

    private static Block registerBlockGeneral(Block block, String name, ItemGroup group) {
        Registry.register(Registry.ITEM, new Identifier(Salem.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
        return Registry.register(Registry.BLOCK, new Identifier(Salem.MOD_ID, name), block);
    }

    public static void init() {}

}
