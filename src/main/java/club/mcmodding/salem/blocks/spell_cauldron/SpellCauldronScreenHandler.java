package club.mcmodding.salem.blocks.spell_cauldron;

import club.mcmodding.salem.items.Amulet;
import club.mcmodding.salem.Screens;
import club.mcmodding.salem.items.spell_caster.SpellCaster;
import club.mcmodding.salem.items.spell_caster.SpellCasterInventory;
import club.mcmodding.salem.items.spell_caster.SpellCasterUtil;
import club.mcmodding.salem.recipes.Recipes;
import club.mcmodding.salem.recipes.SpellRecipe;
import club.mcmodding.salem.util.HideableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpellCauldronScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final SpellCasterInventory spellCasterInventory = new SpellCasterInventory();

    private final PlayerInventory playerInventory;
    private int effectiveSize = 0;

    public SpellCauldronScreenHandler(int syncID, PlayerInventory playerInventory) {
        this(syncID, playerInventory, new SpellCauldronInventory());
    }

    public SpellCauldronScreenHandler(int syncID, PlayerInventory playerInventory, Inventory inventory) {
        super(Screens.SPELL_CAULDRON_SCREEN, syncID);
        checkSize(inventory, 22);

        this.inventory = inventory;
        this.playerInventory = playerInventory;
        inventory.onOpen(playerInventory.player);

        addSlot(new Slot(inventory, 0, 44, 138) {
            @Override
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                onRemove(stack);

                return super.onTakeItem(player, stack);
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                onInsert(stack);
            }

            @Override
            public boolean canInsert(ItemStack stack) {
                return super.canInsert(stack) && stack.getItem() instanceof SpellCaster;
            }
        });
        addSlot(new Slot(inventory, 1, 147, 44) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return super.canInsert(stack) && stack.getItem() instanceof Amulet;
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                updateResult(syncID, playerInventory.player.world, playerInventory.player, (SpellCauldronInventory) inventory);
            }
        });
        addSlot(new Slot(inventory, 2, 174, 44));
        addSlot(new Slot(inventory, 3, 201, 44) {
            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                updateResult(syncID, playerInventory.player.world, playerInventory.player, (SpellCauldronInventory) inventory);
            }
        });

        for (int i = 0; i < 9; i++) addSlot(new Slot(inventory, i + 4 , 102 + 18 * i, 18) {
            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                updateResult(syncID, playerInventory.player.world, playerInventory.player, (SpellCauldronInventory) inventory);
            }
        });
        for (int i = 0; i < 9; i++) addSlot(new Slot(inventory, i + 13, 102 + 18 * i, 70) {
            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);

                updateResult(syncID, playerInventory.player.world, playerInventory.player, (SpellCauldronInventory) inventory);
            }
        });

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                addSlot(new HideableSlot(spellCasterInventory, (j * 5) + i, 8 + 18 * i, 18 + 18 * j) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return super.canInsert(stack) && stack.getItem() instanceof Amulet;
                    }
                });
            }
        }

        layoutPlayerInventory(playerInventory, 102, 92);

        onInsert(inventory.getStack(0));
    }

    private void setEffectiveSize(int size) {
        effectiveSize = size;

        slots.stream().filter(slot -> slot.inventory instanceof SpellCasterInventory).forEach(s -> {
            if (s instanceof HideableSlot) {
                HideableSlot slot = (HideableSlot) s;
                slot.setShown(slot.getIndex() < effectiveSize);
            }
        });
    }

    public int getEffectiveSize() {
        return effectiveSize;
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

    protected void onRemove(ItemStack stack) {
        if (stack.getItem() instanceof SpellCaster)
            SpellCasterUtil.getOrPopulateTag(stack).put("inventory", spellCasterInventory.serialize(effectiveSize));

        spellCasterInventory.clear();
        setEffectiveSize(0);
    }

    protected void onInsert(ItemStack stack) {
        CompoundTag tag = SpellCasterUtil.getOrPopulateTag(stack);

        if (stack.getItem() instanceof SpellCaster) {
            if (SpellCasterUtil.getOrPopulateTag(stack).get("inventory") != null) {
                spellCasterInventory.deserialize(SpellCasterUtil.getOrPopulateTag(stack).getCompound("inventory"));
                setEffectiveSize(tag.contains("inventory") && tag.getCompound("inventory").contains("size") ?
                        tag.getCompound("inventory").getInt("size") : 30);
            }
        }
    }

    @Override
    public void close(PlayerEntity player) {
        onRemove(inventory.getStack(0));

        super.close(player);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
    }

    protected static void updateResult(int syncId, World world, PlayerEntity player, SpellCauldronInventory inventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack itemStack = ItemStack.EMPTY;

            List<SpellRecipe> spellRecipes = world.getServer().getRecipeManager().getAllMatches(Recipes.SPELL_TYPE, inventory, world);
            


            inventory.setStack(2, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, itemStack));
        }
    }

    public ArrayList<SpellRecipe> getRecipes() {
        World world = playerInventory.player.world;
        return playerInventory.player.world == null ? new ArrayList<>(
               playerInventory.player.world.getRecipeManager().getAllMatches(Recipes.SPELL_TYPE, (SpellCauldronInventory) inventory, playerInventory.player.world)
        ) : new ArrayList<>();
    }

}
