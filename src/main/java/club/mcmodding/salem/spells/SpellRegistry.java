package club.mcmodding.salem.spells;

import club.mcmodding.salem.Salem;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public class SpellRegistry {

    public static final SimpleRegistry<Spell> SPELL_TYPE = FabricRegistryBuilder.createSimple(Spell.class, new Identifier(Salem.MOD_ID, "spell_type")).buildAndRegister();

    public static final Spell FIREBALL_SPELL = registerSpell(new Spell() {
        @Override
        public boolean execute(LivingEntity entity, HitResult raycastHitResult, ItemStack spellCastingItem, float power) {
            FireballEntity fireballEntity = new FireballEntity(entity.world, entity, 1f, 1f, 1f);
            fireballEntity.explosionPower = (int) ((power / 4.5f) - 0.5f);
            fireballEntity.updatePosition(entity.getX(), entity.getY(), entity.getZ());
            entity.world.spawnEntity(fireballEntity);

            return true;
        }

        @Override
        public SpellType getSpellType() {
            return SpellType.FIRE;
        }
    }, "fireball_spell");

    private static Spell registerSpell(Spell spell, String name) {
        return Registry.register(SPELL_TYPE, new Identifier(Salem.MOD_ID, name), spell);
    }

}
