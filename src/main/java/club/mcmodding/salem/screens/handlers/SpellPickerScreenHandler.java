package club.mcmodding.salem.screens.handlers;

import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronInventory;
import club.mcmodding.salem.screens.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class SpellPickerScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;

    public SpellPickerScreenHandler(int syncID, PlayerInventory playerInventory) {
        this(syncID, playerInventory, null);
    }

    public SpellPickerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Screens.SPELL_PICKER_SCREEN, syncId);
        this.context = context;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
