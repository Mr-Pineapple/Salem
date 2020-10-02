package club.mcmodding.salem;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

/**
 * A spell casting item is any item that has the ability to hold and execute {@link Spell}s.  They have a radial menu to select
 * {@link Spell}s, a certain amount of 'energy', and a spell capacity and energy capacity.  Custom spell casting items can contain
 * custom logic to determine how much power to give each {@link Spell} based on the stats given to the particular spell casting item.
 */
public class SpellCastingItem extends Item {

    private int capacity = 8;
    private float energyCapacity = 1000f;

    public SpellCastingItem(Settings settings) {
        super(settings);
    }

    /** Set the spell capacity of this spell casting item. */
    public SpellCastingItem setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    /** Set the magical energy capacity of this spell casting item. */
    public SpellCastingItem setEnergyCapacity(float energyCapacity) {
        this.energyCapacity = energyCapacity;
        return this;
    }

    /** Spell casting capacity */
    public int getCapacity() {
        return capacity;
    }

    public float getEnergyCapacity() {
        return energyCapacity;
    }

    /** Add the {@link Spell} to the spells that the spell casting item stack contains and are available for use. */
    public void addSpell(ItemStack stack, Spell spell) {
        ((ListTag) getOrCreateTag(stack).get("spell_list")).add(spell.serialize());
    }

    /**
     * Remove the {@link Spell} from available spells.
     * @return Whether the spell was removed.
     */
    public boolean removeSpell(ItemStack stack, Spell spell) {
        try {
            return ((ListTag) getOrCreateTag(stack).get("spell_list")).remove(spell.serialize());
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Remove the {@link Spell} at position {@code index} from available spells.
     * @return Whether the spell was removed.
     */
    public boolean removeSpell(ItemStack stack, int index) {
        try {
            return ((ListTag) getOrCreateTag(stack).get("spell_list")).remove(index) != null;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Removes {@code energy} amount of energy from the {@code stack}.
     * @return Whether the spell casting item stack had enough energy and was able to remove {@code energy} amount of energy.
     */
    public boolean removeEnergy(ItemStack stack, float energy) {
        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (currentEnergy >= energy) {
            tag.putFloat("energy", Util.clamp(currentEnergy - energy, 0f, energyCapacity));
            return true;
        }

        return false;
    }

    /**
     * Adds {@code energy} to the spell casting item stack's current energy.
     * @param simulate Whether to simulate adding it in order to view the return value.
     * @return How much energy could not be added due to the energy capacity.
     */
    public float addEnergy(ItemStack stack, float energy, boolean simulate) {
        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (!simulate) tag.putFloat("energy", Util.clamp(currentEnergy + energy, 0f, energyCapacity));

        return Math.max(energyCapacity - currentEnergy - energy, 0f);
    }

    /** Get the {@code stack}'s compound nbt tag if present, otherwise create and fill one. */
    public CompoundTag getOrCreateTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) return tag;
        tag = new CompoundTag();

        tag.put("spell_list", new ListTag());
        tag.putInt("selected_spell_index", 0);
        tag.putFloat("energy", 0f);

        return tag;
    }

}
