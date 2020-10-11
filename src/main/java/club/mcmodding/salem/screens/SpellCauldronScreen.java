package club.mcmodding.salem.screens;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.screens.handlers.SpellCauldronScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class SpellCauldronScreen extends HandledScreen<SpellCauldronScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Salem.MOD_ID, "textures/gui/spell_cauldron.png");

    private int spellCastingTitleX = 0;
    private final Text spellCastingTitle = new TranslatableText("gui.salem.spell_cauldron.spell_casting");

    private final Supplier<Integer> x = () -> (width - backgroundWidth) / 2;
    private final Supplier<Integer> y = () -> (height - backgroundHeight) / 2;

    public SpellCauldronScreen(SpellCauldronScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        backgroundWidth = 270;
        backgroundHeight = 174;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        textRenderer.draw(matrices, title, titleX, titleY, 4210752);
        textRenderer.draw(matrices, spellCastingTitle, spellCastingTitleX, titleY, 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        client.getTextureManager().bindTexture(TEXTURE);

        drawTexture(matrices, x.get(), y.get(), getZOffset(), 0, 0, backgroundWidth, backgroundHeight, 256, 512);

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                if ((j * 5) + i < handler.getEffectiveSize()) drawTexture(matrices, x.get() + 7 + 18 * i, y.get() + 17 + 18 * j, getZOffset(), 302, 0, 18, 18, 256, 512);
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawBackground(matrices, delta, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);

        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        titleX = 102 + (162 - textRenderer.getWidth(title)) / 2;
        spellCastingTitleX = 8 + (90 - textRenderer.getWidth(spellCastingTitle)) / 2;
        System.out.println((162 - textRenderer.getWidth(title)) / 2);
        System.out.println((90 - textRenderer.getWidth(spellCastingTitle)) / 2);
    }

}
