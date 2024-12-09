package com.syuto.animations;

import com.google.gson.*;
import com.syuto.animations.commands.AnimationCommand;
import com.syuto.animations.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


@Mod(modid = Animations.MODID, version = Animations.VERSION)
public class Animations {
    public static final String MODID = "animations";
    public static final String VERSION = "1.0";


    public static final Logger LOG = LogManager.getLogger("Byte");
    private static final File modDirectory = new File(Minecraft.getMinecraft().mcDataDir, "AnimationsMod");
    private static final File configFile = new File(modDirectory, "animations.json");
    public static JsonObject config = new JsonObject();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (!modDirectory.exists()) {
            modDirectory.mkdir();
        }

        ClientCommandHandler.instance.registerCommand(new AnimationCommand());

        loadConfig();
        LOG.info("Animations mod initialized.");
    }

    public static void saveConfig() {
        try {
            if (!modDirectory.exists()) {
                modDirectory.mkdir();
            }

            JsonObject jo = new JsonObject();
            jo.addProperty("mode", Config.mode != null ? Config.mode.toLowerCase() : "vanilla");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonData = gson.toJson(jo);

            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(jsonData);
            }

            LOG.info("Configuration saved successfully.");
        } catch (Throwable t) {
            LOG.error("Unable to save configuration: {}", t.getMessage());
        }
    }

    public static void loadConfig() {
        if (!configFile.exists()) {
            saveConfig();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonParser jsonParser = new JsonParser();
            config = jsonParser.parse(reader).getAsJsonObject();
            if (config.has("mode")) {
                Config.mode = config.get("mode").getAsString();
            } else {
                Config.mode = "vanilla";
            }
            LOG.info("Configuration loaded successfully.");
        } catch (JsonSyntaxException | IOException e) {
            LOG.error("Unable to load configuration: {}", e.getMessage());
        }
    }

    public static void setAnimationMode(String mode) {
        Config.mode = mode;
        saveConfig();
    }
}
