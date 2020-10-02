package club.mcmodding.salem.items;

import club.mcmodding.salem.Salem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class SalemItems {

    public static final Item WITCH_HAT = registerItem(new WitchHatSpellCastingItem(new FabricItemSettings().group(ItemGroup.FOOD)), "witch_hat");

    private static Item registerItem(Item item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(Salem.MOD_ID, name), item);
    }

    private static ItemGroup registerItemGroup(String name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(new Identifier(Salem.MOD_ID, name), icon);
    }

    public static void init() {}

}
