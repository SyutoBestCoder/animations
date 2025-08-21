package com.syuto.animations.mixins;


import com.syuto.animations.config.Config;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    /**
     * @author
     * @reason
     */

    @Overwrite
    private int getArmSwingAnimationEnd() {
        int percentage = Math.max(0, Math.min(Config.swingSpeed, 100));
        int scaledSpeed = (int) (6 + ((percentage / 100.0) * (20 - 6)));
        return scaledSpeed;
    }


}
