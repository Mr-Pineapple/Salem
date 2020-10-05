package club.mcmodding.salem.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class HideableSlot extends Slot {

    private boolean shown = true;

    public HideableSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public HideableSlot(Inventory inventory, int index, int x, int y, boolean shown) {
        this(inventory, index, x, y);

        this.shown = shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public void toggleHidden() {
        this.shown = !shown;
    }

    @Override
    public boolean doDrawHoveringEffect() {
        return shown && super.doDrawHoveringEffect();
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return shown && super.canInsert(stack);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return shown && super.canTakeItems(playerEntity);
    }

}
