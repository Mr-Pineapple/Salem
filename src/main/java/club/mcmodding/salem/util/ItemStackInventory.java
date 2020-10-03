package club.mcmodding.salem.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public interface ItemStackInventory {

    /**
     * Returns a COPY of the {@link ItemStack} at the position.  Please make changes to the slot by changing the copy then setting that as the stack with {@link ItemStackInventory#setStack(int, ItemStack)}.
     * @return A copy of the `ItemStack` at this position.
     */
    ItemStack getStackCopy(int id);
    void setStack(int id, ItemStack stack);

    /**
     * Attempt to add a {@code stack} to the inventory.
     * @param simulate Whether to actually insert the stack, useful for testing if there's enough space.
     * @return How much couldn't be added.
     */
    ItemStack addStack(ItemStack stack, boolean simulate);
    /**
     * Attempt to remove a {@code stack} from the inventory.
     * @param simulate Whether to actually remove the stack, useful for testing if there's enough of the item.
     * @return How much couldn't be removed.
     */
    ItemStack removeStack(ItemStack stack, boolean simulate);

    /**
     * Attempt to add a {@code stack} to the specified slot.
     * @param simulate Whether to actually insert the stack, useful for testing if there's enough space.
     * @return How much couldn't be added.
     */
    ItemStack insertStack(int id, ItemStack stack, boolean simulate);
    /**
     * Attempt to remove a {@code stack} from the specified slot.
     * @param simulate Whether to actually remove the stack, useful for testing if there's enough of the item.
     * @return How much couldn't be removed.
     */
    ItemStack extractStack(int id, ItemStack stack, boolean simulate);

    CompoundTag serialize();
    void deserialize(CompoundTag tag);

    /** Does the {@code stack} belong in this {@code slot}?  For example, the furnace only allows fuels into the fuel slot. */
    boolean isStackValid(int slot, ItemStack stack);

    int size();
    boolean isEmpty();
    void clear();

    default void onContentsChanged(int id) {}

}
