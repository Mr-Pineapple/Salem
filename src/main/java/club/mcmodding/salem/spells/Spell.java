package club.mcmodding.salem.spells;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public interface Spell {

    /**
     * Execute the {@link Spell}.
     * @param entity The entity who casted the {@link Spell}.  Use this to access the {@link World}.
     * @param raycastHitResult The {@link HitResult} of a raycast from the {@code entity}.  Perform {@code instanceof}
     *                         checks and cast to {@link net.minecraft.util.hit.BlockHitResult} or
     *                         {@link net.minecraft.util.hit.EntityHitResult} for more information.
     * @param spellCastingItem The {@link ItemStack} used to cast this {@link Spell}.  May contain useful information
     *                         such as stats.
     * @param power The amount of power given to this {@link Spell} by the spell casting item.  Minimum is {@code 1f},
     *              normal is {@code} 5f, maximum is {@code 9f}.
     * @return Whether the action was successful.  Only if so will energy be drained from the spell casting item.
     */
    boolean execute(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power);

    /**
     * Modify the {@code baselineEnergyUse} (override {@link Spell#getBaselineEnergyUse} to change) to simulate how much
     * energy will be used to execute the {@link Spell}.
     *
     * DO NOT UNDER ANY CIRCUMSTANCES MAKE CHANGES TO THE {@code playerEntity} (or its fields) OR THE {@code spellCastingItem}.
     * @param entity The entity who casted the {@link Spell}.  Use this to access the {@link World}.
     * @param raycastHitResult The {@link HitResult} of a raycast from the {@code entity}.  Perform {@code instanceof}
     *                         checks and cast to {@link net.minecraft.util.hit.BlockHitResult} or
     *                         {@link net.minecraft.util.hit.EntityHitResult} for more information.
     * @param spellCastingItem The {@link ItemStack} used to cast this {@link Spell}.  May contain useful information
     *                         such as stats.
     * @param power The amount of power given to this {@link Spell} by the spell casting item.  Minimum is {@code 1f},
     *              normal is {@code} 5f, maximum is {@code 9f}.
     * @param baselineEnergyUse The baseline energy usage for the {@link Spell} under normal circumstances, with a power
     *                          of {@code 5f}. Override {@link Spell#getBaselineEnergyUse} to change this.
     * @return The amount of energy used by the {@link Spell}.
     */
    float simulateEnergyUsage(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power, float baselineEnergyUse);

    /** The baseline energy usage of the {@link Spell}, under normal conditions, with a {@code power} of {@code 5f}. */
    float getBaselineEnergyUse();

    /**
     * The {@link SpellType} of this spell. May be used in certain spell casting items to determine how much power to
     * give, or whether to execute this {@link Spell} in the first place.  Also used to color the {@link Spell} icon.
     */
    SpellType getSpellType();

}
