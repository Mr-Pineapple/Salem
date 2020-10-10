package club.mcmodding.salem.screens;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.screens.handlers.SpellCauldronScreenHandler;
import club.mcmodding.salem.screens.handlers.SpellPickerScreenHandler;
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
    public static final ScreenHandlerType<SpellPickerScreenHandler> SPELL_PICKER_SCREEN = registerScreen(SpellPickerScreenHandler::new, "spell_picker");

    @Environment(EnvType.CLIENT)
    public static void registerScreensClient() {
        ScreenRegistry.register(SPELL_CAULDRON_SCREEN, SpellCauldronScreen::new);
        ScreenRegistry.register(SPELL_PICKER_SCREEN, SpellPickerScreen::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreen(SimpleClientHandlerFactory<T> screenHandlerType, String name) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(Salem.MOD_ID, name), screenHandlerType);
    }

    public static void init() {}

}
