package club.mcmodding.salem.items;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WitchHatSpellCaster extends Item implements SpellCaster {

    public WitchHatSpellCaster(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        execute(user, user.raycast(32d, 1f, false), user.getStackInHand(hand));

        return super.use(world, user, hand);
    }

    @Override
    public int getCapacity() {
        return 8;
    }

    @Override
    public float getEnergyCapacity() {
        return 1000f;
    }

    @Override
    public boolean canUse(Spell spell) {
        return true;
    }

    @Override
    public float getPower(ItemStack stack, Spell spell) {
        return 5f;
    }

    @Override
    public CompoundTag getOrCreateTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) return tag;
        tag = new CompoundTag();

        tag.put("spell_list", new ListTag());
        tag.putInt("selected_spell_index", 0);
        tag.putFloat("energy", 0f);

        ((ListTag) tag.get("spell_list")).add(StringTag.of(SpellRegistry.SPELL.getId(SpellRegistry.FIREBALL_SPELL).toString()));

        return tag;

    }

}
