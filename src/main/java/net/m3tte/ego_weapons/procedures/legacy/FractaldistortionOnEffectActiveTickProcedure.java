package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.particle.ClockparticleParticle;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;

public class FractaldistortionOnEffectActiveTickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure FractaldistortionOnEffectActiveTick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure FractaldistortionOnEffectActiveTick!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ClockparticleParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
					(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
		}
	}
}
