package club.mcmodding.salem.items;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import club.mcmodding.salem.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class SpellCasterUtil {

    /** Add the {@link Spell} to the spells that the spell casting item stack contains and are available for use. */
    public static void addSpell(ItemStack stack, Spell spell) {
        ((ListTag) getOrCreateTag(stack).get("spell_list")).add(StringTag.of(SpellRegistry.SPELL.getId(spell).toString()));
    }

    /**
     * Remove the {@link Spell} from available spells.
     * @return Whether the spell was removed.
     */
    public static boolean removeSpell(ItemStack stack, Spell spell) {
        try {
            return ((ListTag) getOrCreateTag(stack).get("spell_list")).remove(StringTag.of(SpellRegistry.SPELL.getId(spell).toString()));
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Remove the {@link Spell} at position {@code index} from available spells.
     * @return Whether the spell was removed.
     */
    public static boolean removeSpell(ItemStack stack, int index) {
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
    public static boolean removeEnergy(ItemStack stack, float energy) {
        SpellCaster spellCaster;
        if (stack.getItem() instanceof SpellCaster) spellCaster = (SpellCaster) stack.getItem();
        else return false;

        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (currentEnergy >= energy) {
            tag.putFloat("energy", Util.clamp(currentEnergy - energy, 0f, spellCaster.getEnergyCapacity()));
            return true;
        }

        return false;
    }

    /**
     * Adds {@code energy} to the spell casting item stack's current energy.
     * @param simulate Whether to simulate adding it in order to view the return value.
     * @return How much energy could not be added due to the energy capacity.
     */
    public static float addEnergy(ItemStack stack, float energy, boolean simulate) {
        SpellCaster spellCaster;
        if (stack.getItem() instanceof SpellCaster) spellCaster = (SpellCaster) stack.getItem();
        else return energy;

        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (!simulate) tag.putFloat("energy", Util.clamp(currentEnergy + energy, 0f, spellCaster.getEnergyCapacity()));

        return Math.max(spellCaster.getEnergyCapacity() - currentEnergy - energy, 0f);
    }

    /** Get the {@code stack}'s compound nbt tag if present, otherwise create and fill one. */
    public static CompoundTag getOrCreateTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) return tag;
        tag = new CompoundTag();

        tag.put("spell_list", new ListTag());
        tag.putInt("selected_spell_index", 0);
        tag.putFloat("energy", 0f);

        return tag;
    }

}
