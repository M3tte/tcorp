package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class SwapFiringModePressProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {


		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (0 >= (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).globalcooldown) {
			{
				boolean _setval = (!(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).firingMode);
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.firingMode = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).firingMode) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, (float) 1, (float) 1.5);
			} else {
				((World) world).playSound(null, new BlockPos(x, y, z),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, (float) 1, (float) 0.5);
			}
		}
	}
}
