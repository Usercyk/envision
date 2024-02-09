package me.usercyk.envision;

import me.usercyk.envision.screen.EnvisionHandledScreens;
import net.fabricmc.api.ClientModInitializer;

public class EnvisionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Envision.LOGGER.info("Initializing client side for " + Envision.MOD_ID);
        EnvisionHandledScreens.register();
    }
}
