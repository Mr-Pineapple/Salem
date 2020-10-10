package club.mcmodding.salem.screens.handlers;

import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronInventory;
import club.mcmodding.salem.screens.Screens;
import club.mcmodding.salem.items.SpellCaster;
import club.mcmodding.salem.items.SpellCasterInventory;
import club.mcmodding.salem.items.SpellCasterUtil;
import club.mcmodding.salem.util.HideableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SpellCauldronScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final SpellCasterInventory spellCasterInventory = new SpellCasterInventory();

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

        addSlot(new Slot(inventory, 0, 44, 138) {
            @Override
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                if (stack.getItem() instanceof SpellCaster) {
                    SpellCasterUtil.getOrPopulateTag(stack).put("inventory", spellCasterInventory.serialize());
                    spellCasterInventory.clear();
                }

                return super.onTakeItem(player, stack);
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                if (stack.getItem() instanceof SpellCaster) {
                    if (SpellCasterUtil.getOrPopulateTag(stack).get("inventory") != null) {
                        spellCasterInventory.deserialize(SpellCasterUtil.getOrPopulateTag(stack).getCompound("inventory"));
                    }
                }
            }

            @Override
            public void onStackChanged(ItemStack originalItem, ItemStack itemStack) {
                super.onStackChanged(originalItem, itemStack);

                if (itemStack.getItem() instanceof SpellCaster) {
                    if (SpellCasterUtil.getOrPopulateTag(itemStack).get("inventory") != null) {
                        spellCasterInventory.deserialize(SpellCasterUtil.getOrPopulateTag(itemStack).getCompound("inventory"));
                    }
                }
            }
        });
        addSlot(new Slot(inventory, 1, 174, 44));

        for (int i = 0; i < 9; i++) addSlot(new Slot(inventory, i + 2 , 102 + 18 * i, 18));
        for (int i = 0; i < 9; i++) addSlot(new Slot(inventory, i + 11, 102 + 18 * i, 70));

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                addSlot(new HideableSlot(spellCasterInventory, (j * 5) + i, 8 + 18 * i, 18 + 18 * j));
            }
        }

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

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
    }

}
