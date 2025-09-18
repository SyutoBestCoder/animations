package com.syuto.animations;

import com.google.gson.*;
import com.syuto.animations.commands.AnimationsCommand;
import com.syuto.animations.config.api.AnimationMode;
import com.syuto.animations.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

@Mod(modid = Animations.MODID, version = Animations.VERSION)
public class Animations {
    public static final String MODID = "animations";
    public static final String VERSION = "1.6";

    public static final Logger LOG = LogManager.getLogger("Byte");
    private static final File modDirectory = new File(Minecraft.getMinecraft().mcDataDir, "AnimationsMod");
    private static final File configFile = new File(modDirectory, "animations.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        doesDirectoryExists();
        ClientCommandHandler.instance.registerCommand(new AnimationsCommand());
        loadConfig();
        LOG.info("Animations mod initialized.");
    }

    private static void doesDirectoryExists() {
        if (!modDirectory.exists() && !modDirectory.mkdir()) {
            LOG.warn("Failed to create mod directory: {}", modDirectory.getAbsolutePath());
        }
    }

    public static void saveConfig() {
        doesDirectoryExists();
        JsonObject obj = new JsonObject();
        obj.addProperty("mode", Config.mode.name());
        obj.addProperty("scale", Config.scale);
        obj.addProperty("speed", Config.swingSpeed);

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(obj, writer);
            LOG.info("Configuration saved successfully.");
        } catch (IOException e) {
            LOG.error("Unable to save configuration.", e);
        }
    }

    public static void loadConfig() {
        if (!configFile.exists()) {
            saveConfig();
            return;
        }
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);

            Config.mode = AnimationMode.fromJsonValue(obj.get("mode").getAsString());
            Config.scale = obj.get("scale").getAsInt();
            Config.swingSpeed = obj.get("speed").getAsInt();

            LOG.info("Configuration loaded successfully.");
        } catch (IOException | JsonSyntaxException e) {
            LOG.error("Unable to load configuration.", e);
        }
    }

    public static void updateConfig(AnimationMode mode, int scale, int speed) {
        if (mode != null)
            Config.mode = mode;
        Config.scale = scale;
        Config.swingSpeed = speed;
        saveConfig();
    }
}
