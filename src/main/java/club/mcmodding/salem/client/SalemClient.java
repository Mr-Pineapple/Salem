package club.mcmodding.salem.client;

import club.mcmodding.salem.blocks.Screens;
import net.fabricmc.api.ClientModInitializer;

public class SalemClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Screens.registerScreensClient();
    }

}
