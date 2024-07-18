package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.m3tte.tcorp.particle.SparkparticleParticle;
import net.m3tte.tcorp.particle.BlackdamageParticle;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;
import java.util.Collection;

public class CrystalshitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Crystalshit!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Crystalshit!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Crystalshit!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Crystalshit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Crystalshit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				TcorpMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Crystalshit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
					for (EffectInstance effect : effects) {
						if (effect.getEffect() == FuriosoPotionEffect.potion)
							return true;
					}
				}
				return false;
			}
		}.check(sourceentity)) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 4));
			if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
				sourceentity.getPersistentData().putDouble("furiosohits", (2.6 + sourceentity.getPersistentData().getDouble("furiosohits")));
				if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
					if (world instanceof World && !((World) world).isClientSide) {
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:cross_slash")),
								SoundCategory.PLAYERS, (float) 1, (float) 1);
					} else {
						((World) world).playLocalSound(x, y, z,
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:cross_slash")),
								SoundCategory.PLAYERS, (float) 1, (float) 1, false);
					}
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(SparkparticleParticle.particle, (sourceentity.getX()),
								(sourceentity.getY() + sourceentity.getBbHeight() / 1.5), (sourceentity.getZ()), (int) 10, 0, 0, 0, 0.1);
					}
					if (entity instanceof LivingEntity)
						((LivingEntity) entity)
								.setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 4));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
				}
			}
		} else {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 2));
		}
	}
}
