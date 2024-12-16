package com.syuto.animations.mixins;

import com.syuto.animations.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    private float spin = 0.0f;

    @Shadow protected abstract void transformFirstPersonItem(float p_transformFirstPersonItem_1_, float p_transformFirstPersonItem_2_);

    @Shadow protected abstract void doBlockTransformations();

    @Redirect(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V",
                    ordinal = 2
            )
    )
    private void skip(ItemRenderer instance, float p_transformFirstPersonItem_1_, float p_transformFirstPersonItem_2_) {
    }


    @Inject(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;doBlockTransformations()V"

            )
    )
    public void render(float p_renderItemInFirstPerson_1_, CallbackInfo ci) { //@AwayXD plz use GL11 not GLStateManager thx xoxoxo
        ItemRendererAccessor itemRenderer = (ItemRendererAccessor) this;
        float equippedProgress = itemRenderer.getEquippedProgress();
        float prevEquippedProgress = itemRenderer.getPrevEquippedProgress();
        float f = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * p_renderItemInFirstPerson_1_);
        AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().thePlayer;
        float f1 = abstractclientplayer.getSwingProgress(p_renderItemInFirstPerson_1_);
        float sine = MathHelper.sin(MathHelper.sqrt_float(f1) * (float) Math.PI);

        switch (Config.mode) {
            case EXHIBITION:
                GL11.glTranslated(0, -0.1, 0);
                this.transformFirstPersonItem(f / 2, 0.0F);
                GL11.glTranslatef(0.1F, 0.4F, -0.1F);
                GL11.glRotated(-sine * 30.0F, sine / 2, 0.0F, 9.0F);
                GL11.glRotated(-sine * 50.0F, 0.8F, sine / 2, 0F);
                break;
            case SIGMA:
                this.transformFirstPersonItem(f * 0.5f, 0);
                GL11.glRotated(-sine * 55 / 2.0F, -8.0F, -0.0F, 9.0F);
                GL11.glRotated(-sine * 45, 1.0F, sine / 2, -0.0F);
                GL11.glTranslated(-0.1, 0.3, 0.1);
                break;
            case VANILLA:
                GL11.glTranslated(0, 0.05, 0);
                this.transformFirstPersonItem(0, f1);
                break;
            case PLAIN:
                GL11.glTranslated(0, 0.05, 0);
                this.transformFirstPersonItem(f, 0.0F);
                break;
            case SPIN:
                GL11.glRotated(this.spin, 0f, 0f, -0.1f);
                this.transformFirstPersonItem(f, 0f);
                this.spin = -(System.currentTimeMillis() / 2 % 360);
                break;
            /*case STAB: //broken @AwayXD
                GL11.glTranslated(0.75, 0.45, 0.8);
                this.transformFirstPersonItem(f / 1.5f, 0.0f);
                GL11.glTranslated(0.6f, 0.3f, -0.6f + -sine * 0.7);
                GL11.glRotated(6090, 0.0f, 0.0f, 0.1f);
                GL11.glRotated(6085, 0.0f, 0.1f, 0.0f);
                GL11.glRotated(6110, 0.1f, 0.0f, 0.0f);
                this.transformFirstPersonItem(0.0F, 0.0f);
                this.doBlockTransformations();
                break;*/
        }
    }



    @Inject(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void itemScale(float p_renderItemInFirstPerson_1_, CallbackInfo ci) {
        GL11.glScaled((double) Config.scale / 100, (double) Config.scale / 100, (double) Config.scale / 100);
    }
}
