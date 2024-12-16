package com.syuto.animations.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ClientUtils {
    static Minecraft mc = Minecraft.getMinecraft();


    public static boolean nullCheck() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static void sendMessage(String message) {
        if (!nullCheck()) {
            return;
        }
        final String txt = replace("&7[&dA&7]&r " + message);
        mc.thePlayer.addChatMessage(new ChatComponentText(txt));
    }

    public static void sendLine() {
        sendMessage("&7&m-------------------------");
    }

    public static String replace(String text) {
        return text.replace("&", "§").replace("%and", "&");
    }


}