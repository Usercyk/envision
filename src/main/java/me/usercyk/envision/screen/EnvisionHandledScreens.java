package me.usercyk.envision.screen;

import me.usercyk.envision.Envision;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class EnvisionHandledScreens {

    public static void register() {
        Envision.LOGGER.info("Registering screens for " + Envision.MOD_ID);
        HandledScreens.register(EnvisionScreenHandlerTypes.WOODEN_HOPPER_SCREEN_HANDLER, WoodenHopperScreen::new);
    }
}
