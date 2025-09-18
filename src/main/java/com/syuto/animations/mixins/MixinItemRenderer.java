package com.syuto.animations.mixins;

import com.syuto.animations.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
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

    @Shadow
    @Final
    private Minecraft mc;

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
    public void render(float p_renderItemInFirstPerson_1_, CallbackInfo ci) {
        ItemRendererAccessor itemRenderer = (ItemRendererAccessor) this;
        float equippedProgress = itemRenderer.getEquippedProgress();
        float prevEquippedProgress = itemRenderer.getPrevEquippedProgress();
        float f = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * p_renderItemInFirstPerson_1_);
        AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().thePlayer;
        float swingProgress = abstractclientplayer.getSwingProgress(p_renderItemInFirstPerson_1_);

        float sine = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        float sqrtSwing = MathHelper.sqrt_float(swingProgress);
        float sine1 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);

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
                GL11.glRotated(-sine * 27.5F, -8.0F, -0.0F, 9.0F);
                GL11.glRotated(-sine * 45, 1.0F, sine / 2, -0.0F);
                GL11.glTranslated(-0.1, 0.3, 0.1);
                break;

            case VANILLA:
                GL11.glTranslated(0, 0.05, -0.1);
                this.transformFirstPersonItem(f , swingProgress);
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

            case ETB:
                GL11.glTranslated(0, -0.1, 0);
                this.transformFirstPersonItem(f, 0.0F);
                GL11.glTranslatef(0.1F, 0.4F, -0.1F);
                GL11.glRotated(-sine * 35f, -8f, -0f, 9f);
                GL11.glRotated(-sine * 70, 1.5f, -0.4f, -0f);
                break;

            case DORTWARE:
                float altSine = MathHelper.sin(sqrtSwing * (float) Math.PI - 3);
                this.transformFirstPersonItem(f, 0.0F);
                GL11.glRotated(-sine * 10, 0.0f, 15.0f, 200.0f);
                GL11.glRotated(-sine * 10f, 300.0f, sine / 2.0f, 1.0f);
                GL11.glTranslated(3.4, 0.3, -0.4);
                GL11.glTranslatef(-2.10f, -0.2f, 0.1f);
                GL11.glRotated(altSine * 13.0f, -10.0f, -1.4f, -10.0f);
                break;

            case AVATAR:
                GL11.glTranslatef(0.56F, -0.52F, -0.71999997F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(sine1 * -20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(sine * -20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(sine * -40.0F, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(0.4F, 0.4F, 0.4F);
                break;

            case SWONG:
                this.transformFirstPersonItem(f / 2.0F, 0.0F);
                GL11.glRotated(-sine * 20.0F, sine / 2.0F, -0.0F, 9.0F);
                GL11.glRotated(-sine * 30.0F, 1.0F, sine / 2.0F, -0.0F);
                break;

            case SWANG:
                this.transformFirstPersonItem(f / 2.0F, swingProgress);
                GL11.glRotated(sine * 15.0F, -sine, -0.0F, 9.0F);
                GL11.glRotated(sine * 40.0F, 1.0F, -sine / 2.0F, -0.0F);
                break;

            case SWANK:
                this.transformFirstPersonItem(f / 2.0F, swingProgress);
                GL11.glRotated(sine * 30.0F, -sine, -0.0F, 9.0F);
                GL11.glRotated(sine * 40.0F, 1.0F, -sine, -0.0F);
                break;

            case STYLES:
                this.transformFirstPersonItem(f, 0.0F);
                GL11.glTranslatef(-0.05f, 0.2f, 0.0f);
                GL11.glRotated(-sine * 35.0f, -8.0f, -0.0f, 9.0f);
                GL11.glRotated(-sine * 70.0f, 1.0f, -0.4f, -0.0f);
                break;

            case NUDGE:
                GL11.glTranslated(-0.1D, 0.09D, 0.0D);
                GL11.glRotated(0, -320, 320, 0);
                this.transformFirstPersonItem(0, 1);
                float nudgeSine1 = MathHelper.sin(sqrtSwing * 3f);
                float nudgeSine2 = MathHelper.sin(sqrtSwing * 4.9415927f);
                GL11.glRotated(-nudgeSine1 * 60.0f, -90, -nudgeSine2, 10);
                GL11.glRotated(-nudgeSine1 * 110, 15, nudgeSine2, -0);
                break;

            case PUNCH:
                this.transformFirstPersonItem(f, 0.0f);
                GL11.glTranslatef(0.1f, 0.2f, 0.3f);
                GL11.glRotated(-sine * 30.0f, -5.0f, 0.0f, 9.0f);
                GL11.glRotated(-sine * 10.0f, 1.0f, -0.4f, -0.5f);
                break;

            case SLIDE:
                GL11.glTranslated(-0.1D, 0.15D, 0.0D);
                this.transformFirstPersonItem(0, 0);
                float slideSine = MathHelper.sin(sqrtSwing * 2.9415927f);
                GL11.glTranslatef(-0.05f, -0.0f, 0.35f);
                GL11.glRotated(-slideSine * 30.0f, -15.0f, slideSine, 10);
                GL11.glRotated(-slideSine * 70.0, 5.0f, -slideSine, -0);
                break;

            case JIGSAW:
                GL11.glTranslatef(0.56F, -0.42F, -0.71999997F);
                GL11.glTranslatef(0.1F * sine, -0F, -0.21999997F * sine);
                GL11.glTranslatef(0.0F, sine1 * -0.15F, 0.0F);
                GL11.glRotated(sine1 * 45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotated(sine1 * -20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotated(sine * -20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotated(sine* -80.0F, 1.0F, 0.0F, 0.0F);
                break;
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
