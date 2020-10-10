package club.mcmodding.salem;

import club.mcmodding.salem.blocks.BlockEntities;
import club.mcmodding.salem.blocks.SalemBlocks;
import club.mcmodding.salem.screens.Screens;
import club.mcmodding.salem.items.SalemItems;
import club.mcmodding.salem.screens.SpellPickerScreen;
import club.mcmodding.salem.screens.handlers.SpellPickerScreenHandler;
import club.mcmodding.salem.spells.SpellRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class Salem implements ModInitializer {

    public static final String MOD_ID  = "salem";
    public static final Identifier OPEN_SPELL_WINDOW_ID = new Identifier(MOD_ID, "open_spell_window");

    @Override
    public void onInitialize() {
        SpellRegistry.init();
        SalemItems.init();
        SalemBlocks.init();
        BlockEntities.init();
        Screens.init();

        //TODO: Move this to an external place ie. PacketUtil
        ServerSidePacketRegistry.INSTANCE.register(OPEN_SPELL_WINDOW_ID, ((packetContext, attachedData) -> {
            packetContext.getTaskQueue().execute(() -> {
                //Execute on the Main Thread!
                ServerPlayerEntity player = (ServerPlayerEntity)packetContext.getPlayer();
                if(player != null) {
                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                        return new SpellPickerScreenHandler(i, playerInventory, ScreenHandlerContext.create(player.world, player.getBlockPos()));
                    }, SpellPickerScreen.spellSelectionTitle));
                }
            });
        }));




    }

}
