package club.mcmodding.salem.items;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import club.mcmodding.salem.util.Util;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;

/**
 * A spell casting item is any item that has the ability to hold and execute {@link Spell}s.  They have a radial menu to select
 * {@link Spell}s, a certain amount of 'energy', and a spell capacity and energy capacity.  Custom spell casting items can contain
 * custom logic to determine how much power to give each {@link Spell} based on the stats given to the particular spell casting item.
 */
public interface SpellCaster {

    /** Spell casting capacity */
    int getCapacity();
    float getEnergyCapacity();

    /** Add the {@link Spell} to the spells that the spell casting item stack contains and are available for use. */
    default void addSpell(ItemStack stack, Spell spell) {
        ((ListTag) getOrCreateTag(stack).get("spell_list")).add(StringTag.of(SpellRegistry.SPELL.getId(spell).toString()));
    }

    /**
     * Remove the {@link Spell} from available spells.
     * @return Whether the spell was removed.
     */
    default boolean removeSpell(ItemStack stack, Spell spell) {
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
    default boolean removeSpell(ItemStack stack, int index) {
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
    default boolean removeEnergy(ItemStack stack, float energy) {
        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (currentEnergy >= energy) {
            tag.putFloat("energy", Util.clamp(currentEnergy - energy, 0f, getEnergyCapacity()));
            return true;
        }

        return false;
    }

    /**
     * Adds {@code energy} to the spell casting item stack's current energy.
     * @param simulate Whether to simulate adding it in order to view the return value.
     * @return How much energy could not be added due to the energy capacity.
     */
    default float addEnergy(ItemStack stack, float energy, boolean simulate) {
        CompoundTag tag = getOrCreateTag(stack);
        float currentEnergy = tag.getFloat("energy");

        if (!simulate) tag.putFloat("energy", Util.clamp(currentEnergy + energy, 0f, getEnergyCapacity()));

        return Math.max(getEnergyCapacity() - currentEnergy - energy, 0f);
    }

    /** If the {@link Spell} is usable by this particular spell casting item. */
    boolean canUse(Spell spell);

    /**  Calculate the amount of power given to the {@link Spell}.  Minimum is {@code 1f}, normal is {@code} 5f, maximum is {@code 9f}.*/
    float getPower(ItemStack stack, Spell spell);

    /**
     * Executes the selected {@link Spell}'s {@code execute} method with {@link SpellCaster#getPower(ItemStack, Spell)} power.
     * @param entity The entity who casted the {@link Spell}.
     * @param raycastHitResult The {@link HitResult} of a raycast from the {@code entity}.
     * @param spellCastingItem The {@link ItemStack} used to cast this {@link Spell}.
     */
    default void execute(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem) {
        CompoundTag tag = getOrCreateTag(spellCastingItem);
        ListTag listTag = (ListTag) tag.get("spell_list");

        Spell spell = null;
        if (listTag != null && listTag.size() > 0) spell = SpellRegistry.SPELL.get(new Identifier(listTag.getString(tag.getInt("selected_spell_index"))));

        if (spell != null && canUse(spell) && spell.execute(entity, raycastHitResult, spellCastingItem, Util.clamp(getPower(spellCastingItem, spell), 1f, 9f))) {
            removeEnergy(spellCastingItem, spell.simulateEnergyUsage(entity, raycastHitResult, spellCastingItem,
                    Util.clamp(getPower(spellCastingItem, spell), 1f, 9f), spell.getBaselineEnergyUse()));
        }
    }

    /** Get the {@code stack}'s compound nbt tag if present, otherwise create and fill one. */
    static CompoundTag getOrCreateTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) return tag;
        tag = new CompoundTag();

        tag.put("spell_list", new ListTag());
        tag.putInt("selected_spell_index", 0);
        tag.putFloat("energy", 0f);

        return tag;
    }

}
