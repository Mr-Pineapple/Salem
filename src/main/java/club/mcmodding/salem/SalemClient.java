package club.mcmodding.salem;

import club.mcmodding.salem.util.KeybindUtil;
import net.fabricmc.api.ClientModInitializer;

public class SalemClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeybindUtil.register();
        Screens.registerScreensClient();
    }

}
