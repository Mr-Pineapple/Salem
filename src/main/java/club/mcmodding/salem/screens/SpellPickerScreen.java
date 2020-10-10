package club.mcmodding.salem.screens;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.screens.handlers.SpellPickerScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

/*
 * TODO:
 *      Add Spell Selections
 *      Add Tooltips
 */

public class SpellPickerScreen extends HandledScreen<SpellPickerScreenHandler> {

    public static final Text spellSelectionTitle = new TranslatableText("gui.salem.spell_selection");
    private static final Identifier TEXTURE = new Identifier(Salem.MOD_ID, "textures/gui/spell_selection.png");

    public SpellPickerScreen(SpellPickerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 256;
        this.backgroundHeight = 256;

    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {

    }

    @Override
    @SuppressWarnings("deprecation")
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int startX = (this.width - this.backgroundWidth) / 2;
        int startY = (this.height - this.backgroundHeight) / 2;

        //GUI Texture
        //TODO: Add other components around the center item
        client.getTextureManager().bindTexture(TEXTURE);
        drawTexture(matrices, startX + 108, startY + 108, 0, 0, 40, 40);

        //The ItemStack the player is currently holding
        ItemStack currentItem = client.player.getMainHandStack();

        //Remder Item on Screen
        RenderSystem.pushMatrix(); {
            RenderSystem.translatef(startX + 129, startY + 135, 100);
            RenderSystem.scalef(60F, -60F, 60F);

            MatrixStack matrixstack = new MatrixStack();
            VertexConsumerProvider.Immediate buffer = this.client.getBufferBuilders().getEntityVertexConsumers();

            MinecraftClient.getInstance().getItemRenderer().renderItem(currentItem, ModelTransformation.Mode.GROUND, false, matrixstack, buffer, 15728880, OverlayTexture.DEFAULT_UV, getModel(currentItem));
            buffer.draw();
        }
        RenderSystem.popMatrix();
    }

    //Method to get the model, can be removed, not entirely needed
    private static BakedModel getModel(ItemStack item) {
        return MinecraftClient.getInstance().getItemRenderer().getModels().getModel(item);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawBackground(matrices, delta, mouseX, mouseY);

        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        System.out.println("OPENED THE SPELL SELECTOR GUI");
    }

}
