package club.mcmodding.salem.blocks;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class BlockEntities {

    public static BlockEntityType<SpellCauldronBlockEntity> SPELL_CAULDRON_BLOCK_ENTITY = registerBlockEntity(SpellCauldronBlockEntity::new, SalemBlocks.SPELL_CAULDRON, "spell_cauldron");

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(Supplier<T> blockEntitySupplier, Block block, String name) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Salem.MOD_ID, name), BlockEntityType.Builder.create(blockEntitySupplier, block).build(null));
    }

    public static void init() {}

}
