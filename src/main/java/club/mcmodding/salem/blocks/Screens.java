package club.mcmodding.salem.blocks;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronScreen;
import club.mcmodding.salem.blocks.spell_cauldron.SpellCauldronScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Screens {

    public static final ScreenHandlerType<SpellCauldronScreenHandler> SPELL_CAULDRON_SCREEN = registerScreen(SpellCauldronScreenHandler::new, "spell_cauldron");

    @Environment(EnvType.CLIENT)
    public static void registerScreensClient() {
        ScreenRegistry.register(SPELL_CAULDRON_SCREEN, SpellCauldronScreen::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreen(SimpleClientHandlerFactory<T> screenHandlerType, String name) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(Salem.MOD_ID, name), screenHandlerType);
    }

    public static void init() {}

}
