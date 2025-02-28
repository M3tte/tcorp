package net.m3tte.ego_weapons.specialParticles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class DoubtExplosionParticle extends HitParticle {
    public DoubtExplosionParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize = 0.6F;
        this.lifetime = 12;
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;
    }

    @Override
    public void tick() {
        this.quadSize += 0.15f;
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            if (EpicFightMod.CLIENT_INGAME_CONFIG.offBloodEffects.getValue()) {
                return null;
            }
            DoubtExplosionParticle particle = new DoubtExplosionParticle(worldIn, x, y, z, spriteSet);
            return particle;
        }


    }
}
