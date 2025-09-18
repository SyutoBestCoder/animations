package com.syuto.animations.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ClientUtils {
    private static final String PREFIX = ChatFormatting.GRAY + "["
            + ChatFormatting.LIGHT_PURPLE + "Animations"
            + ChatFormatting.GRAY + "] "
            + ChatFormatting.RESET;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean nullCheck() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static void sendMessage(String message) {
        if (!nullCheck()) {
            return;
        }

        mc.thePlayer.addChatMessage(new ChatComponentText(PREFIX + message));
    }


}