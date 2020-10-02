package club.mcmodding.salem.spells;

import club.mcmodding.salem.Salem;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.registry.*;

import java.util.function.Supplier;

public class SpellRegistry {

    public static final SimpleRegistry<Spell> SPELL_TYPE = FabricRegistryBuilder.createSimple(Spell.class, new Identifier(Salem.MOD_ID, "spell_type")).buildAndRegister();

    static {
        Registry.register(SPELL_TYPE, new Identifier(Salem.MOD_ID, "my_spell"), new Spell() {
            @Override
            public boolean execute(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power) {
                return false;
            }

            @Override
            public float simulateEnergyUsage(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power, float baselineEnergyUse) {
                return 0;
            }

            @Override
            public SpellType getSpellType() {
                return SpellType.FIRE;
            }

            @Override
            public CompoundTag serialize() {
                return null;
            }

            @Override
            public void deserialize(CompoundTag tag) {

            }
        });
    }

}
