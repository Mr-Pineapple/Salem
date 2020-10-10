package club.mcmodding.salem.util;

import club.mcmodding.salem.Salem;
import club.mcmodding.salem.items.SpellCaster;
import club.mcmodding.salem.screens.SpellPickerScreen;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class KeybindUtil {
    public static KeyBinding amulet;

    //Register Keybinds
    private static void registerKeyBinds() {
        amulet = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.amulet.select", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.category.first.test"));
    }

    //What happens to each keybind when clicked
    @SuppressWarnings("deprecation")
    private static void onKeyPress() {
        ClientTickCallback.EVENT.register(client -> {
            MinecraftClient minecraft = MinecraftClient.getInstance();
            if(minecraft.currentScreen instanceof SpellPickerScreen) {
                if(amulet.wasPressed()) {
                    minecraft.player.closeHandledScreen();
                }
            } else if(minecraft.player != null && minecraft.currentScreen == null) {
                ClientPlayerEntity player = minecraft.player;
                if(player.getMainHandStack().getItem() instanceof SpellCaster && amulet.wasPressed()) {
                    //Data for Packet
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    //Send packet to server to execute
                    ClientSidePacketRegistry.INSTANCE.sendToServer(Salem.OPEN_SPELL_WINDOW_ID, passedData);
                }
            }
        });
    }

    public static void register() {
        registerKeyBinds();
        onKeyPress();
    }
}
