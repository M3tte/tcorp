
package net.m3tte.tcorp.item.mimicry;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.redmist.RedMistEGOSuit;
import net.m3tte.tcorp.item.redmist.RedMistJacket;
import net.m3tte.tcorp.potion.BleedPotionEffect;
import net.m3tte.tcorp.procedures.MimicryhitentityProcedure;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import static net.m3tte.tcorp.execFunctions.HitProcedure.hitStunEffect;

@TcorpModElements.ModElement.Tag
public class MimicryItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:mimicry")
	public static final Item block = null;

	public MimicryItem(TcorpModElements instance) {
		super(instance, 165);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {

			@Override
			public int getUses() {
				return 0;
			}

			@Override
			public float getSpeed() {
				return 4.2f;
			}

			@Override
			public float getAttackDamageBonus() {
				return 14;
			}

			@Override
			public int getLevel() {
				return 1;
			}

			@Override
			public int getEnchantmentValue() {
				return 2;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}

		}, 3, -2.65f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("A disgusting reminder to what shall not be."));
				list.add(new StringTextComponent("[Ability] Horizontal Split - 7E"));
				list.add(new StringTextComponent("[Passive] Heal when dealing damage"));
			}

			@Override
			public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
				boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
				double x = target.getX();
				double y = target.getY();
				double z = target.getZ();
				World world = target.level;

				Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

				PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

				PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

				DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

				final int anim_id = currentanim.getId();

				if ((chestItem.equals(RedMistJacket.body.getItem()) || chestItem.equals(RedMistEGOSuit.body.getItem())) && entityData != null) {


					if (anim_id == TCorpAnimations.KALI_DASH.getId()) {
						if (sourceentity instanceof PlayerEntity) {
							if (entityData.blips > 2) {
								entityData.blips -= 2;
								entitypatch.playAnimationSynchronized(TCorpAnimations.KALI_REND, 0);

								entitypatch.playSound(TCorpSounds.SWORD_STAB, 1, 1, 1);
							}
						}
					}

					if (anim_id == TCorpAnimations.KALI_JUMP.getId()) {
						entitypatch.playAnimationSynchronized(TCorpAnimations.KALI_IMPACT, 0);
						LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

						if (targetentitypatch != null) {
							if (Animations.BIPED_HIT_LONG.equals(targetentitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG))) {
								targetentitypatch.playAnimationSynchronized(TCorpAnimations.PUMMEL_DOWN, 0);
							}
						}

						entitypatch.playSound(TCorpSounds.SWORD_STAB, 1, 1, 1);
					}

					if (anim_id == TCorpAnimations.KALI_AUTO_4.getId()) {
						entitypatch.setStamina(Math.min(entitypatch.getStamina() + 1f, entitypatch.getMaxStamina()));
						sourceentity.heal(1);
					}

					if (anim_id == TCorpAnimations.GREAT_SPLIT_HORIZONTAL.getId()) {
						if (sourceentity instanceof PlayerEntity) {
							if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
								sourceentity.heal(2);
								((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 15);
								entitypatch.playSound(TCorpSounds.KALI_SPLIT_HORIZONTAL_HIT, 1,1);
								entityData.globalcooldown = 15;
							}
						}

						entitypatch.setStamina(Math.min(entitypatch.getStamina() + 0.8f, entitypatch.getMaxStamina()));
						sourceentity.heal(1);
					}

					if (anim_id == TCorpAnimations.KALI_IMPACT.getId() && entityData.blips < entityData.maxblips) {
						entitypatch.playSound(TCorpSounds.RESULT_POSITIVE, 1,1);
						entityData.blips = entityData.blips + 1;
					}
				} else {
					if (anim_id == TCorpAnimations.MIMICRY_HELLO.getId()) {
						if (entityData.globalcooldown <= 0) {
							entitypatch.playSound(TCorpSounds.NOTHING_THERE_BLUNT, 1,1);
							entityData.globalcooldown = 1;
						}
						LivingEntityPatch<?> patch = (LivingEntityPatch)target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
						if (patch != null) {
							hitStunEffect(patch, 0f);
						}
					}
					else if (anim_id == TCorpAnimations.MIMICRY_GOODBYE.getId()) {
						entitypatch.playSound(TCorpSounds.NOTHING_THERE_SLASH, 1,1);

					}
				}

				if (chestItem.equals(MimicryarmorItem.body.getItem())){
					if (anim_id == TCorpAnimations.MIMICRY_HELLO.getId()) {
						if (entityData.globalcooldown == 1) {
							if (entityData.blips < entityData.maxblips) {
								entityData.blips = Math.min(entityData.blips+1, entityData.maxblips);
								target.level.playSound(null,target.blockPosition(), TCorpSounds.RESULT_POSITIVE, SoundCategory.PLAYERS, 1, 1);
							}

							sourceentity.heal(4);
							((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
							entityData.globalcooldown = 5;
						}

					}

					if (anim_id == TCorpAnimations.MIMICRY_AUTO_3.getId()) {
						target.level.playSound(null,target.blockPosition(), TCorpSounds.KALI_SPLIT_VERTICAL_SLASH, SoundCategory.PLAYERS, 1, 1);
						target.addEffect(new EffectInstance(BleedPotionEffect.potion, 40, 1));
					}

					if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
						sourceentity.heal(2);
						((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
						entityData.globalcooldown = 5;
					}
				} else {
					if (sourceentity instanceof PlayerEntity) {
						if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
							sourceentity.heal(1);
							((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
							entityData.globalcooldown = 5;
						}
					}
				}


				entityData.syncPlayerVariables(sourceentity);


				MimicryhitentityProcedure.executeProcedure(Stream
						.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("entity", target),
								new AbstractMap.SimpleEntry<>("sourceentity", sourceentity), new AbstractMap.SimpleEntry<>("itemstack", itemstack))
						.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
				return retval;
			}
		}.setRegistryName("mimicry"));
	}
}