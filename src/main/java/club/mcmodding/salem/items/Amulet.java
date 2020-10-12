package club.mcmodding.salem.items;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import club.mcmodding.salem.spells.SpellType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public abstract class Amulet extends Item {

    public Amulet(Settings settings) {
        super(settings);
    }

    public abstract SpellType getSpellType();

    public CompoundTag getOrPopulateTag(ItemStack stack) {
        if (!(stack.getItem() instanceof Amulet && stack.getOrCreateTag().contains("spell"))) return stack.getOrCreateTag();

        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("spell", "salem:empty");

        return tag;
    }

    public void setSpell(ItemStack stack, Spell spell) {
        Identifier identifier = SpellRegistry.SPELL.getKey(spell).isPresent() ? SpellRegistry.SPELL.getKey(spell).get().getValue() : new Identifier(Salem.MOD_ID, "empty");
        getOrPopulateTag(stack).putString("spell", identifier.toString());
    }

    public Spell getSpell(ItemStack stack) {
        return SpellRegistry.SPELL.get(new Identifier(getOrPopulateTag(stack).getString("spell")));
    }

}
