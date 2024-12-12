package com.syuto.animations.commands;

import com.syuto.animations.Animations;
import com.syuto.animations.config.api.AnimationMode;
import com.syuto.animations.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.stream.Stream;

public class AnimationCommand extends CommandBase {

    Minecraft mc = Minecraft.getMinecraft();


    @Override
    public String getCommandName() {
        return "animation";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/animation [help|modes|mode] - Displays help or sets animation mode";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            ClientUtils.sendMessage("&cInvalid usage. Use /animation help.");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                sendHelpMessage();
                break;

            case "modes":
                sendAvailableModes();
                break;
            default:
                setAnimationMode(args[0]);
                break;
        }
    }

    private void sendHelpMessage() {
        ClientUtils.sendLine();
        ClientUtils.sendMessage("&eAnimation Command Help:");
        ClientUtils.sendMessage("&7/animation [mode] - Sets the animation mode.");
        ClientUtils.sendMessage("&7/animation modes - Shows the animation modes.");
        ClientUtils.sendMessage("&7/animation help - Shows this help message.");
        ClientUtils.sendLine();
    }

    private void sendAvailableModes() {
        ClientUtils.sendLine();
        ClientUtils.sendMessage("&eAvailable Animation Modes:");
        Stream.of(AnimationMode.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .forEach(mode -> ClientUtils.sendMessage("&3- " + mode));
        ClientUtils.sendLine();
    }

    private void setAnimationMode(String modeName) {
        try {
            AnimationMode mode = AnimationMode.valueOf(modeName.toUpperCase());
            Animations.setAnimationMode(mode);
            ClientUtils.sendMessage("&aAnimation mode set to: " + mode.name().toLowerCase());
        } catch (IllegalArgumentException e) {
            ClientUtils.sendMessage("&cInvalid mode. Use /animation modes to view valid modes.");
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
