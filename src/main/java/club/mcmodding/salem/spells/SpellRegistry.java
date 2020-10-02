package club.mcmodding.salem.spells;

import club.mcmodding.salem.Salem;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public class SpellRegistry {

    public static final SimpleRegistry<Spell> SPELL = FabricRegistryBuilder.createSimple(Spell.class, new Identifier(Salem.MOD_ID, "spells")).buildAndRegister();

    public static final Spell FIREBALL_SPELL = registerSpell(new Spell() {
        @Override
        public boolean execute(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power) {
            Vec3d vector = entity.getRotationVector();
            FireballEntity fireballEntity = new FireballEntity(entity.world, entity.getX(), entity.getEyeY(), entity.getZ(), vector.x, vector.y, vector.z);
            fireballEntity.explosionPower = (int) power;
            entity.world.spawnEntity(fireballEntity);

            return true;
        }

        @Override
        public float simulateEnergyUsage(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power, float baselineEnergyUse) {
            return (power / 4.5f) - 0.5f;
        }

        @Override
        public float getBaselineEnergyUse() {
            return 2f;
        }

        @Override
        public SpellType getSpellType() {
            return SpellType.FIRE;
        }
    }, "fireball_spell");

    private static Spell registerSpell(Spell spell, String name) {
        return Registry.register(SpellRegistry.SPELL, new Identifier(Salem.MOD_ID, name), spell);
    }

    public static void init() {}

}
