package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.potion.PutridtouchPotionEffect;
import net.m3tte.tcorp.particle.RedpowerupParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class HeavenswatcherabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Heavenswatcherability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Heavenswatcherability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Heavenswatcherability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Heavenswatcherability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Heavenswatcherability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity target = null;
		{
			double _setval = ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new TcorpModVariables.PlayerVariables())).blips - 4);
			entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.blips = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if (!world.isClientSide()) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.2);
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.blaze.hurt")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.2);
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
			}
		}
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(PutridtouchPotionEffect.potion, (int) 100, (int) 0, (false), (false)));
		if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).blipcooldown < 10) {
			{
				double _setval = 10;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
