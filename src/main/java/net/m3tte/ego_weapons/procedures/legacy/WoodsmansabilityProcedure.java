package net.m3tte.ego_weapons.procedures.legacy;

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
import net.minecraft.command.FunctionObject;

import net.m3tte.ego_weapons.potion.WoodsmansstancePotionEffect;
import net.m3tte.ego_weapons.particle.RedpowerupParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.ArmourupparticleParticle;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Optional;
import java.util.Map;

public class WoodsmansabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Woodsmansability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure Woodsmansability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure Woodsmansability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure Woodsmansability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Woodsmansability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity target = null;
		{
			double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).blips - 5);
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.blips = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if (!world.isClientSide()) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.3);
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break")),
					SoundCategory.NEUTRAL, (float) 1, (float) 0.8);
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ArmourupparticleParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0.1);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
			}
		}
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 200, (int) 3, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 200, (int) 4, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 200, (int) 5, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(WoodsmansstancePotionEffect.potion, (int) 200, (int) 0, (false), (false)));
		if (!entity.level.isClientSide && entity.level.getServer() != null) {
			Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:woodsmansabilityuse"));
			if (_fopt.isPresent()) {
				FunctionObject _fobj = _fopt.get();
				entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
			}
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).blipcooldown < 25) {
			{
				double _setval = 25;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}