package com.syuto.animations.commands;

import com.syuto.animations.Animations;
import com.syuto.animations.utils.ClientUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class AnimationCommand extends CommandBase {

    private static final List<String> VALID_MODES = Arrays.asList("exhibition", "sigma", "plain", "vanilla");

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
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    ClientUtils.sendLine();
                    ClientUtils.sendMessage("&3Animation Command Help:");
                    ClientUtils.sendMessage("&3/animation mode [mode] - Sets the animation mode.");
                    ClientUtils.sendMessage("&3/animation modes - Shows the animation modes.");
                    ClientUtils.sendMessage("&3/animation help - Shows this help message.");
                    ClientUtils.sendLine();
                    break;

                case "modes":
                    ClientUtils.sendLine();
                    ClientUtils.sendMessage("&3Animation Modes:");
                    VALID_MODES.forEach(mode -> ClientUtils.sendMessage("&3- " + mode));
                    ClientUtils.sendLine();
                    break;

                default:
                    ClientUtils.sendMessage("&cInvalid usage. Use /animation help.");
                    break;
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("mode")) {
            String mode = args[1];
            if (VALID_MODES.contains(mode)) {
                Animations.setAnimationMode(mode.toLowerCase());
                ClientUtils.sendMessage("&aAnimation mode set to: " + mode);
            } else {
                ClientUtils.sendMessage("&cInvalid mode. Use /animation modes to view valid modes.");
            }
        } else {
            ClientUtils.sendMessage("&cInvalid usage. Use /animation help.");
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
