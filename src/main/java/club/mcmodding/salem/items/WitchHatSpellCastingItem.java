package club.mcmodding.salem.items;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WitchHatSpellCastingItem extends SpellCastingItem {

    public WitchHatSpellCastingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        execute(user, user.raycast(32d, 1f, false), user.getStackInHand(hand));

        return super.use(world, user, hand);
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
