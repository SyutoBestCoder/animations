package com.syuto.animations.mixins;


import com.syuto.animations.Animations;
import com.syuto.animations.config.Config;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {


    @Shadow public abstract PotionEffect getActivePotionEffect(Potion p_getActivePotionEffect_1_);

    @Shadow public abstract boolean isPotionActive(Potion p_isPotionActive_1_);

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
