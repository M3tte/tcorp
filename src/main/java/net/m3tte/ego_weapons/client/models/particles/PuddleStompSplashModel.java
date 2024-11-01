package net.m3tte.ego_weapons.client.models.particles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PuddleStompSplashModel extends AbstractParticleModel {
    private final ParticleVoxel splash;

    public PuddleStompSplashModel() {
        texWidth = 80;
        texHeight = 80;

        splash = new ParticleVoxel(this);
        splash.setPos(0.0F, 24.0F, 0.0F);
        splash.texOffs(0, 0).addBox(-6.0F, -37.0F, -6.0F, 12.0F, 37.0F, 12.0F, 0.0F, false);

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuffer, int light, float u0, float u1, float v0, float v1, float red, float green, float blue, float alpha) {
        this.splash.render(matrixStack, vertexBuffer, light, u0, u1, v0, v1, red, green, blue, alpha);
    }
}
