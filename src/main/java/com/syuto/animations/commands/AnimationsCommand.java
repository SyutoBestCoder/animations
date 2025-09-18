package com.syuto.animations.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.syuto.animations.screens.AnimationsGui;
import com.syuto.animations.utils.ClientUtils;
import com.syuto.animations.utils.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class AnimationsCommand extends CommandBase {
    private static final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() {
        return "animations";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/animations - Opens the Animations Settings GUI";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        new DelayedTask(() -> mc.displayGuiScreen(new AnimationsGui()));
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
