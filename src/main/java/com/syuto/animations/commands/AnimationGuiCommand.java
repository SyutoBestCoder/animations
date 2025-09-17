package com.syuto.animations.commands;

import com.syuto.animations.gui.AnimationGuiScreen;
import com.syuto.animations.utils.DelayedTask;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;

public class AnimationGuiCommand extends CommandBase {
    private static final Minecraft mc = Minecraft.getMinecraft();



    @Override
    public String getCommandName() {
        return "animationgui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/animationgui - Opens the Animation Settings GUI";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        new DelayedTask(() -> mc.displayGuiScreen(new AnimationGuiScreen()));
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
