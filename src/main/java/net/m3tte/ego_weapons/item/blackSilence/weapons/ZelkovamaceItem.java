
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.client.util.ITooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public class ZelkovamaceItem extends SwordItem {
	//@ObjectHolder("tcorp:zelkova_mace")

	public ZelkovamaceItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(zelkovaMaceTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add(new StringTextComponent("A mace."));
		list.add(new StringTextComponent("Formerly the weapon of a popular fixer."));
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
		list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Regain stamina and energy on hit. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" +15% Stamina").withStyle(TextFormatting.GOLD)));

	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitZelkova(entity.level, entity, sourceentity);
		return retval;
	}
	// elements.items.add(() -> new PickaxeItem(, 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier zelkovaMaceTier = new IItemTier() {
		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4;
		}

		@Override
		public float getAttackDamageBonus() {
			return 6;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}
	};
}