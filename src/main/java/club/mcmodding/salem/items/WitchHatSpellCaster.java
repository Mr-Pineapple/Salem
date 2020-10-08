package club.mcmodding.salem.items;

import club.mcmodding.salem.spells.Spell;
import club.mcmodding.salem.spells.SpellRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class WitchHatSpellCaster extends Item implements SpellCaster {

    public WitchHatSpellCaster(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        execute(user, user.raycast(32d, 1f, false), user.getStackInHand(hand));

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        float energy = SpellCasterUtil.getEnergy(stack);
        int progress = (int) ((250f / getEnergyCapacity()) * 10);

        StringBuilder energyText = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i < progress) energyText.append("▓");
            else energyText.append("░");
        }

        tooltip.add(new TranslatableText("tooltip.salem.spell_caster.energy").setStyle(Style.EMPTY.withColor(Formatting.RED)));
        tooltip.add(new LiteralText(energyText.toString() + " (" + (int) 250f + "/" + (int) getEnergyCapacity() + ")").setStyle(Style.EMPTY.withColor(Formatting.DARK_RED)));
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

}
