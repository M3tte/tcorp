
package net.m3tte.tcorp.item.blackSilence;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.network.IPacket;
import net.minecraft.item.UseAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Blocks;

import net.m3tte.tcorp.procedures.AtelierpistolsRangedItemUsedProcedure;
import net.m3tte.tcorp.procedures.AtelierPistolHitProcedure;
import net.m3tte.tcorp.entity.renderer.AtelierpistolsRenderer;
import net.m3tte.tcorp.TcorpModElements;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

@TcorpModElements.ModElement.Tag
public class AtelierpistolsItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:atelier_pistol")
	public static final Item block = null;
	public static final EntityType arrow = (EntityType.Builder.<ArrowCustomEntity>of(ArrowCustomEntity::new, EntityClassification.MISC)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
			.sized(0.5f, 0.5f)).build("projectile_atelier_pistol").setRegistryName("projectile_atelier_pistol");

	public AtelierpistolsItem(TcorpModElements instance) {
		super(instance, 83);
		FMLJavaModLoadingContext.get().getModEventBus().register(new AtelierpistolsRenderer.ModelRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemRanged());
		elements.entities.add(() -> arrow);
	}

	public static class ItemRanged extends Item {
		public ItemRanged() {
			super(new Properties().tab(null).stacksTo(1));
			setRegistryName("atelier_pistol");
		}

		@Override
		public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
			entity.startUsingItem(hand);
			return new ActionResult(ActionResultType.SUCCESS, entity.getItemInHand(hand));
		}

		@Override
		public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
			super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
			list.add(new StringTextComponent("Pistols... echoing with vengeance"));
			list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
			list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Parries engage counterattack ").withStyle(TextFormatting.GRAY)));

		}


		@Override
		public UseAction getUseAnimation(ItemStack p_77661_1_) {
			return UseAction.BOW;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 72000;
		}


		@Override
		public void releaseUsing(ItemStack itemstack, World world, LivingEntity entityLiving, int p_77615_4_) {
			if (!world.isClientSide && entityLiving instanceof ServerPlayerEntity) {
				ServerPlayerEntity entity = (ServerPlayerEntity) entityLiving;
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				if (true) {
					ArrowCustomEntity entityarrow = shoot(world, entity, random, 1.4f, 3.5, 0);
					itemstack.hurt(1, random,entity);
					entityarrow.pickup	= AbstractArrowEntity.PickupStatus.DISALLOWED;

					AtelierpistolsRangedItemUsedProcedure.executeProcedure(
							Stream.of(new AbstractMap.SimpleEntry<>("entity", entity), new AbstractMap.SimpleEntry<>("itemstack", itemstack))
									.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
				}
			}
		}
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class ArrowCustomEntity extends AbstractArrowEntity implements IRendersAsItem {
		public ArrowCustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			super(arrow, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, World world) {
			super(type, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack getItem() {
			return new ItemStack(Blocks.WARPED_STEM);
		}

		@Override
		protected void doPostHurtEffects(LivingEntity entity) {
			super.doPostHurtEffects(entity);
			//entity.setArrowCount(entity.getArrowCount() - 1);
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
			super.onHitEntity(entityRayTraceResult);
			Entity entity = entityRayTraceResult.getEntity();
			Entity sourceentity = this.getEntity();
			Entity immediatesourceentity = this;
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;

			AtelierPistolHitProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity),
							new AbstractMap.SimpleEntry<>("sourceentity", sourceentity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}


		@Override
		public void tick() {
			super.tick();
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;
			Entity entity = this.getEntity();
			Entity immediatesourceentity = this;
			if (this.inGround)
				this.remove();
		}

		@Override
		protected ItemStack getPickupItem() {
			return ItemStack.EMPTY;
		}
	}




	public static ArrowCustomEntity shoot(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, world);
		entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
				(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
				SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static ArrowCustomEntity shoot(LivingEntity entity, LivingEntity target) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, entity.level);
		double d0 = target.getY() + (double) target.getEyeHeight() - 1.1;
		double d1 = target.getX() - entity.getX();
		double d3 = target.getZ() - entity.getZ();
		entityarrow.shoot(d1, d0 - entityarrow.getX() + (double) MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 1.4f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(3.5);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		entity.level.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
				(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
				SoundCategory.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
