
package net.m3tte.tcorp.potion;

import net.m3tte.tcorp.TcorpModVariables;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Staggered {
	@ObjectHolder("tcorp:staggered")
	public static final Effect potion = null;

	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: STAGGER");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.HARMFUL, -16777216);
			setRegistryName("staggered");
		}

		@Override
		public String getDescriptionId() {
			return "effect.staggered";
		}

		@Override
		public boolean isBeneficial() {
			return false;
		}

		@Override
		public boolean isInstantenous() {
			return false;
		}

		@Override
		public boolean shouldRenderInvText(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean shouldRender(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean shouldRenderHUD(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
			super.applyEffectTick(entity, p_76394_2_);

			if (entity instanceof PlayerEntity) {
				TcorpModVariables.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
				PlayerPatch<?> entitypatch = (PlayerPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
				if (entity != null && playerVars != null) {
					if (playerVars.globalcooldown <= 5) {
						playerVars.globalcooldown = 5;
						playerVars.syncPlayerVariables(entity);
						if (entitypatch != null) {
							entitypatch.setStamina(0);
						}

					}

					if (entity.getEffect(this).getDuration() < 5) {
						playerVars.stagger = playerVars.maxStagger;
						playerVars.syncStagger(entity);
					}
				}

			} else {
				if (entity.getEffect(this).getDuration() < 5) {
					entity.getPersistentData().putDouble("stagger",0);
				}
			}

		}
	}
}