package club.mcmodding.salem;

import club.mcmodding.salem.items.SalemItems;
import club.mcmodding.salem.spells.SpellRegistry;
import net.fabricmc.api.ModInitializer;

public class Salem implements ModInitializer {

    public static final String MOD_ID  = "salem";

    @Override
    public void onInitialize() {
        SpellRegistry.init();
        SalemItems.init();
    }

}
