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
        CompoundTag tag = SpellCasterUtil.getOrCreateTag(spellCastingItem);
        ListTag listTag = (ListTag) tag.get("spell_list");

        Spell spell = null;
        if (listTag != null && listTag.size() > 0) spell = SpellRegistry.SPELL.get(new Identifier(listTag.getString(tag.getInt("selected_spell_index"))));

        if (spell != null && canUse(spell) && spell.execute(entity, raycastHitResult, spellCastingItem, Util.clamp(getPower(spellCastingItem, spell), 1f, 9f))) {
            SpellCasterUtil.removeEnergy(spellCastingItem, spell.simulateEnergyUsage(entity, raycastHitResult, spellCastingItem,
                    Util.clamp(getPower(spellCastingItem, spell), 1f, 9f), spell.getBaselineEnergyUse()));
        }
    }

}
