package club.mcmodding.salem.items.spell_caster;

import club.mcmodding.salem.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

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
