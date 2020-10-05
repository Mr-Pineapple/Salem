package club.mcmodding.salem.blocks.spell_cauldron;

import club.mcmodding.salem.blocks.Screens;
import club.mcmodding.salem.util.HideableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class SpellCauldronScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PlayerInventory playerInventory;

    public SpellCauldronScreenHandler(int syncID, PlayerInventory playerInventory) {
        this(syncID, playerInventory, new SpellCauldronInventory());
    }

    public SpellCauldronScreenHandler(int syncID, PlayerInventory playerInventory, Inventory inventory) {
        super(Screens.SPELL_CAULDRON_SCREEN, syncID);
        checkSize(inventory, 20);

        this.inventory = inventory;
        this.playerInventory = playerInventory;
        inventory.onOpen(playerInventory.player);

        addSlot(new Slot(inventory, 0, 44, 138));
        addSlot(new Slot(inventory, 1, 174, 44));

        for (int i = 0; i < 9; i++) addSlot(new HideableSlot(inventory, i + 2 , 102 + 18 * i, 18, false));
        for (int i = 0; i < 9; i++) addSlot(new HideableSlot(inventory, i + 11, 102 + 18 * i, 70, false));

        layoutPlayerInventory(playerInventory, 102, 92);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    private void layoutPlayerInventory(PlayerInventory playerInventory, int x, int y) {
        for (int m = 0; m < 3; ++m) for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l + m * 9 + 9, x + l * 18, y + m * 18));
        }

        for (int m = 0; m < 9; ++m) this.addSlot(new Slot(playerInventory, m, x + m * 18, y + 58));
    }

}
