
package net.m3tte.ego_weapons.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.m3tte.ego_weapons.EgoWeaponsModElements;

@EgoWeaponsModElements.ModElement.Tag
public class MaceItem extends EgoWeaponsModElements.ModElement {
	@ObjectHolder("tcorp:mace")
	public static final Item block = null;

	public MaceItem(EgoWeaponsModElements instance) {
		super(instance, 157);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {

			@Override
			public int getUses() {
				return 250;
			}

			@Override
			public float getSpeed() {
				return 4f;
			}

			@Override
			public float getAttackDamageBonus() {
				return 7;
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
		}, 3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
		}.setRegistryName("mace"));
	}
}