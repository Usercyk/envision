package me.usercyk.envision.screen;

import me.usercyk.envision.Envision;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class EnvisionScreenHandlerTypes {

    public static final ScreenHandlerType<WoodenHopperScreenHandler> WOODEN_HOPPER_SCREEN_HANDLER = registerScreenHandler("wooden_hopper", new ExtendedScreenHandlerType<>(WoodenHopperScreenHandler::new));


    @SuppressWarnings("SameParameterValue")
    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType<T> screenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, Envision.id(name), screenHandlerType);
    }

    public static void register() {
        Envision.LOGGER.info("Registering Screen Handlers for " + Envision.MOD_ID);
    }
}
