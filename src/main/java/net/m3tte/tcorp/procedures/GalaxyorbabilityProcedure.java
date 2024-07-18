package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.RedpowerupParticle;
import net.m3tte.tcorp.particle.GalaxyparticleParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.Comparator;

public class GalaxyorbabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Galaxyorbability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Galaxyorbability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Galaxyorbability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Galaxyorbability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Galaxyorbability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle, x, (y + entity.getBbHeight() / 2), z, (int) 8, 0.1, 0.1, 0.1, 0.04);
		}
		{
			List<Entity> _entfound = world
					.getEntities(entity,
							new AxisAlignedBB(x - (12 / 2d), y - (12 / 2d), z - (12 / 2d), x + (12 / 2d), y + (12 / 2d), z + (12 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
						}
					}.compareDistOf(x, y, z)).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (entityiterator.isAlive() && (entityiterator instanceof PlayerEntity || entityiterator instanceof ServerPlayerEntity)) {
					if ((entityiterator.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new TcorpModVariables.PlayerVariables())).teamid == (entity
									.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).teamid) {
						if (entityiterator instanceof LivingEntity)
							((LivingEntity) entityiterator)
									.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 200, (int) 0, (false), (false)));
						if (entityiterator instanceof LivingEntity)
							((LivingEntity) entityiterator)
									.addEffect(new EffectInstance(Effects.REGENERATION, (int) 200, (int) 0, (false), (false)));
						if (entityiterator instanceof LivingEntity)
							((LivingEntity) entityiterator)
									.addEffect(new EffectInstance(Effects.ABSORPTION, (int) 200, (int) 0, (false), (false)));
						if (world instanceof ServerWorld) {
							((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle, (entityiterator.getX()),
									(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 8, 0.1, 0.1, 0.1,
									0.04);
						}
						if (world instanceof ServerWorld) {
							((ServerWorld) world).sendParticles(RedpowerupParticle.particle, (entityiterator.getX()),
									(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 1, 0.1, 0.1, 0.1,
									0);
						}
					}
				}
			}
		}
		{
			double _setval = ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new TcorpModVariables.PlayerVariables())).blips - 4);
			entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.blips = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if (world instanceof World && !((World) world).isClientSide) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.2);
		} else {
			((World) world).playLocalSound(x, y, z,
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.2, false);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
		}
		if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).blipcooldown < 15) {
			{
				double _setval = 15;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
