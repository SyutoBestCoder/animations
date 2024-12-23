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
        ItemRendererAccessor itemRenderer = (ItemRendererAccessor) this; //@SyutoBestCoder I gotchu mb
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
            case ETB: // >_< i stole these
                GL11.glTranslated(0, -0.1, 0);
                this.transformFirstPersonItem(f , 0.0F);
                GL11.glTranslatef(0.1F, 0.4F, -0.1F);
                GL11.glRotated(-sine * (float) 70 / 2f, -8f, -0f, 9f);
                GL11.glRotated(-sine * (float) 70, 1.5f, -0.4f, -0f);
                break;
            case DORTWARE:
                float sine2 = MathHelper.sin(MathHelper.sqrt_float(f1) * (float) Math.PI - 3);
                this.transformFirstPersonItem(f , 0.0F);
                GL11.glRotated(-sine * 10, 0.0f, 15.0f, 200.0f);
                GL11.glRotated(-sine * 10f, 300.0f, sine / 2.0f, 1.0f);
                GL11.glTranslated(3.4, 0.3, -0.4);
                GL11.glTranslatef(-2.10f, -0.2f, 0.1f);
                GL11.glRotated(sine2 * 13.0f, -10.0f, -1.4f, -10.0f);
                break;
            case AVATAR:
                float swingProgress = abstractclientplayer.getSwingProgress(p_renderItemInFirstPerson_1_);
                GL11.glTranslatef(0.56F, -0.52F, -0.71999997F);
                GL11.glTranslatef(0.0F, 0, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                float red = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
                float blue = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI);
                GL11.glRotatef(red * -20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(blue * -20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(blue * -40.0F, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(0.4F, 0.4F, 0.4F);
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
