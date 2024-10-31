package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.client.models.entities.DawnOfGreenDoubtAModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class DoubtARenderer extends PatchedLivingEntityRenderer<DawnOfGreenDoubtEntity, DoubtAPatch, DawnOfGreenDoubtAModel> {
    public DoubtARenderer() {

    }

    @Override
    protected void setJointTransforms(DoubtAPatch entitypatch, Armature armature, float partialTicks) {
        this.setJointTransform(3, armature, entitypatch.getHeadMatrix(partialTicks));
    }

    @Override
    protected int getRootJointIndex() {
        return 0;
    }

    @Override
    protected double getLayerCorrection() {
        return 0.5F;
    }
}