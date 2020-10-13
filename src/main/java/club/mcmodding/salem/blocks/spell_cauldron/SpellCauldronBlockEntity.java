package club.mcmodding.salem.blocks.spell_cauldron;

import club.mcmodding.salem.blocks.BlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SpellCauldronBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private SpellCauldronInventory inventory = new SpellCauldronInventory();

    public SpellCauldronBlockEntity() {
        super(BlockEntities.SPELL_CAULDRON_BLOCK_ENTITY);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.salem.spell_cauldron");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SpellCauldronScreenHandler(syncId, inv, inventory);
    }
}
