package net.m3tte.tcorp.execFunctions;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.CrimsonkimonoItem;
import net.m3tte.tcorp.item.blackSilence.weapons.WheelsIndustry;
import net.m3tte.tcorp.particle.ArmourupparticleParticle;
import net.m3tte.tcorp.particle.CrimsonfanparticleParticle;
import net.m3tte.tcorp.potion.MarkedeffectPotionEffect;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.m3tte.tcorp.potion.WoodsmansstancePotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.HashMap;
import java.util.Map;

public class HitProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityAttacked(LivingAttackEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				Entity sourceentity = event.getSource().getEntity();
				Entity immediatesourceentity = event.getSource().getDirectEntity();
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				double amount = event.getAmount();
				World world = entity.level;
				Map<String, Object> dependencies = new HashMap<>();

				if (entity instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) entity;
					if (sourceentity instanceof LivingEntity && living.getHealth() > amount) {
						LivingEntity sourceLiving = (LivingEntity) sourceentity;


						if (sourceLiving.getItemInHand(Hand.MAIN_HAND).getItem().equals(TCorpItems.WHEELS_INDUSTRY.get())) {
							LivingEntityPatch<?> patch = (LivingEntityPatch) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, (Direction) null).orElse((LivingEntityPatch) null);
							if (patch != null) {

								if (patch.getServerAnimator().animationPlayer.getAnimation() instanceof AttackAnimation) {
									patch.playSound(TcorpModElements.sounds.get(ResourceLocation.of("tcorp:stagger", ':')), 1, -0.05F, 0.1F);
									sourceLiving.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), (int) 20, (int) 0, (false), (false)));

									if (patch instanceof PlayerPatch<?>) {
										PlayerPatch<?> playerPatch = (PlayerPatch<?>) patch;
										playerPatch.setStamina(playerPatch.getStamina() - 3);

										if (playerPatch.getStamina() <= 0) {
											playerPatch.setStamina(0);
										}
									}

									hitStunEffect(patch);
								}
							}
						}
					}


					if (living.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(CrimsonkimonoItem.body) && world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(CrimsonfanparticleParticle.particle, x, (y + 1), z, 8, 0.2, 0.4, 0.2, 0.05);
						((ServerWorld) world).sendParticles(CrimsonfanparticleParticle.particle, x, (y + 1), z, 8, 0.2, 0.4, 0.2, 0.05);
					}

					if (living.hasEffect(WoodsmansstancePotionEffect.potion)) {
						if (sourceentity instanceof LivingEntity)
							((LivingEntity) sourceentity).addEffect(new EffectInstance(Effects.WEAKNESS, 40, 1, (false), (false)));
						sourceentity.hurt(DamageSource.GENERIC, (float) 1);
						if (world instanceof ServerWorld) {
							((ServerWorld) world).sendParticles(ArmourupparticleParticle.particle, x, (y + 1), z, 8, 0.2, 0.4, 0.2, 0.3);
						}
						if (!world.isClientSide()) {
							if (world instanceof World && !((World) world).isClientSide) {
								world.playSound(null, new BlockPos(x, y, z),
										ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:metal_clash")),
										SoundCategory.NEUTRAL, (float) 0.7, (float) 1.4);
							} else {
								((World) world).playLocalSound(x, y, z,
										ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:metal_clash")),
										SoundCategory.NEUTRAL, (float) 0.7, (float) 1.4, false);
							}
						}
					}
					if (living.hasEffect(MarkedeffectPotionEffect.potion)) {
						if (entity instanceof LivingEntity)
							((LivingEntity) entity).setHealth((float) (living.getHealth() - amount * 0.2 * (living.getEffect(MarkedeffectPotionEffect.potion).getAmplifier() + 1)));
						if (!world.isClientSide()) {
							if (world instanceof World && !((World) world).isClientSide) {
								world.playSound(null, new BlockPos(x, y, z),
										ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.break")),
										SoundCategory.NEUTRAL, (float) 1, (float) 0.8);
							} else {
								((World) world).playLocalSound(x, y, z,
										ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.break")),
										SoundCategory.NEUTRAL, (float) 1, (float) 0.8, false);
							}
						}
					}
					if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).iFrames > 0) {
						if (dependencies.get("event") != null) {
							Object _obj = dependencies.get("event");
							if (_obj instanceof Event) {
								Event _evt = (Event) _obj;
								if (_evt.isCancelable())
									_evt.setCanceled(true);
							}
						}
					}

					if (living.hasEffect(OrlandoPotionEffect.potion) && living instanceof PlayerEntity) {
						PlayerEntity player = (PlayerEntity) living;

						PlayerPatch<?> entitypatch = (PlayerPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

						// Blacksilence Auto Dodge, has a 30% Chance
						if (!world.isClientSide() && !player.getCooldowns().isOnCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get()) && entitypatch.getStamina() > entitypatch.getMaxStamina() * 0.2f) {
							if (amount > 6 && living.getRandom().nextFloat() > 0.7) {
								entitypatch.setStamina(entitypatch.getStamina() - entitypatch.getMaxStamina() * 0.4f);
								player.getCooldowns().addCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get(), 240);
								entitypatch.playAnimationSynchronized(TCorpAnimations.BS_DODGE, 0);

								if (dependencies.get("event") != null) {
									Object _obj = dependencies.get("event");
									if (_obj instanceof Event) {
										Event _evt = (Event) _obj;
										if (_evt.isCancelable())
											_evt.setCanceled(true);
									}
								}
							} else if (amount > 6) {
								player.getCooldowns().addCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get(), 60);
							}
						}
					}

				}
			}
		}

	}

		public static void hitStunEffect(LivingEntityPatch<?> patch) {
			hitStunEffect(patch, 0);
		}

		public static void hitStunEffect(LivingEntityPatch<?> patch, float timeMod) {
			LivingEntity living = patch.getOriginal();
			if (patch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
				patch.playAnimationSynchronized(TCorpAnimations.RANGA_GUARD_STAGGER, timeMod);
				living.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), (int) (20 * (timeMod * 2 + 1)), (int) 0, (false), (false)));
			} else {
				living.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 15, (int) 4, (false), (false)));
				living.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 40, (int) 4, (false), (false)));
				if (patch.getHitAnimation(ExtendedDamageSource.StunType.SHORT) != null) {
					patch.playAnimationSynchronized(patch.getHitAnimation(ExtendedDamageSource.StunType.SHORT), 1f);
				}
			}
		}

}
