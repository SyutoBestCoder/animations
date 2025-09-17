package com.syuto.animations.gui;

import com.syuto.animations.Animations;
import com.syuto.animations.config.Config;
import com.syuto.animations.config.api.AnimationMode;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class AnimationGuiScreen extends GuiScreen {

    private GuiButton modeButton;
    private GuiSlider scaleSlider;
    private GuiSlider speedSlider;
    private GuiButton resetButton;

    private int modeIndex = 0;

    @Override
    public void initGui() {
        buttonList.clear();

        AnimationMode currentMode = Config.mode;
        AnimationMode[] modes = AnimationMode.values();
        for (int i = 0; i < modes.length; i++) {
            if (modes[i] == currentMode) {
                modeIndex = i;
                break;
            }
        }

        int centerX = width / 2;
        int centerY = height / 2;

        modeButton = new GuiButton(0, centerX - 100, centerY - 70, 200, 20,
                "Mode: " + modes[modeIndex].name().toLowerCase());
        buttonList.add(modeButton);

        scaleSlider = new GuiSlider(1, centerX - 100, centerY - 40, 200, 20,
                "Scale: ", "%", 1, 100, Config.scale, false, true);
        buttonList.add(scaleSlider);

        speedSlider = new GuiSlider(2, centerX - 100, centerY - 10, 200, 20,
                "Speed: ", "%", 1, 100, Config.swingSpeed, false, true);
        buttonList.add(speedSlider);

        resetButton = new GuiButton(3, centerX - 100, centerY + 20, 95, 20, "Reset Defaults");
        buttonList.add(resetButton);

        buttonList.add(new GuiButton(4, centerX + 5, centerY + 20, 95, 20, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            modeIndex = (modeIndex + 1) % AnimationMode.values().length;
            AnimationMode newMode = AnimationMode.values()[modeIndex];
            Animations.setAnimationMode(newMode);
            button.displayString = "Mode: " + newMode.name().toLowerCase();
        } else if (button.id == 3) {
            Config.scale = 100;
            Config.swingSpeed = 1;
            modeIndex = 0;

            Animations.setAnimationScale(Config.scale);
            Animations.setAnimationSpeed(Config.swingSpeed);
            Animations.setAnimationMode(AnimationMode.values()[modeIndex]);

            scaleSlider.setValue(Config.scale);
            speedSlider.setValue(Config.swingSpeed);
            modeButton.displayString = "Mode: " + AnimationMode.values()[modeIndex].name().toLowerCase();
        } else if (button.id == 4) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        int newScale = scaleSlider.getValueInt();
        if (newScale != Config.scale) {
            Config.scale = newScale;
            Animations.setAnimationScale(newScale);
        }

        int newSpeed = speedSlider.getValueInt();
        if (newSpeed != Config.swingSpeed) {
            Config.swingSpeed = newSpeed;
            Animations.setAnimationSpeed(newSpeed);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Animation Settings", width / 2, height / 2 - 90, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
